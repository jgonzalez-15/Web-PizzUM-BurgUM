import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { ContextoSesion } from "../Components/context/ContextoSesion.jsx";
import { usarCarrito } from "../Components/context/ContextoCarrito.jsx";
import EncabezadoPago from "../Components/EncabezadoPago.jsx";
import PieDePagina from "../Components/PieDePagina.jsx";

export default function PaginaPago() {
  const { sessionInfo } = useContext(ContextoSesion);
  const { items, limpiarCarrito } = usarCarrito();
  const navigate = useNavigate();
  const correoCliente = sessionInfo?.email;

  const [domicilios, setDomicilios] = useState([]);
  const [mediosDePago, setMediosDePago] = useState([]);

  const [direccionNueva, setDireccionNueva] = useState("");
  const [pagoNuevo, setPagoNuevo] = useState({
    numeroTarjeta: "",
    nombreTitular: "",
    fechaVencimiento: "",
  });
  const [cvv, setCvv] = useState("");

  const [domicilioSeleccionado, setDomicilioSeleccionado] = useState(null);
  const [pagoSeleccionado, setPagoSeleccionado] = useState(null);

  const [cargando, setCargando] = useState(true);
  const [creandoPedido, setCreandoPedido] = useState(false);
  const [procesandoPago, setProcesandoPago] = useState(false);
  const [mensaje, setMensaje] = useState(null);
  const [pedidoId, setPedidoId] = useState(null);

  useEffect(() => {
    if (correoCliente) obtenerDatosCliente();
  }, [correoCliente]);

  const obtenerDatosCliente = async () => {
    try {
      const [respuestaDomicilios, respuestaPagos] = await Promise.all([
        fetch(`http://localhost:8080/api/clienteDomicilio/listar`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          credentials: "include",
        }),
        fetch(`http://localhost:8080/api/medioDePago/listar`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
          credentials: "include",
        }),
      ]);

      if (respuestaDomicilios.ok) setDomicilios(await respuestaDomicilios.json());
      if (respuestaPagos.ok) setMediosDePago(await respuestaPagos.json());
    } catch (error) {
      console.error("Error al obtener datos del cliente:", error);
    } finally {
      setCargando(false);
    }
  };

  const agregarDomicilio = async () => {
    if (!direccionNueva.trim()) return;

    try {
      const respuesta = await fetch("http://localhost:8080/api/domicilio/crearDomicilio", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({ direccion: direccionNueva }),
        credentials: "include",
      });

      if (!respuesta.ok) return;
      const domicilioCreado = await respuesta.json();

      const asociar = await fetch("http://localhost:8080/api/clienteDomicilio/asociarDomicilio", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({ email: correoCliente, idDomicilio: domicilioCreado.id }),
        credentials: "include",
      });

      if (asociar.ok) {

        setDireccionNueva("");
        await cargarDomicilios();
      }
    } catch (error) {
      console.error("Error al agregar domicilio:", error);
    }
  };

  const cargarDomicilios = async () => {
    try {
      const resp = await fetch("http://localhost:8080/api/clienteDomicilio/listar", {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        credentials: "include",
      });
      if (resp.ok) setDomicilios(await resp.json());
    } catch (error) {
      console.error("Error al cargar domicilios:", error);
    }
  };

  const agregarMedioDePago = async () => {
    const { nombreTitular, numeroTarjeta, fechaVencimiento } = pagoNuevo;
    if (!nombreTitular || !numeroTarjeta || !fechaVencimiento) return;

    try {
      const respuesta = await fetch("http://localhost:8080/api/medioDePago/agregar", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({
          nombreTitular,
          numeroTarjeta: Number(numeroTarjeta),
          fechaVencimiento: `${fechaVencimiento}-01`,
        }),
        credentials: "include",
      });

      if (respuesta.ok) {
        const nuevo = await respuesta.json();
        setMediosDePago((prev) => [...prev, nuevo]);
        setPagoNuevo({ numeroTarjeta: "", nombreTitular: "", fechaVencimiento: "" });
      }
    } catch (error) {
      console.error("Error al agregar medio de pago:", error);
    }
  };

  const generarPedido = async () => {
    if (!domicilioSeleccionado || !pagoSeleccionado) {
      setMensaje({
        tipo: "error",
        texto: "Seleccioná un domicilio y un método de pago.",
      });
      return null;
    }

    setMensaje(null);
    setCreandoPedido(true);

    const creaciones = items
        .filter((item) => item.tipo !== "Bebida")
        .map((item) => ({
          creacion: { id: item.id, precio: item.precio },
          cantidad: item.cantidad || 1,
        }));

    const bebidas = items
        .filter((item) => item.tipo === "Bebida")
        .map((item) => ({
          producto: { idProducto: item.idProducto ?? item.id },
          cantidad: item.cantidad || 1,
        }));

    try {
      const respuesta = await fetch("http://localhost:8080/api/pedido/realizarPedido", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({
          idCliente: correoCliente,
          idDomicilio: domicilioSeleccionado,
          idMedioDePago: pagoSeleccionado,
          creaciones,
          bebidas,
        }),
        credentials: "include",
      });

      if (!respuesta.ok) {
        let mensajeError = "No se pudo crear el pedido.";
        try {
          const error = await respuesta.json();
          if (error?.message) mensajeError = error.message;
        } catch {}
        setMensaje({ tipo: "error", texto: mensajeError });
        return null;
      }

      const pedido = await respuesta.json();
      setPedidoId(pedido.id);
      return pedido.id;
    } catch (error) {
      console.error("Error al crear pedido:", error);
      setMensaje({ tipo: "error", texto: "Error de conexión al crear el pedido." });
      return null;
    } finally {
      setCreandoPedido(false);
    }
  };

  const procesarPago = async () => {
    if (!cvv.trim()) {
      setMensaje({ tipo: "error", texto: "Ingresá el código CVV." });
      return;
    }

    setMensaje(null);
    setProcesandoPago(true);

    const idPedido = pedidoId || (await generarPedido());
    if (!idPedido) {
      setProcesandoPago(false);
      return;
    }

    try {
      const respuesta = await fetch("http://localhost:8080/api/dummy/procesarPago", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({ idPedido, idMedioPago: pagoSeleccionado, cvv }),
        credentials: "include",
      });

      if (!respuesta.ok) {
        let mensajeError = "Pago rechazado. Probá con otro método.";
        try {
          const error = await respuesta.json();
          if (error?.message) mensajeError = error.message;
        } catch {}
        setMensaje({ tipo: "error", texto: mensajeError });
        return;
      }

      const data = await respuesta.json();
      setMensaje({ tipo: "ok", texto: `Pago aprobado: ${data.codigoTransaccion}` });
      limpiarCarrito();
      navigate("/verPedidos");
    } catch (error) {
      console.error("Error al procesar pago:", error);
      setMensaje({ tipo: "error", texto: "No se pudo procesar el pago." });
    } finally {
      setProcesandoPago(false);
    }
  };

  if (cargando) {
    return (
        <div className="flex justify-center items-center h-screen">
          <h2 className="text-lg font-semibold text-gray-700">
            Cargando información...
          </h2>
        </div>
    );
  }

  return (
      <>

        <EncabezadoPago />
        <div className="pt-24 min-h-screen flex flex-col justify-between items-center bg-gray-50">
          <h1 className="text-3xl font-bold mb-6 text-gray-800">
            Finalizar compra
          </h1>

          <div className="flex flex-col gap-8 w-[calc(100vw-4rem)] md:w-[calc(100vw-28rem)] max-w-3xl mb-10">
            {/* Domicilio */}
            <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-100">
              <h2 className="font-bold text-xl mb-4 text-gray-800">
                Seleccionar domicilio
              </h2>
              {domicilios.map((dom) => (
                  <div key={dom.id} className="flex items-center mb-2">
                    <input
                        type="radio"
                        name="domicilio"
                        checked={domicilioSeleccionado === dom.id}
                        onChange={() => setDomicilioSeleccionado(dom.id)}
                        className="mr-3"
                    />
                    <span>{dom.direccion}</span>
                  </div>
              ))}
              <div className="flex gap-2 mt-3">
                <input
                    className="bg-gray-100 rounded-2xl p-2 flex-1"
                    placeholder="Nueva dirección"
                    value={direccionNueva}
                    onChange={(e) => setDireccionNueva(e.target.value)}
                />
                <button
                    onClick={agregarDomicilio}
                    className="bg-orange-500 text-white font-bold px-3 py-2 rounded-2xl hover:bg-orange-600 transition"
                >
                  + Agregar
                </button>
              </div>
            </div>

            {/* Metodo de pago */}
            <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-100">
              <h2 className="font-bold text-xl mb-4 text-gray-800">
                Seleccionar método de pago
              </h2>
              {mediosDePago.map((medio) => (
                  <div key={medio.id} className="flex items-center mb-2">
                    <input
                        type="radio"
                        name="medioPago"
                        checked={pagoSeleccionado === medio.id}
                        onChange={() => setPagoSeleccionado(medio.id)}
                        className="mr-3"
                    />
                    <span>{`${medio.numeroTarjeta} - ${medio.nombreTitular}`}</span>
                  </div>
              ))}
              <div className="grid grid-cols-1 md:grid-cols-3 gap-3 mt-3">
                <input
                    className="bg-gray-100 rounded-2xl p-2"
                    placeholder="Número de tarjeta"
                    value={pagoNuevo.numeroTarjeta}
                    onChange={(e) =>
                        setPagoNuevo({ ...pagoNuevo, numeroTarjeta: e.target.value })
                    }
                />
                <input
                    className="bg-gray-100 rounded-2xl p-2"
                    placeholder="Nombre del titular"
                    value={pagoNuevo.nombreTitular}
                    onChange={(e) =>
                        setPagoNuevo({ ...pagoNuevo, nombreTitular: e.target.value })
                    }
                />
                <input
                    type="month"
                    className="bg-gray-100 rounded-2xl p-2"
                    value={pagoNuevo.fechaVencimiento}
                    onChange={(e) =>
                        setPagoNuevo({
                          ...pagoNuevo,
                          fechaVencimiento: e.target.value,
                        })
                    }
                />
              </div>
              <div className="flex justify-end mt-3">
                <button
                    onClick={agregarMedioDePago}
                    className="bg-orange-500 text-white font-bold px-3 py-2 rounded-2xl hover:bg-orange-600 transition"
                >
                  + Agregar
                </button>
              </div>
            </div>

            {/* Confirmar pago */}
            <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-100">
              <h2 className="font-bold text-xl mb-4 text-gray-800">
                Confirmar pago
              </h2>

              {mensaje && (
                  <div
                      className={`mb-4 rounded-xl px-4 py-3 text-sm ${
                          mensaje.tipo === "error"
                              ? "bg-red-50 text-red-700 border border-red-200"
                              : "bg-green-50 text-green-700 border border-green-200"
                      }`}
                  >
                    {mensaje.texto}
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
                  onClick={procesarPago}
                  disabled={creandoPedido || procesandoPago}
                  className={`w-full mt-6 text-white font-bold py-3 rounded-2xl transition-transform text-lg shadow-xl ${
                      creandoPedido || procesandoPago
                          ? "bg-orange-300 cursor-not-allowed"
                          : "bg-orange-500 hover:scale-105 hover:bg-orange-600"
                  }`}
              >
                {procesandoPago ? "Procesando..." : "Pagar ahora"}
              </button>
            </div>
          </div>
        </div>
        <PieDePagina />
      </>
  );
}
