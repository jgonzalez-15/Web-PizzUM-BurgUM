import { Link } from "react-router-dom"
import Button from "../Components/Button"
import Favourite from "../Components/Favourite"
import { useState } from "react"
import Sidebar from "../Components/Sidebar";

function HomePage(){
const [open, setOpen] = useState(false);

    return (
    <>
        <div className="min-h-screen flex flex-col justify-between">
            <header className="flex justify-between items-center fixed top-0 w-full h-16 bg-white border-b-1 border-gray-300">
                <div className="md:hidden m-2 h-12 w-12 bg-orange-400 flex items-center justify-center rounded-sm">
                    <button className="text-white text-center" onClick={()=>setOpen(!open)}>
                        ☰
                    </button>
                </div>
                <h1 className="text-right md:text-left font-bold w-full m-2 ml-8">
                    PizzUM & BurgUM
                </h1>
                <div className="hidden md:flex md:w-full md:m-2 md:justify-end md:items-center">
                    <p className="m-2">Mis Pedidos</p>
                    <Button text="Comprar" isPrimary={true}/>
                    <Button text="Ingresar" isPrimary={false}/>
                    
                </div>
            </header>
            <div className="flex flex-1 justify-center items-start pt-16 ml-8 mr-8 md:ml-16 md:mr-16 max-w-screen">
                {open && (
                    <Sidebar/>
                )}
                <div className="flex flex-col mx-auto justify-center m-4 max-w-full">
                    <div className="flex bg-gray-100 w-full h-64 rounded-2xl shadow-2xl justify-center items-center flex-col">
                        <h1 className="font-bold m-5 text-center">
                            Armá tu nueva Creación favorita
                        </h1>
                        <div className="flex flex-col md:flex-row justify-center m-4">
                            <Button text="Crear PizzUM" isPrimary={true}/>
                            <Button text="Crear BurgUM" isPrimary={false}/>
                        </div>
                    </div>
                    <div className="mt-4 max-w-full">
                        <h1 className="m-2 font-bold">Tus Favoritos</h1>
                        <div className="max-w-full m-4">
                            <div className="flex flex-row justify-start-safe w-full overflow-x-auto max-w-full">
                                <Favourite title="Pizza 1"/>
                                <Favourite title="Pizza 2"/>
                                <Favourite title="Hamburguesa 1"/>
                                <Favourite title="Hamburguesa 2"/>
                                <Favourite title="Pizza 3"/>
                                <Favourite title="Hamburguesa 3"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer className="p-8 bg-gray-100 flex flex-col md:flex-row justify-between items-center md:items-start">
                <p className="text-gray-600">Sobre nosotros</p>
                <p className="text-gray-600">Contacto</p>
                <p className="text-gray-600">PizzUM & BurgUM ©</p>
            </footer>
        </div>
    </>
    )
}

export default HomePage