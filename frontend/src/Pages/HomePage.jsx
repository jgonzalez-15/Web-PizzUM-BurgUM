import { Link } from "react-router-dom"
import { useState } from "react"

import Button from "../Components/Button"
import Favourite from "../Components/Favourite"
import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";

function HomePage(){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const [favouriteList, setFavouriteList] = useState([{id: 10, name:"La mejor pizza de la historia mundial", type:"Pizza", price:123}])


    return (
    <>
        <div className="min-h-screen flex flex-col justify-between">
            <MainHeader className="z-10"/>
            <div className="flex justify-center max-w-full mx-auto items-start pt-16 ml-8 mr-8 md:ml-16 md:mr-16">
                <div className="flex flex-col justify-center mt-4 w-full">

                    {/* Imagen central */}
                    <div className="flex bg-black/50 bg-cover bg-top bg-[url('https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2024/01/09/pizzas-y-hamburguesas.jpeg')] bg-blend-multiply w-full h-64 2xl:h-[28rem] rounded-2xl shadow-2xl justify-center items-center flex-col">
                        <h1 className="font-bold text-white mt-2 m-1 md:m-2 text-center text-2xl md:text-4xl 2xl:text-5xl">
                            Armá tu nueva Creación favorita
                        </h1>
                        <div className="flex flex-col md:flex-row justify-center m-4">
                            <Button text="Crear PizzUM" isPrimary={true} route='/design/pizza'/>
                            <Button text="Crear BurgUM" isPrimary={false} route='/design/burger'/>
                        </div>
                    </div>

                    {/* Lista de favoritos */}
                    <div className="mt-8">
                        <Link to='/favourites' className="m-2 font-bold">Tus Favoritos: </Link>
                        <div className="flex flex-1 flex-row overflow-auto">
                            {favouriteList.length > 0 ? (
                                favouriteList.map((favourite)=>(
                                <Favourite title={favourite.name}/>
                            ))
                            ): (
                                <h1 className="m-4">No tienes favoritos agregados</h1>
                            )}
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