import { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { SessionContext } from "../Components/context/SessionContext";

import Footer from "../Components/Footer";
import MainHeader from "../Components/MainHeader";
import AddToCartButton from "../Components/AddToCartButton";

function Favoritos() {
    const [listaFavoritos, setListaFavoritos] = useState([]);
    const { sessionInfo } = useContext(SessionContext);
    const navigate = useNavigate();

    useEffect(() => {
        // Evita superposición con el header al cargar
        window.scrollTo(0, 0);
        obtenerFavoritos();
    }, []);

    const obtenerFavoritos = async () => {
        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/favorito/listar`,
                {
                    method: "GET",
                    headers: { "Content-Type": "application/json",
                        'Authorization': `Bearer ${localStorage.getItem("token")}`},
                    credentials: "include",
                }
            );

            if (respuesta.ok) {
                const datos = await respuesta.json();
                setListaFavoritos(datos);
            } else {
                alert("Ocurrió un error al obtener los productos");
            }
        } catch (error) {
            console.error("Error al obtener productos:", error);
        }
    };

    return (
        <>
            <MainHeader className="z-20 fixed top-0 left-0 w-full" />

            {/* Contenedor general con padding para no superponerse con el header */}
            <div className="pt-24 flex flex-col justify-between min-h-screen max-w-full bg-white">
                {/* Título principal con espacio */}
                <div className="mx-4 md:mx-8 bg-gray-100 rounded-xl shadow-sm p-4 text-gray-800 flex items-center justify-start">
                    <span className="text-3xl mr-3">⭐</span>
                    <h1 className="font-extrabold text-xl md:text-2xl tracking-tight">
                        Tus Favoritos
                    </h1>
                </div>

                {/* Lista */}
                <div className="mt-6 flex flex-col mx-4 md:mx-8 gap-4 mb-10">
                    {listaFavoritos.length > 0 ? (
                        listaFavoritos.map((favorito) => (
                            <div
                                key={favorito.id}
                                className="flex items-center bg-white rounded-2xl shadow-md p-4 md:p-6 border border-gray-100 hover:shadow-lg transition-shadow duration-200"
                            >
                                {/* Cuadro del emoji con degradado naranja más suave */}
                                <div
                                    className="flex items-center justify-center w-24 h-24 rounded-xl mr-4 text-4xl text-white shadow-md select-none"
                                    style={{
                                        background: "linear-gradient(135deg, #ffa94d, #ff7a00)",
                                    }}
                                >
                                    {favorito.tipo === "Pizza" ? "🍕" : "🍔"}
                                </div>

                                {/* Información */}
                                <div className="flex-1 flex flex-col justify-center">
                                    <h2 className="text-lg font-semibold text-gray-800">
                                        {favorito.nombre}
                                    </h2>
                                    <p className="text-orange-600 font-bold">${favorito.precio}</p>
                                </div>

                                {/* Acciones */}
                                <div className="flex flex-col gap-2">
                                    <button
                                        onClick={() =>
                                            navigate("/viewCreation", {
                                                state: { creationId: favorito.id },
                                            })
                                        }
                                        className="bg-gray-200 text-gray-800 rounded-full px-4 py-1 font-semibold text-sm hover:bg-gray-300 transition"
                                    >
                                        Detalles
                                    </button>

                                    {/* Reutilizo tu componente para que no se rompa el carrito */}
                                    <AddToCartButton isPrimary={true} item={favorito} />
                                </div>
                            </div>
                        ))
                    ) : (
                        <h2 className="text-center text-gray-500 text-lg mt-10">
                            No tienes favoritos agregados
                        </h2>
                    )}
                </div>

                <Footer />
            </div>
        </>
    );
}

export default Favoritos;
