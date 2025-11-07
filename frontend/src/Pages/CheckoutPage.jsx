import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { SessionContext } from "../Components/context/SessionContext";
import { useCart } from "../Components/context/CartItems";
import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";

export default function CheckoutPage() {
  const { sessionInfo } = useContext(SessionContext);
  const { items, clearCart } = useCart();
  const [domicilios, setDomicilios] = useState([]);
  const [pagos, setPagos] = useState([]);
  const [direccion, setDireccion] = useState("");
  const [nuevoPago, setNuevoPago] = useState({
    numeroTarjeta: "",
    nombreTitular: "",
    fechaVencimiento: "",
  });
  const [cvv, setCvv] = useState("");
  const [selectedDomicilio, setSelectedDomicilio] = useState(null);
  const [selectedPago, setSelectedPago] = useState(null);
  const [loading, setLoading] = useState(true);
  const [pedidoId, setPedidoId] = useState(null);
  const [errorPago, setErrorPago] = useState("");
  const navigate = useNavigate();

  const email = sessionInfo?.email;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [domRes, pagoRes] = await Promise.all([
          fetch(`http://localhost:8080/api/clienteDomicilio/${email}/listar`, { credentials: "include" }),
          fetch(`http://localhost:8080/api/medioDePago/${email}/listar`, { credentials: "include" }),
        ]);
        if (domRes.ok) setDomicilios(await domRes.json());
        if (pagoRes.ok) setPagos(await pagoRes.json());
      } finally {
        setLoading(false);
      }
    };
    if (email) fetchData();
  }, [email]);

  const agregarDomicilio = async () => {
    if (!direccion.trim()) return;
    try {
      const crear = await fetch("http://localhost:8080/api/domicilio/crearDomicilio", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ direccion }),
        credentials: "include",
      });
      const nuevo = await crear.json();

      const asociar = await fetch("http://localhost:8080/api/clienteDomicilio/asociarDomicilio", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, idDomicilio: nuevo.id }),
        credentials: "include",
      });

      if (asociar.ok) {
        setDomicilios([...domicilios, nuevo]);
        setDireccion("");
      }
    } catch {}
  };

  const agregarPago = async () => {
    const { nombreTitular, numeroTarjeta, fechaVencimiento } = nuevoPago;
    if (!nombreTitular || !numeroTarjeta || !fechaVencimiento) return;

    try {
      const respuesta = await fetch(`http://localhost:8080/api/medioDePago/${email}/agregar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          nombreTitular,
          numeroTarjeta: Number(numeroTarjeta),
          fechaVencimiento: `${fechaVencimiento}-01`,
        }),
        credentials: "include",
      });

      if (respuesta.ok) {
        const data = await respuesta.json();
        setPagos([...pagos, data]);
        setNuevoPago({ numeroTarjeta: "", nombreTitular: "", fechaVencimiento: "" });
      }
    } catch {}
  };

  const crearPedido = async () => {
    if (!selectedDomicilio || !selectedPago) return null;

    const creaciones = items
        .filter((i) => i.tipo !== "Bebida")
        .map((i) => ({
          creacion: { id: i.id, precio: i.precio },
          cantidad: i.cantidad || 1,
        }));

    // IMPORTANTE: el backend espera producto.idProducto
    const bebidas = items
        .filter((i) => i.tipo === "Bebida")
        .map((i) => ({
          producto: { idProducto: i.idProducto ?? i.id },
          cantidad: i.cantidad || 1,
        }));

    try {
      const response = await fetch("http://localhost:8080/api/pedido/realizar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idCliente: email,
          idDomicilio: selectedDomicilio,
          idMedioDePago: selectedPago,
          creaciones,
          bebidas,
        }),
        credentials: "include",
      });

      if (!response.ok) return null;

      const data = await response.json();
      setPedidoId(data.id);
      return data.id;
    } catch {
      return null;
    }
  };

  const confirmarPago = async () => {
    setErrorPago("");
    if (!cvv.trim()) return;

    const idPedido = pedidoId || (await crearPedido());
    if (!idPedido) {
      setErrorPago("No se pudo crear el pedido. Inténtelo nuevamente.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/dummy/procesarPago", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idPedido,
          idMedioPago: selectedPago,
          cvv,
        }),
        credentials: "include",
      });

      if (response.ok) {
        clearCart();
        navigate("/viewOrders");
      } else {
        setErrorPago("El pago fue rechazado. Por favor pruebe con otro método de pago.");
      }
    } catch {
      setErrorPago("Ocurrió un error al procesar el pago. Intente nuevamente.");
    }
  };

  if (loading) {
    return (
        <div className="flex justify-center items-center h-screen">
          <h2 className="text-lg font-semibold text-gray-700">Cargando métodos de pago...</h2>
        </div>
    );
  }

  return (
      <>
        <MainHeader />
        <div className="pt-24 min-h-screen flex flex-col justify-between items-center bg-gray-50">
          <h1 className="text-3xl font-bold mb-6 text-gray-800">Finalizar compra</h1>

          <div className="flex flex-col gap-8 w-[calc(100vw-4rem)] md:w-[calc(100vw-28rem)] max-w-3xl mb-10">
            <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-100">
              <h2 className="font-bold text-xl mb-4 text-gray-800">Seleccionar domicilio</h2>
              {domicilios.length > 0 &&
                  domicilios.map((d) => (
                      <div key={d.id} className="flex items-center mb-2">
                        <input
                            type="radio"
                            name="domicilio"
                            checked={selectedDomicilio === d.id}
                            onChange={() => setSelectedDomicilio(d.id)}
                            className="mr-3"
                        />
                        <span>{d.direccion}</span>
                      </div>
                  ))}
              <div className="flex gap-2 mt-3">
                <input
                    className="bg-gray-100 rounded-2xl p-2 flex-1"
                    placeholder="Nueva dirección"
                    value={direccion}
                    onChange={(e) => setDireccion(e.target.value)}
                />
                <button
                    onClick={agregarDomicilio}
                    className="bg-orange-400 text-white font-bold px-3 py-2 rounded-2xl"
                >
                  + Agregar
                </button>
              </div>
            </div>

            <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-100">
              <h2 className="font-bold text-xl mb-4 text-gray-800">Seleccionar método de pago</h2>
              {pagos.length > 0 &&
                  pagos.map((m) => (
                      <div key={m.id} className="flex items-center mb-2">
                        <input
                            type="radio"
                            name="pago"
                            checked={selectedPago === m.id}
                            onChange={() => setSelectedPago(m.id)}
                            className="mr-3"
                        />
                        <span>{`${m.numeroTarjeta} - ${m.nombreTitular}`}</span>
                      </div>
                  ))}
              <div className="grid grid-cols-1 md:grid-cols-3 gap-3 mt-3">
                <input
                    className="bg-gray-100 rounded-2xl p-2"
                    placeholder="Número de tarjeta"
                    value={nuevoPago.numeroTarjeta}
                    onChange={(e) => setNuevoPago({ ...nuevoPago, numeroTarjeta: e.target.value })}
                />
                <input
                    className="bg-gray-100 rounded-2xl p-2"
                    placeholder="Nombre del titular"
                    value={nuevoPago.nombreTitular}
                    onChange={(e) => setNuevoPago({ ...nuevoPago, nombreTitular: e.target.value })}
                />
                <input
                    type="month"
                    className="bg-gray-100 rounded-2xl p-2"
                    value={nuevoPago.fechaVencimiento}
                    onChange={(e) => setNuevoPago({ ...nuevoPago, fechaVencimiento: e.target.value })}
                />
              </div>
              <div className="flex justify-end mt-3">
                <button
                    onClick={agregarPago}
                    className="bg-orange-400 text-white font-bold px-3 py-2 rounded-2xl"
                >
                  + Agregar
                </button>
              </div>
            </div>

            <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-100">
              <h2 className="font-bold text-xl mb-4 text-gray-800">Confirmar pago</h2>
              <input
                  type="password"
                  maxLength={3}
                  className="bg-gray-100 rounded-2xl p-2 w-24 text-center text-lg tracking-widest"
                  placeholder="CVV"
                  value={cvv}
                  onChange={(e) => setCvv(e.target.value)}
              />
              {errorPago && <p className="text-red-600 font-semibold mt-4">{errorPago}</p>}
              <button
                  onClick={confirmarPago}
                  className="w-full mt-6 bg-orange-500 text-white font-bold py-3 rounded-2xl hover:scale-105 transition-transform text-lg shadow-xl"
              >
                Pagar ahora
              </button>
            </div>
          </div>
        </div>
        <Footer />
      </>
  );
}
