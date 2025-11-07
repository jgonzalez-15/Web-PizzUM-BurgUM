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
  const [nuevoPago, setNuevoPago] = useState({ numeroTarjeta: "", nombreTitular: "", fechaVencimiento: "" });
  const [cvv, setCvv] = useState("");
  const [selectedDomicilio, setSelectedDomicilio] = useState(null);
  const [selectedPago, setSelectedPago] = useState(null);
  const [loading, setLoading] = useState(true);

  const [creando, setCreando] = useState(false);
  const [pagando, setPagando] = useState(false);
  const [mensaje, setMensaje] = useState(null); // {type:'error'|'ok', text:string}

  const [pedidoId, setPedidoId] = useState(null);
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
      } catch (e) {
        console.error(e);
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
      if (!crear.ok) return;
      const nuevo = await crear.json();
      const asociar = await fetch("http://localhost:8080/api/clienteDomicilio/asociarDomicilio", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, idDomicilio: nuevo.id }),
        credentials: "include",
      });
      if (asociar.ok) {
        setDomicilios((prev) => [...prev, nuevo]);
        setDireccion("");
      }
    } catch (e) {
      console.error(e);
    }
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
        setPagos((prev) => [...prev, data]);
        setNuevoPago({ numeroTarjeta: "", nombreTitular: "", fechaVencimiento: "" });
      }
    } catch (e) {
      console.error(e);
    }
  };

  const crearPedido = async () => {
    if (!selectedDomicilio || !selectedPago) {
      setMensaje({ type: "error", text: "Seleccioná un domicilio y un método de pago." });
      return null;
    }
    setMensaje(null);
    setCreando(true);

    const creaciones = items
        .filter((i) => i.tipo !== "Bebida")
        .map((i) => ({
          creacion: { id: i.id, precio: i.precio },
          cantidad: i.cantidad || 1,
        }));

    const bebidas = items


        .filter((i) => i.tipo === "Bebida")
        .map((i) => ({
          producto: { idProducto: i.idProducto ?? i.id},
          cantidad: i.cantidad || 1,
        }));



    try {
      const res = await fetch("http://localhost:8080/api/pedido/realizar", {
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

      if (!res.ok) {
        let msg = "No se pudo crear el pedido. Inténtelo nuevamente.";
        try {
          const err = await res.json();
          if (err?.message) msg = err.message;
        } catch {}
        setMensaje({ type: "error", text: msg });
        return null;
      }

      const data = await res.json();
      setPedidoId(data.id);
      return data.id;
    } catch (e) {
      console.error(e);
      setMensaje({ type: "error", text: "Error de red al crear el pedido." });
      return null;
    } finally {
      setCreando(false);
    }
  };

  const confirmarPago = async () => {
    if (!cvv.trim()) {
      setMensaje({ type: "error", text: "Ingresá el CVV." });
      return;
    }

    setMensaje(null);
    setPagando(true);

    const idPedido = pedidoId || (await crearPedido());
    if (!idPedido) {
      setPagando(false);
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/dummy/procesarPago", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idPedido, idMedioPago: selectedPago, cvv }),
        credentials: "include",
      });

      if (!res.ok) {
        let msg = "Pago rechazado. Probá con otro método de pago.";
        try {
          const err = await res.json();
          if (err?.message) msg = err.message;
        } catch {}
        setMensaje({ type: "error", text: msg });
        return;
      }

      const data = await res.json();
      setMensaje({ type: "ok", text: `Pago aprobado: ${data.codigoTransaccion}` });
      clearCart();
      navigate("/viewOrders");
    } catch (e) {
      console.error(e);
      setMensaje({ type: "error", text: "No se pudo procesar el pago. Intentá nuevamente." });
    } finally {
      setPagando(false);
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
              {domicilios.map((d) => (
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
              {pagos.map((m) => (
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

              {mensaje && (
                  <div
                      className={`mb-4 rounded-xl px-4 py-3 text-sm ${
                          mensaje.type === "error"
                              ? "bg-red-50 text-red-700 border border-red-200"
                              : "bg-green-50 text-green-700 border border-green-200"
                      }`}
                  >
                    {mensaje.text}
                  </div>
              )}

              <input
                  type="password"
                  maxLength={3}
                  className="bg-gray-100 rounded-2xl p-2 w-24 text-center text-lg tracking-widest"
                  placeholder="CVV"
                  value={cvv}
                  onChange={(e) => setCvv(e.target.value)}
              />
              <button
                  onClick={confirmarPago}
                  disabled={creando || pagando}
                  className={`w-full mt-6 text-white font-bold py-3 rounded-2xl transition-transform text-lg shadow-xl ${
                      creando || pagando ? "bg-orange-300 cursor-not-allowed" : "bg-orange-500 hover:scale-105"
                  }`}
              >
                {pagando ? "Procesando..." : "Pagar ahora"}
              </button>
            </div>
          </div>
        </div>
        <Footer />
      </>
  );
}
