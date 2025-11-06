export default function AdminProductCard({ product, onEdit, onRemove }) {
    const isHidden = product.visible === false;

    return (
        <div
            className={`flex justify-between items-center rounded-2xl shadow-xl p-4 transition-all ${
                isHidden
                    ? "bg-red-100 border-2 border-red-500"
                    : "bg-green-100 border-2 border-green-400"
            }`}
        >
            <div className="flex flex-col">
                <h1
                    className={`font-bold text-xl ${
                        isHidden ? "text-red-700" : "text-green-700"
                    }`}
                >
                    {product.nombre}
                    {isHidden && (
                        <span className="ml-2 text-sm text-red-600 font-semibold">
              (OCULTO)


                            

            </span>
                    )}
                </h1>
                <h2 className="text-gray-700 text-lg">
                    Precio: <span className="text-black font-semibold">${product.precio}</span>
                </h2>
                <h3 className="text-gray-600 text-sm">Tipo: {product.tipo}</h3>
            </div>

            <div className="flex gap-3">
                <button
                    className="bg-gray-400 text-white rounded-2xl px-4 py-2 shadow-lg hover:bg-gray-500"
                    onClick={onEdit}
                >
                    Editar
                </button>
                <button
                    className="bg-red-600 text-white rounded-2xl px-4 py-2 shadow-lg hover:bg-red-700 transition"
                    onClick={(e) => onRemove(e, product.idProducto)}
                >
                    Eliminar
                </button>
            </div>
        </div>
    );
}
