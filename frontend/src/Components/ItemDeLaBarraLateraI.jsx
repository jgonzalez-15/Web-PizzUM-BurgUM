import { Link, useLocation } from "react-router-dom";

function ItemDeLaBarraLateraI({ texto, ruta }) {
    const ubicacion = useLocation();
    const activo = ubicacion.pathname === ruta;

    return (
        <Link
            to={ruta}
            className={`
        flex items-center justify-start gap-3
        px-6 py-3 mx-2 my-1 rounded-xl
        font-semibold text-gray-700 text-base md:text-lg
        transition-all duration-200
        ${activo ? "bg-orange-100 text-orange-700 shadow-sm" : "hover:bg-orange-50"}
      `}
        >
            <div
                className={`
          h-3 w-3 rounded-full border-2 
          ${activo ? "bg-orange-500 border-orange-500" : "border-gray-400"}
        `}
            ></div>
            <span>{texto}</span>
        </Link>
    );
}

export default ItemDeLaBarraLateraI;
