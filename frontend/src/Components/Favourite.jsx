import { useNavigate } from "react-router-dom";

export default function Favourite({ favorito }) {
    const navigate = useNavigate();
    if (!favorito) return null;

    const icono =
        favorito.tipo === "Pizza"
            ? "🍕"
            : favorito.tipo === "Hamburguesa"
                ? "🍔"
                : "⭐";

    return (
        <div
            onClick={() =>
                navigate("/viewCreation", { state: { creationId: favorito.id } })
            }
            className="cursor-pointer flex flex-col items-center justify-center m-2 bg-white rounded-2xl shadow-md w-36 h-40 hover:shadow-lg hover:scale-105 transition-transform duration-200 border border-gray-100"
        >
            <div
                className="flex items-center justify-center w-20 h-20 rounded-xl text-5xl text-white shadow-md mb-2"
                style={{
                    background: "linear-gradient(135deg, #ffa94d, #ff7a00)",
                }}
            >
                {icono}
            </div>
            <h3 className="text-center font-semibold text-gray-800 text-sm truncate px-2">
                {favorito.nombre}
            </h3>
        </div>
    );
}
