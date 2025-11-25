import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Encabezado from "../Components/Encabezado.jsx";
import PieDePagina from "../Components/PieDePagina.jsx";
import BotonChico from "../Components/BotonChico.jsx";
import AddToCartButton from "../Components/BotonAgregarCarrito.jsx";

export default function VerCreacion() {
    const id = useLocation().state?.creationId;
    const navigate = useNavigate();

    const [cargando, setCargando] = useState(true);
    const [creacion, setCreacion] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const obtenerCreacion = async () => {
            try {
                const respuesta = await fetch(`http://localhost:8080/api/favorito/${id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                    credentials: "include",
                });

                if (respuesta.ok) {
                    const datos = await respuesta.json();
                    setCreacion(datos);
                } else {
                    setError("No se pudo obtener la creación");
                }
            } catch (err) {
                setError("Error al obtener los detalles");
            } finally {
                setCargando(false);
            }
        };

        if (id) obtenerCreacion();
    }, [id]);

    const eliminarDeFavoritos = async () => {
        if (!id) return;

        const confirmar = window.confirm("¿Seguro que querés eliminar esta creación de tus favoritos?");
        if (!confirmar) return;

        try {
            const respuesta = await fetch(`http://localhost:8080/api/favorito/${id}/eliminar`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                credentials: "include",
            });

            if (respuesta.ok) {
                alert("Creación eliminada de favoritos correctamente.");
                navigate("/favoritos");
            } else {
                alert("No se pudo eliminar la creación de favoritos.");
            }
        } catch (error) {
            alert("Ocurrió un error al eliminar de favoritos.");
        }
    };

    if (cargando) {
        return (
            <div className="flex justify-center items-center h-screen text-lg font-semibold">
                Cargando detalles...
            </div>
        );
    }

    if (error || !creacion) {
        return (
            <div className="flex flex-col justify-center items-center h-screen text-red-500 text-lg font-semibold">
                {error || "No se encontró la creación"}
            </div>
        );
    }

    const { nombre, tipo, precio, ingredientes, ingredientesInvalidos = [], cantidadCarnes } = creacion;

    const icono = tipo === "Pizza" ? "🍕" : tipo === "Hamburguesa" ? "🍔" : "";

    let detalles;
    if (tipo === "Pizza") {
        const tamanio = ingredientes.filter((i) => i.tipo === "Tamanio");
        const masas = ingredientes.filter((i) => i.tipo === "Masa");
        const salsas = ingredientes.filter((i) => i.tipo === "Salsa");
        const toppings = ingredientes.filter((i) => i.tipo === "Topping");

        detalles = (
            <div className="flex flex-col gap-4 text-gray-700">
                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Tamaño:</h3>
                    <div className="flex flex-wrap gap-2">
                        {tamanio.map((m) => (
                            <span key={m.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
                                {m.nombre}
                            </span>
                        ))}
                    </div>
                </div>

                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Masa:</h3>
                    <div className="flex flex-wrap gap-2">
                        {masas.map((m) => (
                            <span key={m.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
                                {m.nombre}
                            </span>
                        ))}
                    </div>
                </div>

                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Salsa:</h3>
                    <div className="flex flex-wrap gap-2">
                        {salsas.map((s) => (
                            <span key={s.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
                                {s.nombre}
                            </span>
                        ))}
                    </div>
                </div>

                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Toppings:</h3>
                    <div className="flex flex-wrap gap-2">
                        {toppings.map((t) => (
                            <span key={t.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
                                {t.nombre}
                            </span>
                        ))}
                    </div>
                </div>
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
                            <span key={p.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
                                {p.nombre}
                            </span>
                        ))}
                    </div>
                </div>

                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Carne:</h3>
                    <div className="flex flex-wrap gap-2">
                        {carnes.map((c) => (
                            <span key={c.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
                                {cantidadCarnes || 1} x {c.nombre}
                            </span>
                        ))}
                    </div>
                </div>

                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Salsas:</h3>
                    <div className="flex flex-wrap gap-2">
                        {salsas.map((s) => (
                            <span key={s.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
                                {s.nombre}
                            </span>
                        ))}
                    </div>
                </div>

                <div>
                    <h3 className="font-semibold text-lg text-gray-800 mb-1">Ingredientes:</h3>
                    <div className="flex flex-wrap gap-2">
                        {extras.map((i) => (
                            <span key={i.idProducto} className="bg-orange-50 border border-orange-100 text-orange-700 rounded-lg px-3 py-1 text-sm shadow-sm">
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
            <Encabezado />

            <div className="sticky top-[70px] left-0 w-full flex justify-start px-10 py-2 z-30">
                <button
                    onClick={() => navigate("/favoritos")}
                    className="bg-gray-200 text-gray-800 font-semibold rounded-2xl px-5 py-2 shadow-md hover:bg-gray-300 transition-all text-lg"
                >
                    ← Volver a mis favoritos
                </button>
            </div>

            <main className="flex-grow flex flex-col justify-start items-center px-8 md:px-20 mt-6 mb-10">
                <div className="flex flex-col gap-6 bg-white shadow-xl rounded-3xl p-10 md:p-14 max-w-4xl w-full border border-gray-100 relative overflow-hidden">

                    <div className="absolute top-0 left-0 w-full h-32 bg-gradient-to-r from-orange-400 to-orange-600 rounded-t-3xl flex justify-center items-center text-6xl text-white shadow-inner">
                        {icono}
                    </div>

                    <div className="mt-36 flex flex-col justify-between gap-4">
                        <div className="text-center">
                            <h1 className="text-3xl font-extrabold text-gray-800">{nombre}</h1>
                            <p className="text-lg text-gray-500">{tipo}</p>
                        </div>

                        {ingredientesInvalidos.length > 0 && (
                            <div className="bg-red-50 border border-red-200 text-red-700 rounded-xl p-4">
                                <h3 className="font-semibold mb-2">Ingredientes no disponibles:</h3>
                                <div className="flex flex-wrap gap-2">
                                    {ingredientesInvalidos.map((inv) => (
                                        <span key={inv.idProducto} className="bg-red-100 text-red-700 border border-red-300 rounded-lg px-3 py-1 text-sm">
                                            {inv.nombre}
                                        </span>
                                    ))}
                                </div>
                            </div>
                        )}

                        <div className="border-t border-gray-200 my-2"></div>

                        <h2 className="text-2xl font-semibold text-gray-800">Detalles</h2>
                        {detalles}

                        <div className="text-center text-xl font-bold text-orange-600 mt-4 mb-2">
                            Precio: ${precio}
                        </div>

                        <div className="flex flex-row gap-4 justify-center mt-2">
                            <BotonChico
                                text="Eliminar de favoritos"
                                isPrimary={false}
                                onClick={eliminarDeFavoritos}
                            />

                            <AddToCartButton
                                isPrimary={true}
                                item={creacion}
                                disabled={ingredientesInvalidos.length > 0}
                            />
                        </div>
                    </div>
                </div>
            </main>

            <PieDePagina />
        </div>
    );
}
