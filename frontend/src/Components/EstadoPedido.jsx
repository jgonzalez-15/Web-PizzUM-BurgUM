import { useState } from "react";
import { FaStar } from "react-icons/fa";

export default function EstadoPedido({ pedido, Cancelar, VerDetalles, EnviarCalificacion }) {
    const etapas = ["En Cola", "En Preparacion", "En Camino", "Entregado"];
    const [ratingMode, setRatingMode] = useState(false);
    const [rating, setRating] = useState(0);

    const getColor = (etapa) => {
        const estadoActual = pedido.estado;

        const orden = {
            "En Cola": 0,
            "En Preparacion": 1,
            "En Camino": 2,
            "Entregado": 3,
        };

        const actualIndex = orden[estadoActual];
        const etapaIndex = orden[etapa];

        if (etapaIndex < actualIndex) return "bg-orange-500";
        if (etapaIndex === actualIndex) return "bg-yellow-400";
        return "bg-gray-300";
    };

    const handleRate = async (value) => {
        setRating(value);
        await EnviarCalificacion(pedido.id, value); // vos completas esta función
        setRatingMode(false);
    };

    return (
        <div className="w-full flex flex-row justify-between items-center bg-gray-50 rounded-2xl shadow-xl h-32">
            <div className="m-4 flex flex-col truncate">
                <h1 className="font-bold text-xl">Pedido #{pedido.id}</h1>
                <h2 className="truncate">Fecha: {pedido.fecha}</h2>
                <h2 className="text-orange-400">{pedido.estado}</h2>
            </div>

            <div className="m-4 flex flex-row gap-4 items-center">
                <div className="gap-4 md:flex flex-row items-center hidden">
                    {etapas.map((etapa, i) => (
                        <div key={i} className="flex flex-col items-center">
                            <div className={`h-10 w-10 rounded-full transition-colors duration-300 ${getColor(etapa)}`} />
                            <h1 className="text-sm">{etapa}</h1>
                        </div>
                    ))}
                </div>

                <div className="flex flex-col md:flex-row gap-2">
                    <button
                        onClick={() => VerDetalles(pedido.id)}
                        className="z-0 h-12 rounded-2xl shadow-2xl font-bold px-4 text-sm bg-orange-500 text-white hover:bg-orange-600 transition-transform hover:scale-105"
                    >
                        Ver detalles
                    </button>
                    {pedido.estado === "Entregado" && pedido.calificacion === 0 ? (
                        ratingMode ? (
                            <div className="flex flex-row gap-1 items-center">
                                {[1, 2, 3, 4, 5].map((value) => (
                                    <button
                                        key={value}
                                        onClick={() => handleRate(value)}
                                        className="text-yellow-400 hover:scale-110 transition-transform"
                                    >
                                        <FaStar
                                            size={20}
                                            className={value <= rating ? "fill-yellow-400" : "fill-gray-300"}
                                        />
                                    </button>
                                ))}
                            </div>
                        ) : (
                            <button
                                onClick={() => setRatingMode(true)}
                                className="z-0 h-12 rounded-2xl shadow-2xl font-bold px-4 text-sm bg-yellow-500 text-white hover:bg-yellow-700 transition-transform hover:scale-105"
                            >
                                Calificar
                            </button>
                        )
                    ) : pedido.estado === "Entregado" && pedido.calificacion > 0 ? (
                        <button
                            disabled
                            className="z-0 h-12 rounded-2xl shadow-2xl font-bold px-4 text-sm bg-gray-400 text-white cursor-not-allowed"
                        >
                            Calificado
                        </button>
                    ) : (
                        <button
                            onClick={() => pedido.estado === "En Cola" && Cancelar(pedido.id)}
                            disabled={pedido.estado !== "En Cola"}
                            className={`z-0 h-12 rounded-2xl shadow-2xl font-bold px-4 text-sm ${
                                pedido.estado === "En Cola"
                                    ? "bg-red-600 text-white hover:bg-red-700"
                                    : "bg-gray-300 text-gray-600 cursor-not-allowed"
                            }`}
                        >
                            Cancelar
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
}
