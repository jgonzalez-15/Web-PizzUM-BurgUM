import { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { SessionContext } from "../Components/context/SessionContext";

import PieDePagina from "../Components/PieDePagina.jsx";
import Encabezado from "../Components/Encabezado.jsx";
import BotonAgregarCarrito from "../Components/BotonAgregarCarrito.jsx";

export default function Favoritos() {
    const [favoritos, setFavoritos] = useState([]);
    const { sessionInfo } = useContext(SessionContext);
    const navigate = useNavigate();

    useEffect(() => {
        window.scrollTo(0, 0);
        obtenerFavoritos();
    }, []);

    const obtenerFavoritos = async () => {
        try {
            const respuesta = await fetch("http://localhost:8080/api/favorito/listar", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                credentials: "include",
            });

            if (respuesta.ok) {
                const datos = await respuesta.json();
                setFavoritos(datos);
            } else {
                alert("Ocurrió un error al obtener tus favoritos.");
            }
        } catch (error) {
            console.error("Error al obtener favoritos:", error);
        }
    };

    return (
        <>
            <Encabezado className="z-20 fixed top-0 left-0 w-full" />

            <div className="pt-24 min-h-screen flex flex-col justify-between bg-gray-50">
                <div className="mx-6 md:mx-16">
                    <div className="bg-white rounded-2xl shadow-md p-6 border border-gray-100 flex items-center gap-3 mb-8">
                        <h1 className="font-extrabold text-2xl md:text-3xl text-gray-800">
                            Tus favoritos
                        </h1>
                    </div>

                    {favoritos.length > 0 ? (
                        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 mb-12">
                            {favoritos.map((fav) => (
                                <div
                                    key={fav.id}
                                    className="bg-white rounded-2xl shadow-lg border border-gray-100 hover:shadow-xl transition-shadow duration-200 p-6 flex flex-col justify-between"
                                >
                                    <div className="flex flex-col items-center text-center">
                                        <div
                                            className="w-24 h-24 flex items-center justify-center rounded-2xl shadow-md mb-4 text-5xl text-white select-none"
                                            style={{
                                                background:
                                                    fav.tipo === "Pizza"
                                                        ? "linear-gradient(135deg, #ffb84d, #ff7300)"
                                                        : "linear-gradient(135deg, #ffb84d, #ff7300)",
                                            }}
                                        >
                                            {fav.tipo === "Pizza" ? "🍕" : "🍔"}
                                        </div>
                                        <h2 className="font-bold text-xl text-gray-800 mb-1">
                                            {fav.nombre}
                                        </h2>
                                        <p className="text-orange-600 font-semibold text-lg mb-3">
                                            ${fav.precio}
                                        </p>
                                    </div>

                                    <div className="flex flex-col gap-2">
                                        <button
                                            onClick={() =>
                                                navigate("/verCreacion", { state: { creationId: fav.id } })
                                            }
                                            className="bg-gray-100 hover:bg-gray-200 text-gray-800 rounded-xl px-4 py-2 font-semibold text-sm transition"
                                        >
                                            Ver detalles
                                        </button>

                                        <BotonAgregarCarrito
                                            esPrincipal={true}
                                            item={fav}
                                        />
                                    </div>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <div className="flex flex-col items-center justify-center text-center py-20 text-gray-500">
                            <h2 className="text-2xl font-semibold mb-2">
                                Todavía no tenés favoritos
                            </h2>
                            <p className="text-sm text-gray-400">
                                Podés crear tus propias pizzas o hamburguesas y guardarlas aquí.
                            </p>
                            <button
                                onClick={() => navigate("/inicio")}
                                className="mt-6 bg-orange-500 text-white font-semibold rounded-2xl px-6 py-2 hover:bg-orange-600 transition"
                            >
                                Crear mi primera creación
                            </button>
                        </div>
                    )}
                </div>

                <PieDePagina />
            </div>
        </>
    );
}
