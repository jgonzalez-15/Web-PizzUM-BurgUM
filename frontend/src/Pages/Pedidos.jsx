import { useContext, useState, useEffect } from "react";
import { SessionContext } from "../Components/context/SessionContext";

import EncabezadoPrincipal from "../Components/Encabezado.jsx";
import EstadoPedido from "../Components/OrderStatus";
import PieDePagina from "../Components/PieDePagina.jsx";

function Pedidos() {
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const [pedidosActivos, setPedidosActivos] = useState([]);
    const [historialPedidos, setHistorialPedidos] = useState([]);
    const [mostrarHistorial, setMostrarHistorial] = useState(false);
    const { sessionInfo } = useContext(SessionContext);
    const correo = sessionInfo?.email;

    const obtenerPedidos = async () => {
        try {
            const respuesta = await fetch(`http://localhost:8080/api/cliente/historial-pedidos`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                credentials: "include",
            });

            if (respuesta.ok) {
                const datos = await respuesta.json();
                setPedidosActivos(datos.filter((p) => p.estado !== "Entregado"));
                setHistorialPedidos(datos.filter((p) => p.estado === "Entregado"));
            } else {
                alert("Ocurrió un error al obtener los pedidos.");
            }
        } catch (error) {
            console.error("Error al obtener pedidos:", error);
        }
    };

    useEffect(() => {
        if (correo) {
            obtenerPedidos();
        }
    }, [correo]);

    return (
        <>
            <EncabezadoPrincipal />

            <div className="pt-24 min-h-screen w-full flex flex-col justify-between bg-gray-50">
                {/* Título principal */}
                <h1 className="text-3xl font-extrabold text-gray-800 text-center mb-6">
                    Mis Pedidos
                </h1>

                {/* Contenido */}
                <div className="flex flex-col items-center w-full px-6 md:px-16 mb-10">
                    {/* Pedidos activos */}
                    <div className="w-full max-w-3xl bg-white shadow-xl rounded-2xl p-8 border border-gray-200 mb-8">
                        <h2 className="text-2xl font-semibold text-gray-800 mb-4">
                            Pedidos en curso
                        </h2>

                        {pedidosActivos.length > 0 ? (
                            <div className="flex flex-col gap-6">
                                {pedidosActivos.map((pedido) => (
                                    <EstadoPedido key={pedido.id} pedido={pedido} />
                                ))}
                            </div>
                        ) : (
                            <p className="text-gray-500 text-center italic">
                                No tenés pedidos en curso.
                            </p>
                        )}
                    </div>

                    {/* Historial de pedidos */}
                    <div className="w-full max-w-3xl bg-white shadow-xl rounded-2xl p-8 border border-gray-200">
                        <div className="flex justify-between items-center mb-4">
                            <h2 className="text-2xl font-semibold text-gray-800">
                                Historial de pedidos
                            </h2>
                            <button
                                onClick={() => setMostrarHistorial(!mostrarHistorial)}
                                className={`px-5 py-2 rounded-2xl font-bold shadow-md transition-transform ${
                                    mostrarHistorial
                                        ? "bg-gray-300 text-gray-800 hover:bg-gray-400"
                                        : "bg-orange-400 text-white hover:bg-orange-500"
                                }`}
                            >
                                {mostrarHistorial ? "Ocultar historial" : "Ver historial"}
                            </button>
                        </div>

                        {mostrarHistorial ? (
                            historialPedidos.length > 0 ? (
                                <div className="flex flex-col gap-6 mt-4">
                                    {historialPedidos.map((pedido) => (
                                        <EstadoPedido key={pedido.id} pedido={pedido} />
                                    ))}
                                </div>
                            ) : (
                                <p className="text-gray-500 text-center italic mt-4">
                                    No hay pedidos anteriores.
                                </p>
                            )
                        ) : (
                            <p className="text-gray-500 text-sm italic text-center mt-2">
                                Mostrando solo pedidos actuales.
                            </p>
                        )}
                    </div>
                </div>

                <PieDePagina />
            </div>
        </>
    );
}

export default Pedidos;
