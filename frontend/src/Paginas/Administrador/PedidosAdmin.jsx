import { useEffect, useState } from "react";
import PedidoDetalleAdmin from "../../Componentes/PedidoDetalleAdmin.jsx";

export default function PedidosAdmin() {
    const [pedidos, setPedidos] = useState([]);
    const [pedidoSeleccionado, setPedidoSeleccionado] = useState(null);

    const cargarPedidos = async () => {
        try {
            const respuesta = await fetch("http://localhost:8080/api/pedido/enCurso", {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (respuesta.ok) {
                const datos = await respuesta.json();
                const filtrados = datos.filter((p) => p.estado !== "Cancelado");
                setPedidos(filtrados);
            } else {
                alert("No se pudieron obtener los pedidos.");
            }
        } catch (error) {
            console.error(error);
            alert("Error de conexión con el servidor.");
        }
    };

    useEffect(() => {
        cargarPedidos();
    }, []);

    const avanzarEstado = async (id) => {
        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/pedido/${id}/cambiarEstado`,
                {
                    method: "PUT",
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );

            if (respuesta.ok) {
                cargarPedidos();
            } else {
                alert("No se pudo actualizar el estado del pedido.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al intentar actualizar el estado.");
        }
    };

    const verDetalles = async (id) => {
        try {
            const respuesta = await fetch(`http://localhost:8080/api/pedido/ver/${id}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (!respuesta.ok) {
                alert("Error al obtener los detalles del pedido.");
                return;
            }

            const datos = await respuesta.json();
            setPedidoSeleccionado(datos);
        } catch (error) {
            console.error(error);
            alert("Error al obtener los detalles del pedido.");
        }
    };

    const cerrarDetalles = () => {
        setPedidoSeleccionado(null);
    };

    return (
        <div className="w-full bg-gray-50 flex flex-col items-center py-10 px-3 md:px-6">
            <h1 className="m-1 text-2xl md:text-3xl font-extrabold text-gray-800 mb-8 text-center">
                Pedidos en curso
            </h1>

            <div className="w-full max-w-5xl flex flex-col gap-6">
                {pedidos.length === 0 ? (
                    <p className="text-center text-gray-500 italic mt-10">
                        No hay pedidos en curso.
                    </p>
                ) : (
                    pedidos.map((pedido) => (
                        <div
                            key={pedido.id}
                            className="w-full flex justify-between items-center bg-white rounded-2xl shadow-md p-4 border border-gray-100 gap-4"
                        >
                            <div>
                                <h1 className="font-bold text-lg text-gray-800">
                                    Pedido #{pedido.id}
                                </h1>
                                <h2 className="text-gray-600">Fecha: {pedido.fecha}</h2>
                                <h2 className="text-orange-500 font-semibold">
                                    Estado: {pedido.estado}
                                </h2>
                            </div>
                            <div className="flex flex-col gap-3">
                                <button
                                    className="bg-gray-200 text-black rounded-xl px-4 py-2 font-bold hover:scale-105 transition-transform"
                                    onClick={() => verDetalles(pedido.id)}
                                >
                                    Ver detalles
                                </button>
                                <button
                                    className="bg-orange-500 text-white rounded-xl px-4 py-2 font-bold hover:scale-105 transition-transform"
                                    onClick={() => avanzarEstado(pedido.id)}
                                >
                                    Avanzar estado
                                </button>
                            </div>
                        </div>
                    ))
                )}
            </div>

            {pedidoSeleccionado && (
                <div className="w-full max-w-5xl mt-10">
                    <div className="bg-white rounded-2xl shadow-lg p-6 border border-gray-200 mb-6">
                        <h2 className="text-2xl font-extrabold text-gray-800 mb-2 text-center">
                            Detalles del Pedido #{pedidoSeleccionado.id}
                        </h2>

                        <PedidoDetalleAdmin
                            pedido={pedidoSeleccionado}
                            Cerrar={cerrarDetalles}
                        />
                    </div>
                </div>
            )}
        </div>
    );
}
