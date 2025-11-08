import { useEffect, useState } from "react";
import { useCart } from "../Components/context/CartItems";
import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";
import SmallButton from "../Components/SmallButton";
import Button from "../Components/Button";

export default function NewOrder() {
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const { items, removeItem, addItem } = useCart();
    const total = items.reduce((sum, item) => sum + (item.precio || 0), 0);
    const [bebidasDisponibles, setBebidasDisponibles] = useState([]);
    const [loadingBebidas, setLoadingBebidas] = useState(true);

    useEffect(() => {
        const fetchBebidas = async () => {
            try {
                const res = await fetch("http://localhost:8080/api/producto/bebidas", {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                    credentials: "include",
                });
                if (!res.ok) throw new Error("Error al listar bebidas");
                const data = await res.json();
                setBebidasDisponibles(data || []);
            } catch {
                setBebidasDisponibles([]);
            } finally {
                setLoadingBebidas(false);
            }
        };
        fetchBebidas();
    }, []);

    const handleAddDrink = (bebida) => {
        const tieneCreaciones = items.some((i) => i.tipo !== "Bebida");
        if (!tieneCreaciones) {
            alert("Debes tener al menos una creación antes de agregar bebidas.");
            return;
        }
        if (!bebida || bebida.idProducto == null) return;
        const newDrink = {
            id: bebida.idProducto,
            idProducto: bebida.idProducto,
            tipo: "Bebida",
            nombre: bebida.nombre,
            precio: bebida.precio,
            sinTacc: bebida.sinTacc,
            estaActivo: bebida.estaActivo,
            visible: bebida.visible,
        };
        addItem(newDrink);
    };

    return (
        <>
            <MainHeader className="z-10" />
            <div className="flex flex-col pt-20 w-screen max-w-full min-h-[calc(100vh)] justify-between bg-gray-50">
                <div className="w-full flex flex-1 flex-col md:flex-row h-full items-start justify-between gap-8 px-6 md:px-16">
                    {/* Carrito principal */}
                    <div className="w-full md:w-2/3 flex flex-col items-center md:items-start">
                        <h1 className="text-3xl font-extrabold text-gray-800 mb-6 mt-2">
                            Tu carrito
                        </h1>
                        {items.length === 0 ? (
                            <div className="flex flex-col items-center justify-center text-center bg-white shadow-md rounded-2xl p-8 w-full md:w-2/3">
                                <h2 className="text-gray-600 mb-4">
                                    No hay artículos en tu carrito todavía.
                                </h2>
                                <Button
                                    text="Volver a la página principal"
                                    isPrimary={true}
                                    route="/homepage"
                                />
                            </div>
                        ) : (
                            <div className="w-full flex flex-col gap-4">
                                {/* 🔥 BLOQUE CORREGIDO */}
                                {items.map((item) => (
                                    <div
                                        key={`${item.tipo}-${item.id || item.idProducto}`}
                                        className="bg-white p-5 rounded-2xl shadow-md hover:shadow-lg transition-all
                                                   flex justify-between items-center flex-wrap gap-4 border border-gray-100"
                                    >
                                        <div className="flex flex-col gap-1 flex-1 min-w-[200px]">
                                            <h2 className="text-xl font-bold text-gray-800 break-words">
                                                {item.nombre}
                                            </h2>

                                            {item.tipo && (
                                                <p className="text-sm text-gray-500">
                                                    Tipo:{" "}
                                                    <span className="text-gray-700">
                                                        {item.tipo}
                                                    </span>
                                                </p>
                                            )}

                                            {item.ingredientes && (
                                                <p className="text-sm text-gray-500 max-w-full break-words">
                                                    Ingredientes:{" "}
                                                    <span className="text-gray-700">
                                                        {Array.isArray(item.ingredientes)
                                                            ? item.ingredientes
                                                                .map(
                                                                    (i) =>
                                                                        i.nombre ||
                                                                        i.producto?.nombre
                                                                )
                                                                .join(", ")
                                                            : item.ingredientes}
                                                    </span>
                                                </p>
                                            )}

                                            <p className="text-base font-semibold text-orange-600 mt-1">
                                                ${item.precio}
                                            </p>
                                        </div>

                                        <button
                                            onClick={() =>
                                                removeItem(item.id || item.idProducto)
                                            }
                                            className="bg-red-100 hover:bg-red-200 text-red-600 font-semibold
                                                       px-3 py-1 rounded-xl transition-all text-sm flex-shrink-0"
                                        >
                                            Quitar
                                        </button>
                                    </div>
                                ))}

                                {/* Agregar bebida */}
                                <div className="mt-6 bg-white p-6 rounded-2xl shadow-md border border-gray-100 flex flex-col gap-4">
                                    <h3 className="font-semibold text-lg text-gray-800">
                                        Agregar bebida
                                    </h3>
                                    {loadingBebidas ? (
                                        <p className="text-gray-600 text-sm">Cargando bebidas...</p>
                                    ) : bebidasDisponibles.length === 0 ? (
                                        <p className="text-gray-600 text-sm">
                                            No hay bebidas disponibles.
                                        </p>
                                    ) : (
                                        <>
                                            <p className="text-gray-600 text-sm">
                                                Elegí una bebida para acompañar tu pedido:
                                            </p>
                                            <div className="flex flex-wrap gap-3">
                                                {bebidasDisponibles.map((b) => (
                                                    <button
                                                        key={`bebida-${b.idProducto}`}
                                                        className="flex flex-col items-center px-4 py-2 bg-orange-50 border border-orange-200 text-orange-600 font-semibold rounded-xl hover:bg-orange-100 transition-all text-sm"
                                                        onClick={() => handleAddDrink(b)}
                                                    >
                                                        {b.nombre}
                                                        <span className="text-xs font-normal text-gray-500 mt-1">
                                                            ${b.precio}
                                                        </span>
                                                    </button>
                                                ))}
                                            </div>
                                        </>
                                    )}
                                </div>
                            </div>
                        )}
                    </div>

                    {/* Resumen de pedido */}
                    <div className="w-full md:w-1/3 bg-white shadow-xl rounded-2xl p-6 border border-gray-100 sticky top-24 self-start">
                        <h1 className="font-bold text-2xl mb-4 text-gray-800">
                            Resumen de pedido
                        </h1>
                        {items.length === 0 ? (
                            <p className="text-gray-500 text-center mt-10">
                                No hay artículos en el carrito
                            </p>
                        ) : (
                            <>
                                <div className="flex flex-col">
                                    {items.map((item) => (
                                        <div
                                            key={`resumen-${item.tipo}-${item.id || item.idProducto}`}
                                            className="flex justify-between py-1"
                                        >
                                            <span className="text-gray-700 font-medium">
                                                {item.nombre}
                                            </span>
                                            <span className="text-gray-700 font-medium">
                                                ${item.precio}
                                            </span>
                                        </div>
                                    ))}
                                </div>
                                <div className="flex flex-row justify-between items-center pt-6 border-t mt-4">
                                    <h2 className="font-bold text-xl text-gray-800">Total:</h2>
                                    <h2 className="font-bold text-xl text-orange-600">
                                        ${total}
                                    </h2>
                                </div>
                                <div className="flex flex-col md:flex-row justify-center items-center gap-4 mt-6">
                                    <SmallButton
                                        text="Seguir comprando"
                                        isPrimary={false}
                                        route="/homepage"
                                    />
                                    <SmallButton
                                        text="Pasar al pago"
                                        isPrimary={true}
                                        route="/checkout"
                                    />
                                </div>
                            </>
                        )}
                    </div>
                </div>
                <Footer />
            </div>
        </>
    );
}
