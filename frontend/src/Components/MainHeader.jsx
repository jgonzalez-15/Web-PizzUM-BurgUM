import { Link } from "react-router-dom";
import { useState } from "react";
import { useCart } from "./context/CartItems";

import Sidebar from "./Sidebar"
import SmallButton from "./SmallButton";


function MainHeader(){
    const [open, setOpen] = useState(false);
    const [showCart, setShowCart] = useState(false);
    const { items } = useCart()
    const { removeItem } = useCart()

    return (
        <>
        <div className="fixed z-10">
            <header className="flex justify-center items-center fixed top-0 w-full max-w-screen h-16 bg-white border-b-[1px] border-gray-300">
                    <Link to='/homepage' className="text-center font-bold text-2xl m-2">
                        PizzUM & BurgUM
                    </Link>
            </header>
            <div className="z-10 m-2 h-12 w-12 bg-gray-100 fixed top-0 left-0 flex items-center justify-center rounded-sm" onClick={()=>{setOpen(!open), setShowCart(false)}}>
                <button className="flex items-center justify-center">
                    {open && (<h1 className="text-center">X</h1>)}
                    {!open && (<h1 className="text-center">☰</h1>)}
                </button>
            </div>
            <div className="z-10 m-2 h-12 w-12 fixed top-0 right-0 rounded-sm flex items-center justify-center" onClick={()=>{setShowCart(!showCart), setOpen(false)}}>
                <div className="h-8 w-8 bg-[url('src/assets/carrito.png')] bg-center bg-contain bg-no-repeat"/>
            </div>
            {open && (
                <div className="h-screen w-screen fixed top-16 left-0 bg-black/25" onClick={()=>{setShowCart(false), setOpen(false)}}/>
            )}
            {showCart && (
            <>
                <div className="h-screen w-screen fixed top-16 left-0 bg-black/25 z-10" onClick={() => { setShowCart(false); setOpen(false); }}/>
                <div className="fixed top-16 right-0 h-[70vh] w-64 bg-white rounded-b-2xl rounded-l-2xl z-20 flex flex-col">
                <div className="p-4 border-b border-gray-300">
                    <h1 className="font-bold">Tu carrito:</h1>
                </div>
                <div className="flex-1 overflow-y-auto px-4 py-2">
                    {items.length === 0 && (
                    <p className="text-gray-500 text-sm italic text-center mt-2">
                        Tu carrito está vacío
                    </p>
                    )}
                    {items.map((item) => (
                    <div key={item.id} className="flex flex-row justify-between items-center py-1 border-b border-gray-100">
                        <h1>● {item.nombre}</h1>
                        <button className="h-6 w-6 rounded-sm font-bold text-red-600" onClick={() => removeItem(item.id)}>
                        X
                        </button>
                    </div>
                    ))}
                </div>
                <div className="p-4 border-t border-gray-300 flex justify-center">
                    <SmallButton text="Ver tu pedido" route="/order" isPrimary={true} />
                </div>
                </div>
            </>
            )}
            <div className={`fixed top-16 left-0 h-screen transform transition-transform duration-300 ${open ? "translate-x-0" : "-translate-x-full"}`}>
                <Sidebar/>
            </div>
        </div>
        </>
    );
}

export default MainHeader