import { Link } from "react-router-dom"
import Button from "../Components/Button"
import Favourite from "../Components/Favourite"
import { useState } from "react"
import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";

function HomePage(){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }
    
    return (
    <>
        <div className="min-h-screen flex flex-col justify-between">
            <MainHeader className="z-10"/>
            <div className="flex justify-center items-start pt-16 ml-8 mr-8 md:ml-16 md:mr-16 max-w-screen">
                <div className="flex flex-col mx-auto justify-center m-4 max-w-full">
                    <div className="flex bg-black/50 bg-cover bg-top bg-[url('https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2024/01/09/pizzas-y-hamburguesas.jpeg')] bg-blend-multiply w-full h-64 2xl:h-[28rem] rounded-2xl shadow-2xl justify-center items-center flex-col">
                        <h1 className="font-bold text-white mt-2 m-1 md:m-2 text-center text-2xl md:text-4xl 2xl:text-5xl">
                            Armá tu nueva Creación favorita
                        </h1>
                        <div className="flex flex-col md:flex-row justify-center m-4">
                            <Button text="Crear PizzUM" isPrimary={true} route='/design/pizza'/>
                            <Button text="Crear BurgUM" isPrimary={false} route='/design/burger'/>
                        </div>
                    </div>
                    <div className="mt-8 max-w-full">
                        <Link to='/favourites' className="m-2 font-bold">Tus Favoritos: </Link>
                        <div className="max-w-full m-4">
                            <div className="hidden md:flex md:flex-row md:justify-start-safe md:w-full md:overflow-x-auto md:max-w-full">
                                <Favourite title="Pizza 1"/>
                                <Favourite title="Pizza 2"/>
                                <Favourite title="Hamburguesa 1"/>
                                <Favourite title="Hamburguesa 2"/>
                                <Favourite title="Pizza 3"/>
                                <Favourite title="Hamburguesa 3"/>
                            </div>
                            <div className="flex flex-row justify-start-safe w-full overflow-x-auto max-w-full md:hidden">
                                <div className="flex flex-col">
                                    <Favourite title="Pizza 1"/>
                                    <Favourite title="Pizza 2"/>
                                </div>
                                <div className="flex flex-col">
                                    <Favourite title="Hamburguesa 2"/>
                                    <Favourite title="Pizza 3"/>
                                </div>
                                <div className="flex flex-col">
                                    <Favourite title="Hamburguesa 3"/>
                                    <Favourite title="Hamburguesa 4"/>
                                </div>
                                <div className="flex flex-col">
                                    <Favourite title="Pizza 4"/>
                                    <Favourite title="Pizza 5"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
    </>
    )
}

export default HomePage