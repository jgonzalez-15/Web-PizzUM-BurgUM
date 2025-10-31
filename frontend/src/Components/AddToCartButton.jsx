import { useCart } from "./context/CartItems";

export default function AddToCartButton({item, isPrimary, handle}){
    const { addItem, cartId } = useCart();

    const use = async () => {
    let nueva = item;
    if (!nueva && handle) {
      nueva = await handle(false); // esFavorita = false
    }
    if (nueva) {
      const { idCreacion, ...resto } = nueva;
      const newItem = {
        ...resto,
        id: idCreacion,
        cartId
      };
      addItem(newItem);
    }
  };

  return(
    <button className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 
    rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base text-center 
    ${isPrimary ? "bg-orange-400 text-white" : "bg-gray-300 text-black"}`} 
    onClick={() => {use(), addItem(newItem)}}>
      <h2 className="m-2">
        Agregar al Carrito
      </h2>
    </button>
  )
}