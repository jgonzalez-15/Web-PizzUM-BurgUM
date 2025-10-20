import { Link } from "react-router-dom";
import Button from "./Button";
import Sidebar from "./Sidebar"
import { useState } from "react";
import SmallButton from "./SmallButton";
import { useCart } from "./context/CartItems";

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
            <div className="z-10 m-2 h-12 w-12 fixed top-0 right-0 flex items-center justify-center rounded-sm" onClick={()=>{setShowCart(!showCart), setOpen(false)}}>
                ⨿
            </div>
            {open && (
                <div className="h-screen w-screen fixed top-16 left-0 bg-black/25" onClick={()=>{setShowCart(false), setOpen(false)}}/>
            )}
            {showCart && (
                <>
                <div className="h-screen w-screen fixed top-16 left-0 bg-black/25" onClick={()=>{setShowCart(false), setOpen(false)}}/>
                <div className="fixed top-16 right-0 h-64 w-64 bg-white flex flex-col justify-between rounded-b-2xl rounded-l-2xl">
                    <div className="m-4 mb-0">
                        <div className="w-full">
                            <h1 className="font-bold">Tu carrito:</h1>
                            <div className="overflow-auto h-36 w-64 mt-4">
                            {
                                items.map((item) => (
                                    <div key={item.id} className="m-2 mt-0 mr-8 md:m-4 flex flex-row justify-between items-center">
                                        <h1>● {item.name}</h1>
                                        <button className="h-6 w-6 rounded-sm font-bold text-red-600" onClick={() => removeItem(item.id)}>X</button>
                                    </div>
                                ))
                            }
                            </div>
                        </div>
                    </div>
                    <SmallButton text="Ver tu pedido" isPrimary={true} route="/order"/>
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