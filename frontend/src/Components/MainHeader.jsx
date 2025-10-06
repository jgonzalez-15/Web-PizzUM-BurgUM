import { Link } from "react-router-dom";
import Button from "./Button";
import Sidebar from "./Sidebar"
import { useState } from "react";

function MainHeader(){
    const [open, setOpen] = useState(false);

    return (
        <>
            <header className="flex justify-center items-center fixed top-0 w-full h-16 bg-white border-b-[1px] border-gray-300">
                    <Link to='/homepage' className="text-center font-bold text-2xl w-full m-2">
                        PizzUM & BurgUM
                    </Link>
            </header>
            <div className="z-10 m-2 h-12 w-12 bg-gray-100 fixed top-0 left-0 flex items-center justify-center rounded-sm" onClick={()=>setOpen(!open)}>
                <button className="flex items-center justify-center">
                    {open && (<h1 className="text-center">X</h1>)}
                    {!open && (<h1 className="text-center">☰</h1>)}
                </button>
            </div>
            {open && (
                <div className="h-screen w-screen fixed top-16 left-0 bg-black/25" onClick={()=>setOpen(false)}/>
            )}
            <div className={`fixed top-16 left-0 h-screen transform transition-transform duration-300 ${open ? "translate-x-0" : "-translate-x-full"}`}>
                <Sidebar/>
            </div>\
        </>
    );
}

export default MainHeader