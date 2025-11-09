import { Link } from "react-router-dom";
import { useState } from "react";
import { usarCarrito } from "./context/CarritoContexto.jsx";
import BarraLateral from "./BarraLateral.jsx";
import SmallButton from "./SmallButton";

function Encabezado() {
    const [menuAbierto, setMenuAbierto] = useState(false);
    const [mostrarCarrito, setMostrarCarrito] = useState(false);
    const { items, eliminarItem } = usarCarrito();

    return (
        <>
            {/* Contenedor principal */}
            <div className="fixed z-20 w-full">
                {/* Encabezado */}
                <header className="flex justify-center items-center fixed top-0 w-full h-16 bg-white border-b border-gray-300 shadow-md">
                    <Link
                        to="/homepage"
                        className="text-center font-extrabold text-2xl text-gray-800 hover:text-orange-500 transition-colors"
                    >
                        Pizz<span className="text-orange-500">UM</span> & Burg
                        <span className="text-orange-500">UM</span>
                    </Link>
                </header>

                {/* Botón menú lateral */}
                <div
                    className="m-2 h-12 w-12 bg-gray-100 hover:bg-gray-200 fixed top-1 left-1 flex items-center justify-center rounded-lg shadow-sm transition-all cursor-pointer z-30"
                    onClick={() => {
                        setMenuAbierto(!menuAbierto);
                        setMostrarCarrito(false);
                    }}
                >
                    <button className="text-2xl font-bold text-gray-700">
                        {menuAbierto ? "✕" : "☰"}
                    </button>
                </div>

                {/* Botón carrito */}
                <div
                    className="m-2 h-12 w-12 fixed top-1 right-1 rounded-lg flex items-center justify-center hover:bg-gray-200 transition-all cursor-pointer shadow-sm z-30"
                    onClick={() => {
                        setMostrarCarrito(!mostrarCarrito);
                        setMenuAbierto(false);
                    }}
                >
                    <div className="relative">
                        <div className="h-8 w-8 bg-[url('src/assets/carrito.png')] bg-center bg-contain bg-no-repeat" />
                        {items.length > 0 && (
                            <span className="absolute -top-1 -right-2 bg-orange-500 text-white text-xs font-bold rounded-full px-1.5 py-0.5">
                {items.length}
              </span>
                        )}
                    </div>
                </div>

                {/* Fondo oscuro al abrir menú o carrito */}
                {(menuAbierto || mostrarCarrito) && (
                    <div
                        className="h-screen w-screen fixed top-16 left-0 bg-black/25 z-10 transition-opacity duration-200"
                        onClick={() => {
                            setMostrarCarrito(false);
                            setMenuAbierto(false);
                        }}
                    />
                )}

                {/* BarraLateral */}
                <div
                    className={`fixed top-16 left-0 h-screen bg-white shadow-2xl rounded-r-2xl border-r border-gray-200 transform transition-transform duration-300 z-20 ${
                        menuAbierto ? "translate-x-0" : "-translate-x-full"
                    }`}
                >
                    <BarraLateral />
                </div>

                {/* Carrito lateral */}
                {mostrarCarrito && (
                    <div className="fixed top-16 right-0 h-[70vh] w-80 bg-white rounded-l-3xl rounded-b-3xl shadow-2xl z-20 flex flex-col overflow-hidden border-l border-gray-200 animate-slide-in">
                        {/* Encabezado */}
                        <div className="p-4 border-b border-gray-300 bg-gradient-to-r from-orange-100 to-orange-50">
                            <h1 className="font-bold text-lg text-gray-800 text-center">
                                Tu carrito
                            </h1>
                        </div>

                        {/* Lista de items */}
                        <div className="flex-1 overflow-y-auto px-4 py-3">
                            {items.length === 0 ? (
                                <p className="text-gray-500 text-sm italic text-center mt-4">
                                    Tu carrito está vacío.
                                </p>
                            ) : (
                                items.map((item) => {
                                    const icono =
                                        item.tipo === "Pizza"
                                            ? "🍕"
                                            : item.tipo === "Hamburguesa"
                                                ? "🍔"
                                                : item.tipo === "Bebida"
                                                    ? "🥤"
                                                    : "";

                                    return (
                                        <div
                                            key={item.id || item.idProducto}
                                            className="flex items-center justify-between bg-orange-50/40 hover:bg-orange-100
                                 transition-all p-3 mb-2 rounded-2xl shadow-sm border border-orange-100"
                                        >
                                            <div className="flex items-center gap-3 overflow-hidden">
                                                <div className="text-2xl">{icono}</div>
                                                <div className="flex flex-col truncate">
                          <span className="font-semibold text-gray-800 truncate">
                            {item.nombre}
                          </span>
                                                    {item.precio && (
                                                        <span className="text-sm text-orange-600 font-medium">
                              ${item.precio}
                            </span>
                                                    )}
                                                </div>
                                            </div>

                                            <button
                                                className="text-red-500 hover:text-red-600 text-lg font-bold transition-transform hover:scale-110"
                                                onClick={() =>
                                                    eliminarItem(item.id || item.idProducto)
                                                }
                                                title="Eliminar"
                                            >
                                                ✕
                                            </button>
                                        </div>
                                    );
                                })
                            )}
                        </div>

                        {/* Botón para ver pedido */}
                        <div className="p-4 border-t border-gray-300 flex justify-center bg-orange-50">
                            <SmallButton
                                text="Ver mi pedido"
                                route="/order"
                                isPrimary={true}
                            />
                        </div>
                    </div>
                )}
            </div>
        </>
    );
}

export default Encabezado;
