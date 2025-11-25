import { useNavigate } from "react-router-dom";

export default function Favorito({ favorito }) {
    const navegar = useNavigate();
    if (!favorito) return null;

    const icono =
        favorito.tipo === "Pizza"
            ? "🍕"
            : favorito.tipo === "Hamburguesa"
                ? "🍔"
                : "";

    return (
        <div
            onClick={() =>
                navegar("/verCreacion", { state: { creationId: favorito.id } })
            }
            className="cursor-pointer flex flex-col items-center justify-center m-2 bg-white rounded-2xl shadow-md w-36 h-44
                 hover:shadow-xl hover:scale-105 active:scale-95 transition-all duration-200 border border-gray-100"
        >
            {/* Ícono */}
            <div
                className="flex items-center justify-center w-20 h-20 rounded-xl text-5xl text-white shadow-lg mb-3"
                style={{
                    background: "linear-gradient(135deg, #ffb347, #ff7e00)",
                }}
            >
                {icono}
            </div>

            {/* Nombre de la creación */}
            <h3 className="text-center font-bold text-gray-800 text-sm truncate px-3">
                {favorito.nombre}
            </h3>

            {/* Tipo de creación */}
            {favorito.tipo && (
                <p className="text-gray-500 text-xs font-medium mt-1">
                    {favorito.tipo}
                </p>
            )}
        </div>
    );
}
