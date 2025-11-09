import { Link } from "react-router-dom";

function Boton({ texto, esPrincipal, ruta, alHacerClick, tamaño = "grande" }) {
    const estilosBase = `
    transition-transform duration-150 ease-in-out hover:scale-105 
    rounded-2xl shadow-md font-bold m-1 text-center 
    ${tamaño === "grande" ? "text-lg md:text-xl px-6 py-3" : "text-sm md:text-base px-4 py-2"}
    ${esPrincipal ? "bg-orange-500 text-white hover:bg-orange-600" : "bg-gray-300 text-gray-800 hover:bg-gray-400"}
  `;

    if (ruta) {
        return (
            <Link to={ruta} className={estilosBase}>
                {texto}
            </Link>
        );
    }

    return (
        <button onClick={alHacerClick} className={estilosBase}>

            {texto}
        </button>
    );
}

export default Boton;
