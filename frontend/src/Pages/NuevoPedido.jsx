import { useEffect, useState } from "react";
import { usarCarrito } from "../Components/context/ContextoCarrito.jsx";

import EncabezadoPrincipal from "../Components/Encabezado.jsx";
import PieDePagina from "../Components/PieDePagina.jsx";

export default function NuevoPedido() {
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const { items, eliminarItem, agregarItem } = usarCarrito();
    const total = items.reduce((sum, item) => sum + (item.precio || 0), 0);

    const [bebidas, setBebidas] = useState([]);
    const [cargandoBebidas, setCargandoBebidas] = useState(true);

    useEffect(() => {
        const obtenerBebidas = async () => {
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
                setBebidas(data || []);
            } catch {
                setBebidas([]);
            } finally {
                setCargandoBebidas(false);
            }
        };
        obtenerBebidas();
    }, []);

    const agregarBebida = (bebida) => {
        const tieneCreaciones = items.some((i) => i.tipo !== "Bebida");
        if (!tieneCreaciones) {
            alert("Primero debes tener al menos una creación antes de agregar bebidas");
            return;
        }
        if (!bebida || bebida.idProducto == null) return;

        const nuevaBebida = {
            id: bebida.idProducto,
            idProducto: bebida.idProducto,
            tipo: "Bebida",
            nombre: bebida.nombre,
            precio: bebida.precio,
            sinTacc: bebida.sinTacc,
            estaActivo: bebida.estaActivo,
            visible: bebida.visible,
        };

        agregarItem(nuevaBebida);
    };

    return (
        <>
            <EncabezadoPrincipal className="z-10" />

            <div className="flex flex-col pt-24 w-screen max-w-full min-h-[calc(100vh)] justify-between bg-gray-50">
                <div className="flex flex-col md:flex-row items-start justify-between gap-8 px-6 md:px-16 mb-10">
                    <div className="w-full md:w-2/3 flex flex-col items-center md:items-start">
                        <h1 className="text-3xl font-extrabold text-gray-800 mb-6">
                            Bebidas
                        </h1>
                        <div className="mb-6 bg-white p-6 rounded-2xl shadow-md border border-gray-100 flex flex-col gap-4">
                            <h3 className="font-semibold text-lg text-gray-800">
                                Agregar bebida
                            </h3>


                            {cargandoBebidas ? (
                                <p className="text-gray-600 text-sm">Cargando bebidas...</p>
                            ) : bebidas.length === 0 ? (
                                <p className="text-gray-600 text-sm">
                                    No hay bebidas disponibles por el momento.
                                </p>
                            ) : (
                                <>
                                    <p className="text-gray-600 text-sm">
                                        Elegí una bebida para acompañar tu pedido:
                                    </p>


                                    <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-4">
                                        {bebidas.map((b, index) => (
                                            <button
                                                key={`bebida-${b.idProducto || index}`}
                                                onClick={() => agregarBebida(b)}
                                                className="relative p-4 rounded-2xl bg-gradient-to-br from-orange-100 to-orange-200 border border-orange-300 shadow-md hover:shadow-xl transition-all hover:scale-[1.06] flex flex-col items-center text-center overflow-hidden group">
                                                <span className="text-orange-800 font-bold text-lg group-hover:scale-110 transition-transform"> {b.nombre}</span>
                                                <span className="text-gray-600 text-sm mt-1 group-hover:opacity-80">${b.precio}</span>
                                                <div className="absolute -bottom-6 -right-6 w-20 h-20 bg-orange-300 rounded-full opacity-30 blur-xl group-hover:opacity-50 transition-all"></div>
                                            </button>
                                        ))}
                                    </div>
                                </>
                            )}
                        </div>
                        <h1 className="text-3xl font-extrabold text-gray-800 mb-6">
                            Tu carrito
                        </h1>
                        {items.length === 0 ? (
                            <div className="flex flex-col items-center justify-center text-center bg-white shadow-xl rounded-2xl p-8 w-full md:w-2/3 border border-gray-200">
                                <h2 className="text-gray-600 mb-4">
                                    No hay artículos en tu carrito todavía.
                                </h2>
                                <button
                                    onClick={() => (window.location.href = "/homepage")}
                                    className="w-48 px-6 py-2.5 rounded-2xl bg-gradient-to-r from-orange-500 to-orange-600 text-white font-semibold shadow-md hover:shadow-lg transition-all duration-200 hover:scale-[1.05]"
                                >
                                    Volver al inicio
                                </button>
                            </div>
                        ) : (
                            <div className="w-full flex flex-col gap-4">
                                {items.map((item) => (
                                    <div
                                        key={item._uuid}
                                        className="bg-white p-5 rounded-2xl shadow-md hover:shadow-lg transition-all flex justify-between items-center flex-wrap gap-4 border border-gray-100"
                                    >
                                        <div className="flex flex-col gap-1 flex-1 min-w-[200px]">
                                            <h2 className="text-xl font-bold text-gray-800 break-words">
                                                {item.nombre}
                                            </h2>

                                            {item.tipo && (
                                                <p className="text-sm text-gray-500">
                                                    Tipo:{" "}
                                                    <span className="text-gray-700">{item.tipo}</span>
                                                </p>
                                            )}
                                            {item.ingredientes && (
                                                <p className="text-sm text-gray-500 max-w-full break-words">
                                                    Ingredientes:{" "}
                                                    <span className="text-gray-700">
                                                        {Array.isArray(item.ingredientes)
                                                            ? item.ingredientes
                                                                .filter((i) => i.tipo != "Tamanio")
                                                                .map((i) => i.nombre || i.producto?.nombre)
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
                                            onClick={() => eliminarItem(item._uuid)}
                                            className="w-28 px-4 py-2 rounded-2xl bg-red-100 text-red-600 font-semibold border border-red-200 hover:bg-red-200 transition-all hover:scale-[1.03]"
                                        >
                                            Quitar
                                        </button>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>

                    <div className="w-full md:w-1/3 bg-white shadow-xl rounded-2xl p-6 border border-gray-200 sticky top-24 self-start">
                        <h1 className="font-bold text-2xl mb-4 text-gray-800">
                            Resumen del pedido
                        </h1>

                        {items.length === 0 ? (
                            <p className="text-gray-500 text-center mt-10">
                                No hay artículos en el carrito.
                            </p>
                        ) : (
                            <>
                                <div className="flex flex-col">
                                    {items.map((item) => (
                                        <div
                                            key={`resumen-${item._uuid}`}
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
                                    <h2 className="font-bold text-xl text-orange-600">${total}</h2>
                                </div>

                                <div className="flex flex-col md:flex-row justify-between items-center gap-4 mt-6 w-full">

                                    <button
                                        onClick={() => (window.location.href = "/")}
                                        className="w-full md:w-40 px-6 py-2.5 rounded-2xl bg-white text-gray-700 border border-gray-300 font-semibold shadow-sm hover:shadow-md hover:bg-gray-100 transition-all duration-200 hover:scale-[1.03] overflow-hidden
"
                                    >
                                        Seguir comprando
                                    </button>

                                    <button
                                        onClick={() => (window.location.href = "/paginaPago")}
                                        className="w-full md:w-40 px-6 py-2.5 rounded-2xl bg-gradient-to-r from-orange-500 to-orange-600 text-white font-semibold shadow-md hover:shadow-lg transition-all duration-200 hover:scale-[1.05] overflow-hidden
"
                                    >
                                        Pasar al pago
                                    </button>

                                </div>

                            </>
                        )}
                    </div>
                </div>

                <PieDePagina />
            </div>
        </>
    );
}
