import { Link } from "react-router-dom";
import { useState } from "react";
import { usarCarrito } from "../Contextos/ContextoCarrito.jsx";
import BarraLateral from "./BarraLateral.jsx";
import BotonChico from "./BotonChico.jsx";

import carritoImg from "../Recursos/carrito.png";


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
                        to="/paginaPrincipal"
                        className="text-center font-extrabold text-2xl text-gray-800 hover:text-orange-500 transition-colors"
                    >
                        Pizz<span className="text-orange-500">UM</span> & Burg
                        <span className="text-orange-500">UM</span>
                    </Link>
                </header>

                {/* Botón menú lateral */}
                <div
                    className="m-1 h-12 w-12 bg-gray-100 hover:bg-gray-200 fixed top-1 left-1 flex items-center justify-center rounded-lg shadow-sm transition-all cursor-pointer z-30"
                    onClick={() => {
                        setMenuAbierto(!menuAbierto);
                        setMostrarCarrito(false);
                    }}
                >
                    <button className="text-2xl font-bold text-gray-700">
                        {menuAbierto ? "✕" : "☰"}
                    </button>
                </div>

                {/* BarraLateral */}
                <div
                    className={`fixed top-16 left-0 h-screen bg-white shadow-2xl rounded-r-2xl border-r border-gray-200 transform transition-transform duration-300 z-20 ${
                        menuAbierto ? "translate-x-0" : "-translate-x-full"
                    }`}
                >
                    <BarraLateral />
                </div>
            </div>
        </>
    );
}

export default Encabezado;
