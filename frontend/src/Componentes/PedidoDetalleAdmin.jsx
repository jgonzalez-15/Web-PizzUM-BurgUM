export default function PedidoDetalleAdmin({ pedido, Cerrar }) {
    if (!pedido) return null;

    const precioTotal = pedido.precio ?? pedido.precioTotal ?? 0;

    return (
        <div className="fixed inset-0 backdrop-blur-sm bg-white/30 flex justify-center items-center z-50">
            <div className="bg-white rounded-2xl shadow-xl p-8 w-11/12 md:w-2/3 lg:w-1/2 max-h-[90vh] overflow-y-auto">
                <h2 className="text-2xl font-bold mb-4 text-gray-800 text-center">
                    Detalles del Pedido #{pedido.id}
                </h2>

                <div className="space-y-2 mb-6">
                    <p><strong>Fecha:</strong> {pedido.fecha}</p>
                    <p><strong>Estado:</strong> {pedido.estado}</p>
                    <p><strong>Domicilio:</strong> {pedido.domicilio.direccion}</p>
                    <p><strong>Total:</strong> ${precioTotal.toFixed(2)}</p>
                </div>

                {/* Creaciones */}
                <h3 className="font-bold mt-6 text-lg text-gray-800">Creaciones</h3>
                {pedido.creacionesPedido?.length > 0 ? (
                    <ul className="list-disc ml-6">
                        {pedido.creacionesPedido.map((c, i) => {
                            const tipo =
                                c.creacion?.tipo?.toLowerCase() === "hamburguesa"
                                    ? "Hamburguesa"
                                    : "Pizza";
                            return (
                                <li key={i} className="my-2">
                                    <span className="font-semibold">{tipo}</span>{" "}
                                    (${c.creacion?.precio ?? 0}){" "}
                                    <span className="text-sm text-gray-600">
                    x{c.cantidad ?? 1}
                  </span>
                                    {c.creacion?.tipo === "Pizza" && (
                                        <div className="text-sm text-gray-600 mt-1">
                                            Tamaño:{" "}
                                            {c.creacion.ingredientes
                                                .filter((ing) => ing.tipo == "Tamanio")
                                                .map((ing) => ing.nombre)
                                                .join(", ")}
                                        </div>
                                    )}
                                    {c.creacion?.ingredientes?.length > 0 && (
                                        <div className="text-sm text-gray-600 mt-1">
                                            Ingredientes:{" "}
                                            {c.creacion.ingredientes
                                                .filter((ing) => ing.tipo != "Tamanio")
                                                .map((ing) => ing.nombre)
                                                .join(", ")}
                                        </div>
                                    )}
                                </li>
                            );
                        })}
                    </ul>
                ) : (
                    <p className="text-gray-500 text-sm">Sin creaciones.</p>
                )}

                {/* Bebidas */}
                <h3 className="font-bold mt-6 text-lg text-gray-800">Bebidas</h3>
                {pedido.bebidas?.length > 0 ? (
                    <ul className="list-disc ml-6">
                        {pedido.bebidas.map((b, i) => (
                            <li key={i} className="my-2">
                <span className="font-semibold">
                  {b.producto?.nombre ?? b.nombre ?? "Bebida"}
                </span>{" "}
                                (${b.producto?.precio ?? b.precio ?? 0}){" "}
                                <span className="text-sm text-gray-600">
                  x{b.cantidad ?? 1}
                </span>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p className="text-gray-500 text-sm">Sin bebidas.</p>
                )}

                <div className="flex justify-end mt-6">
                    <button
                        onClick={Cerrar}
                        className="bg-gray-300 px-5 py-2 rounded-2xl font-bold hover:bg-gray-400"
                    >
                        Cerrar
                    </button>
                </div>
            </div>
        </div>
    );
}
