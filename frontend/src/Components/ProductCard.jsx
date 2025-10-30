export default function ProductCard({ product, onEdit }) {
  return (
    <div className="flex justify-between items-center bg-gray-50 rounded-2xl shadow-xl p-4">
      <div className="flex flex-col">
        <h1 className="font-bold text-xl">{product.name}</h1>
        <h2 className="text-gray-700 text-lg">Precio: <span className="text-black">${product.price}</span></h2>
      </div>
      <div className="flex gap-3">
        <button
          className="bg-gray-400 text-white rounded-2xl px-4 py-2 shadow-lg"
          onClick={onEdit}
        >
          Editar
        </button>
        <button className="bg-red-600 text-white rounded-2xl px-4 py-2 shadow-lg hover:bg-red-700 transition">
          Eliminar
        </button>
      </div>
    </div>
  );
}
