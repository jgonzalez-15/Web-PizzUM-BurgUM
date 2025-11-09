import { Link } from "react-router-dom";
import { useContext, useState, useEffect } from "react";
import { SessionContext } from "../Components/context/SessionContext";

import Boton from "../Components/Boton.jsx";
import Favourite from "../Components/Favorito.jsx";
import Encabezado from "../Components/Encabezado.jsx";
import PieDePagina from "../Components/PieDePagina.jsx";

export default function PaginaPrincipal() {
    if (window.pageYOffset > 0) window.scrollTo(0, 0);

    const [listaFavoritos, setListaFavoritos] = useState([]);
    const { sessionType } = useContext(SessionContext);

    const obtenerFavoritos = async () => {
        try {
            const respuesta = await fetch(`http://localhost:8080/api/favorito/listar`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                credentials: "include",
            });

            if (respuesta.ok) {
                const data = await respuesta.json();
                setListaFavoritos(data);
            } else {
                alert("Ocurrió un error al obtener los productos");
            }
        } catch (error) {
            console.error("Error al obtener productos:", error);
        }
    };

    useEffect(() => {
        if (sessionType === "CLIENTE") {
            obtenerFavoritos();
        }
    }, [sessionType]);

    return (
        <div className="min-h-screen flex flex-col justify-between bg-gray-50">
            <Encabezado />

            {/* Sección principal */}
            <section className="relative flex flex-col items-center justify-center text-center w-full h-[70vh] bg-[url('https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2024/01/09/pizzas-y-hamburguesas.jpeg')] bg-cover bg-center bg-black/50 bg-blend-multiply">
                <div className="px-6">
                    <h1 className="text-white font-extrabold text-3xl md:text-5xl mb-4 drop-shadow-lg">
                        Creá tu próxima <span className="text-orange-400">PizzUM</span> o{" "}
                        <span className="text-orange-400">BurgUM</span>
                    </h1>
                    <p className="text-gray-200 text-lg md:text-xl mb-6 max-w-2xl mx-auto">
                        Diseñá tu creación ideal con ingredientes frescos y combinaciones únicas.
                    </p>

                    <div className="flex flex-col md:flex-row gap-4 justify-center">
                        <Boton texto="Crear PizzUM" esPrincipal={true} ruta="/design/pizza" />
                        <Boton texto="Crear BurgUM" esPrincipal={false} ruta="/design/burger" />
                    </div>
                </div>
            </section>

            {/* Favoritos */}
            <section className="flex flex-col mt-12 mb-16 px-6 md:px-16">
                <div className="flex justify-between items-center mb-4">
                    <h2 className="text-2xl font-bold text-gray-800">Tus favoritos</h2>
                    {listaFavoritos.length > 0 && (
                        <Link
                            to="/favoritos"
                            className="text-orange-500 font-semibold hover:underline transition-colors"
                        >
                            Ver todos
                        </Link>
                    )}
                </div>

                {listaFavoritos.length > 0 ? (
                    <div className="flex flex-row overflow-x-auto space-x-4 pb-4 scrollbar-thin scrollbar-thumb-orange-400 scrollbar-track-gray-200">
                        {listaFavoritos.map((favorito) => (
                            <Favourite
                                key={favorito.id || favorito.idCreacion || favorito.nombre}
                                title={favorito.nombre}
                                favorito={favorito}
                            />
                        ))}
                    </div>
                ) : (
                    <p className="text-gray-500 text-center italic">
                        No tienes favoritos agregados todavía.
                    </p>
                )}
            </section>

            <PieDePagina />
        </div>
    );
}
