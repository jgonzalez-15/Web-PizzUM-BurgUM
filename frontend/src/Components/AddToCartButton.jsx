import { useCart } from "./context/CartItems";

export default function AddToCartButton({item, isPrimary}){
    const { addItem, cartId } = useCart()
    const newItem = {...item, id:cartId}

    return(
        <button className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 
        rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center 
        ${isPrimary ? "bg-orange-400 text-white" : "bg-gray-300 text-black"}`} 
        onClick={() => addItem(newItem)}>
            <h2 className="m-2 2xl:m-3">
                Agregar al Carrito
            </h2>
        </button>
    )
}