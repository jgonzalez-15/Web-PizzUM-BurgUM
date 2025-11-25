import { usarCarrito } from "../Contextos/ContextoCarrito.jsx";

export default function BotonAgregarCarrito({ item, esPrincipal = true, handle, disabled }) {
    const { agregarItem } = usarCarrito();

    const agregar = async () => {
        if (disabled) return;

        let nuevo = item;

        if (handle && (!nuevo || !nuevo.idCreacion)) {
            nuevo = await handle(false);
        }

        if (nuevo) {
            const { idCreacion, ingredientes, ...resto } = nuevo;

            let nombresIngredientes = "";
            if (Array.isArray(ingredientes)) {
                nombresIngredientes = ingredientes
                    .map((i) => i.producto?.nombre || i.nombre)
                    .filter(Boolean)
                    .join(", ");
            } else if (typeof ingredientes === "string") {
                nombresIngredientes = ingredientes;
            }

            let tipo = resto.tipo;
            if (!tipo) {
                if (nuevo.tamanio) tipo = "Pizza";
                else if (nuevo.cantCarnes) tipo = "Hamburguesa";
                else tipo = "Creación";
            }

            const nuevoItem = {
                ...resto,
                id: idCreacion,
                ingredientes: nombresIngredientes,
                tipo,
            };

            agregarItem(nuevoItem);
            alert(`${nuevoItem.nombre} agregado al carrito`);
        }
    };

    const clasesBase = `
        w-full transition-transform duration-150 ease-in-out hover:scale-105
        font-bold text-lg rounded-2xl shadow-md
        px-8 py-4 min-w-[230px] text-center tracking-wide
    `;

    const clasesColor = esPrincipal
        ? "bg-orange-500 text-white hover:bg-orange-600"
        : "bg-gray-200 text-gray-800 hover:bg-gray-300";

    return (
        <button
            onClick={!disabled ? agregar : undefined}
            disabled={disabled}
            className={`${clasesBase} ${clasesColor} ${disabled ? "opacity-50 cursor-not-allowed hover:scale-100" : ""}`}
        >
            {disabled ? "Producto no disponible" : "Agregar al carrito"}
        </button>
    );
}
