import { useEffect, useState } from "react";

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
                setPedidos(await respuesta.json());
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
            const respuesta = await fetch(`http://localhost:8080/api/pedido/${id}/avanzarEstado`, {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

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
            const respuesta = await fetch(`http://localhost:8080/api/pedido/${id}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (!respuesta.ok) {
                alert("Error al obtener los detalles del pedido.");
                return;
            }

            const datos = await respuesta.json();

            const productos = [];
            const agregar = (nombre, tipo, precio, cantidad, ingredientes) =>
                productos.push({ nombre, tipo, precio, cantidad, ingredientes });

            datos.pizzas?.forEach((p) =>
                agregar(p.nombre || "Pizza", "Pizza", p.precio || 0, p.cantidad || 1, p.ingredientes || [])
            );
            datos.hamburguesas?.forEach((h) =>
                agregar(h.nombre || "Hamburguesa", "Hamburguesa", h.precio || 0, h.cantidad || 1, h.ingredientes || [])
            );
            datos.bebidas?.forEach((b) =>
                agregar(b.nombre || "Bebida", "Bebida", b.precio || 0, b.cantidad || 1)
            );
            datos.productos?.forEach((p) =>
                agregar(p.nombre || "Producto", p.tipo || "Producto", p.precio || 0, p.cantidad || 1)
            );

            setPedidoSeleccionado({ ...datos, productos });
        } catch (error) {
            console.error(error);
            alert("Error al obtener los detalles del pedido.");
        }
    };

    const cerrarDetalles = () => {
        setPedidoSeleccionado(null);
    };

    return (
        <div className="min-h-screen w-full bg-gray-50 flex flex-col items-center py-10 px-6">
            <h1 className="text-3xl font-extrabold text-gray-800 mb-8 text-center">
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
                            className="w-full flex justify-between items-center bg-white rounded-2xl shadow-md p-4 border border-gray-100"
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
                            <div className="flex gap-3">
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
                <div className="w-full max-w-5xl bg-white rounded-2xl shadow-lg p-6 mt-10 border border-gray-100">
                    <h2 className="text-2xl font-extrabold text-gray-800 mb-4 text-center">
                        Detalles del Pedido #{pedidoSeleccionado.id}
                    </h2>

                    <div className="text-gray-700 space-y-2 mb-6">
                        <p>
                            <span className="font-semibold">Fecha:</span> {pedidoSeleccionado.fecha}
                        </p>
                        <p>
                            <span className="font-semibold">Estado:</span> {pedidoSeleccionado.estado}
                        </p>
                        <p>
                            <span className="font-semibold">Dirección:</span>{" "}
                            {pedidoSeleccionado.domicilio?.direccion || "No especificada"}
                        </p>
                        <p>
                            <span className="font-semibold">Pago:</span>{" "}
                            {pedidoSeleccionado.estaPago ? "Pagado" : "No pagado"}
                        </p>
                        {pedidoSeleccionado.medioDePago?.numeroTarjeta && (
                            <p>
                                <span className="font-semibold">Tarjeta:</span> **** **** ****{" "}
                                {String(pedidoSeleccionado.medioDePago.numeroTarjeta).slice(-4)}
                            </p>
                        )}
                        <p>
                            <span className="font-semibold">Monto total:</span> $
                            {Number(pedidoSeleccionado.precio ?? 0).toFixed(2)}
                        </p>
                    </div>

                    {Array.isArray(pedidoSeleccionado.productos) &&
                    pedidoSeleccionado.productos.length > 0 ? (
                        <div className="bg-gray-50 rounded-xl p-4 shadow-inner">
                            <h3 className="text-lg font-semibold text-gray-800 mb-3">
                                Productos del pedido
                            </h3>
                            <ul className="space-y-3">
                                {pedidoSeleccionado.productos.map((item, i) => (
                                    <li key={i} className="border-b pb-2">
                                        <div className="flex justify-between">
                                            <span className="text-gray-800 font-medium">
                                                {item.tipo}: {item.nombre} x{item.cantidad}
                                            </span>
                                            <span className="text-gray-900 font-bold">
                                                ${Number(item.precio).toFixed(2)}
                                            </span>
                                        </div>
                                        {item.ingredientes?.length > 0 && (
                                            <ul className="ml-4 mt-1 text-sm text-gray-600 list-disc">
                                                {item.ingredientes.map((ing, j) => (
                                                    <li key={j}>
                                                        {ing.nombre ||
                                                            ing.producto?.nombre ||
                                                            "Ingrediente"}{" "}
                                                        {ing.cantidad ? `x${ing.cantidad}` : ""}
                                                    </li>
                                                ))}
                                            </ul>
                                        )}
                                    </li>
                                ))}
                            </ul>
                        </div>
                    ) : (
                        <p className="text-gray-500 italic text-center mt-4">
                            No hay productos registrados en este pedido.
                        </p>
                    )}

                    <div className="flex justify-center mt-6">
                        <button
                            onClick={cerrarDetalles}
                            className="bg-orange-500 text-white rounded-2xl py-2 px-6 font-bold hover:scale-105 transition-transform"
                        >
                            Cerrar detalles
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}
