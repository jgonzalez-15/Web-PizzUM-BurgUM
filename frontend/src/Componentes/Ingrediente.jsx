export default function Ingrediente({ nombre, precio, seleccionado, alternarSeleccionado, sinTacc }) {
    const seleccionar = (e) => {
        e.preventDefault();
        alternarSeleccionado();
    };

    return (
        <div className="m-2 min-w-24 min-h-24 md:min-w-48 md:min-h-48 flex items-center justify-center">
            <div
                className={`relative h-24 w-24 md:h-48 md:w-48 rounded-2xl flex flex-col justify-between items-center cursor-pointer transition-all duration-200 
        ${seleccionado
                    ? "border-4 border-orange-400 bg-orange-50 shadow-md scale-105"
                    : "bg-gray-50 hover:bg-gray-100 hover:shadow-sm"}`}
                onClick={seleccionar}
            >
                <div className="flex-1 flex items-center justify-center px-2 text-center">
                    <h1 className="font-bold text-gray-800 text-xs md:text-lg break-words leading-tight">
                        {nombre}
                    </h1>
                </div>

                <div className="mb-2">
                    <h2 className="text-sm md:text-lg font-semibold text-orange-600">
                        ${precio}
                    </h2>
                </div>
            </div>
        </div>
    );
}
