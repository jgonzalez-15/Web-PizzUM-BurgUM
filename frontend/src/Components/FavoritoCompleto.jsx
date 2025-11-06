import { Link } from "react-router-dom";
import AddToCartButton from "./AddToCartButton";

export default function FavoritoCompleto({ favorito }) {
    return (
        <div className="flex flex-col md:flex-row items-center justify-between gap-4 bg-white rounded-2xl shadow-md hover:shadow-lg transition-shadow duration-200 p-4 md:p-6 border border-gray-100">
            {/* Imagen */}
            <div className="flex-shrink-0 w-24 h-24 md:w-32 md:h-32 bg-gray-100 rounded-xl" />

            {/* Información */}
            <div className="flex-1 text-center md:text-left">
                <h1 className="font-bold text-xl md:text-2xl text-gray-800">{favorito.nombre}</h1>
                <p className="text-lg text-orange-600 font-semibold mt-1">${favorito.precio}</p>
            </div>

            {/* Botones */}
            <div className="flex flex-row md:flex-col justify-center items-center gap-2 w-full md:w-auto">
                <Link
                    to="/viewCreation"
                    state={{ creationId: favorito.id }}
                    className="w-full md:w-36 bg-gray-200 hover:bg-gray-300 text-gray-800 font-semibold py-2 px-4 rounded-xl text-center shadow-sm transition"
                >
                    Detalles
                </Link>
                <AddToCartButton item={favorito} isPrimary={true} />
            </div>
        </div>
    );
}
