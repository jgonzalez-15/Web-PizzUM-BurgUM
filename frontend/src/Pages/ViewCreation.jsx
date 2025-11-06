import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";
import SmallButton from "../Components/SmallButton";
import AddToCartButton from "../Components/AddToCartButton";

export default function ViewCreation() {
    const id = useLocation().state?.creationId;
    const navigate = useNavigate();

    const [loading, setLoading] = useState(true);
    const [creation, setCreation] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCreation = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/favorito/${id}`, {
                    method: "GET",
                    credentials: "include",
                });

                if (response.ok) {
                    const data = await response.json();
                    setCreation(data);
                } else {
                    setError("No se pudo obtener la creación");
                }
            } catch (err) {
                console.error("Error al obtener los detalles:", err);
                setError("Error al obtener los detalles");
            } finally {
                setLoading(false);
            }
        };

        if (id) fetchCreation();
    }, [id]);

    if (loading) {
        return (
            <div className="flex justify-center items-center h-screen text-lg font-semibold">
                Cargando detalles...
            </div>
        );
    }

    if (error || !creation) {
        return (
            <div className="flex flex-col justify-center items-center h-screen text-red-500 text-lg font-semibold">
                {error || "No se encontró la creación"}
            </div>
        );
    }

    const { nombre, tipo, precio, ingredientes, tamanio, cantidadCarnes } = creation;

    const icono =
        tipo === "Pizza"
            ? "🍕"
            : tipo === "Hamburguesa"
                ? "🍔"
                : "⭐";

    let detalles;
    if (tipo === "Pizza") {
        const masas = ingredientes.filter((i) => i.tipo === "Masa");
        const salsas = ingredientes.filter((i) => i.tipo === "Salsa");
        const toppings = ingredientes.filter((i) => i.tipo === "Topping");

        detalles = (
            <div className="flex flex-col gap-4 text-gray-700">
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Masa:</h3>
                    <div className="flex flex-wrap gap-2">
                        {masas.map((m) => (
                            <span
                                key={m.idProducto}
                                className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm"
                            >
                                {m.nombre}
                            </span>
                        ))}
                    </div>
                </div>
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Salsa:</h3>
                    <div className="flex flex-wrap gap-2">
                        {salsas.map((s) => (
                            <span
                                key={s.idProducto}
                                className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm"
                            >
                                {s.nombre}
                            </span>
                        ))}
                    </div>
                </div>
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Toppings:</h3>
                    <div className="flex flex-wrap gap-2">
                        {toppings.map((t) => (
                            <span
                                key={t.idProducto}
                                className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm"
                            >
                                {t.nombre}
                            </span>
                        ))}
                    </div>
                </div>
                <p className="mt-2 text-gray-600 italic">
                    Tamaño:{" "}
                    <span className="font-medium text-gray-700 not-italic">{tamanio}</span>
                </p>
            </div>
        );
    } else if (tipo === "Hamburguesa") {
        const panes = ingredientes.filter((i) => i.tipo === "Pan");
        const carnes = ingredientes.filter((i) => i.tipo === "Hamburguesa");
        const salsas = ingredientes.filter((i) => i.tipo === "Salsa_Hamburguesa");
        const extras = ingredientes.filter((i) => i.tipo === "Ingrediente");

        detalles = (
            <div className="flex flex-col gap-4 text-gray-700">
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Pan:</h3>
                    <div className="flex flex-wrap gap-2">
                        {panes.map((p) => (
                            <span
                                key={p.idProducto}
                                className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm"
                            >
                                {p.nombre}
                            </span>
                        ))}
                    </div>
                </div>
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Carne:</h3>
                    <div className="flex flex-wrap gap-2">
                        {carnes.map((c) => (
                            <span
                                key={c.idProducto}
                                className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm"
                            >
                                {cantidadCarnes || 1}x {c.nombre}
                            </span>
                        ))}
                    </div>
                </div>
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Salsas:</h3>
                    <div className="flex flex-wrap gap-2">
                        {salsas.map((s) => (
                            <span
                                key={s.idProducto}
                                className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm"
                            >
                                {s.nombre}
                            </span>
                        ))}
                    </div>
                </div>
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Ingredientes:</h3>
                    <div className="flex flex-wrap gap-2">
                        {extras.map((i) => (
                            <span
                                key={i.idProducto}
                                className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm"
                            >
                                {i.nombre}
                            </span>
                        ))}
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="flex flex-col min-h-screen">
            <MainHeader />

            {/* Botón fijo debajo del header */}
            <div className="sticky top-[70px] left-0 w-full flex justify-start px-10 py-2 z-30">
                <button
                    onClick={() => navigate("/favoritos")}
                    className="bg-orange-400 text-white font-bold rounded-2xl px-5 py-2 shadow-xl hover:scale-105 transition-transform text-lg"
                >
                    ← Volver a mis favoritos
                </button>
            </div>

            {/* Contenido flexible */}
            <main className="flex-grow flex flex-col justify-start items-center px-8 md:px-20 mt-6 mb-10">
                <div className="flex flex-col gap-6 bg-white shadow-xl rounded-3xl p-10 md:p-14 max-w-4xl w-full border border-gray-100 relative overflow-hidden">

                    {/* Encabezado visual */}
                    <div className="absolute top-0 left-0 w-full h-32 bg-gradient-to-r from-orange-400 to-orange-600 rounded-t-3xl flex justify-center items-center text-6xl text-white shadow-inner">
                        {icono}
                    </div>

                    {/* Contenido principal */}
                    <div className="mt-36 flex flex-col justify-between gap-4">
                        <div className="text-center">
                            <h1 className="text-3xl font-extrabold text-gray-800">{nombre}</h1>
                            <p className="text-lg text-gray-500">{tipo}</p>
                        </div>

                        <div className="border-t border-gray-200 my-2"></div>

                        <h2 className="text-2xl font-semibold text-gray-800">Detalles</h2>
                        {detalles}

                        <div className="text-center text-xl font-bold text-orange-600 mt-4 mb-2">
                            Precio: ${precio}
                        </div>

                        <div className="flex flex-row gap-4 justify-center mt-2">
                            <SmallButton text="Eliminar de favoritos" isPrimary={false} />
                            <AddToCartButton isPrimary={true} item={creation} />
                        </div>
                    </div>
                </div>
            </main>

            <Footer />
        </div>
    );
}
