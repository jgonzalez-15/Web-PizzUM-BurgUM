import { useCart } from "../Components/context/CartItems";

export default function AddToCartButton({ item, isPrimary, handle }) {
    const { addItem, cartId } = useCart();

    const handleAdd = async () => {
        let nueva = item;

        if (handle && (!nueva || !nueva.idCreacion)) {
            nueva = await handle(false);
        }

        if (nueva) {
            const { idCreacion, ingredientes, ...resto } = nueva;

            let nombresIngredientes = "";
            if (Array.isArray(ingredientes)) {
                nombresIngredientes = ingredientes
                    .map(i => i.producto?.nombre || i.nombre)
                    .filter(Boolean)
                    .join(", ");
            } else if (typeof ingredientes === "string") {
                nombresIngredientes = ingredientes;
            }

            let tipo = resto.tipo;
            if (!tipo) {
                if (nueva.tamanio) tipo = "Pizza";
                else if (nueva.cantCarnes) tipo = "Hamburguesa";
            }

            const newItem = {
                ...resto,
                id: idCreacion,
                ingredientes: nombresIngredientes,
                tipo,
                cartId,
            };

            addItem(newItem);
            alert(`${newItem.nombre} agregado al carrito`);
        }
    };

    return (
        <button
            className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 
      rounded-2xl shadow-sm font-bold m-1 text-sm md:text-base text-center 
      ${isPrimary ? "bg-orange-400 text-white" : "bg-gray-300 text-black"}`}
            onClick={handleAdd}
        >
            <h2 className="m-2">Agregar al Carrito</h2>
        </button>
    );
}
