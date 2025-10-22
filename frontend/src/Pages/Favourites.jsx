import { useState } from "react"
import Footer from "../Components/Footer"
import MainHeader from "../Components/MainHeader"
import { Link } from "react-router-dom"

function Favourites(){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const [favouriteList, setFavouriteList] = useState([{id: 10, name:"La mejor pizza de la historia mundial", type:"Pizza", price:123}])

    return(
        <>
            <MainHeader className="z-10"/>
            <div className="flex-1 flex flex-col justify-between min-h-screen mt-16 max-w-full">
                <div className="m-4 md:ml-8">
                    <h1 className="font-bold text-xl"> Tus Favoritos:</h1>
                </div>
                <div className="flex-1 flex flex-col ml-4 mr-4 md:ml-8 md:mr-8">
                    {favouriteList.length > 0 ? (
                        favouriteList.map((favourite)=>(
                        <div className="flex flex-row gap-4 md:gap-8 overflow-auto">
                            <div className="flex h-24 w-24 md:h-48 md:w-48 bg-gray-100 rounded-2xl"/>
                            <div className="flex-1 h-24 md:h-48 justify-between flex-col overflow-auto">
                                <h1 className="font-bold text-2xl truncate">{favourite.name}</h1>
                                <h1 className="text-xl">{favourite.type}</h1>
                                <h1>${favourite.price}</h1> 
                            </div>
                            <div className="flex flex-col justify-center h-24 w-24 md:h-48 md:w-48">
                                <Link to="/viewCreation" state={{creationId: favourite.id}} className={`h-full flex justify-center items-center transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-sm font-bold m-1 text-sm md:text-base 2xl:text-xl text-center max-w-64 bg-gray-300 text-black`}>
                                    <h2 className="m-1 2xl:m-3">
                                        Detalles
                                    </h2>
                                </Link>
                                <Link className={`h-full flex justify-center items-center transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-sm font-bold m-1 text-sm md:text-base 2xl:text-xl text-center max-w-64 bg-orange-400 text-white`}>
                                    <h2 className="m-1 2xl:m-3">
                                        Agregar al carrito
                                    </h2>
                                </Link>
                            </div>
                        </div>
                    ))
                    ): (
                        <h1>No tienes favoritos agregados</h1>
                    )}
                    
                </div>
                <Footer/>
            </div>
        </>
    )
}

export default Favourites