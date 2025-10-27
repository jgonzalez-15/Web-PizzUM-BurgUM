import { useState } from "react"

import Footer from "../Components/Footer"
import MainHeader from "../Components/MainHeader"
import CompleteFavourite from "../Components/CompleteFavourite";

function Favourites(){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const [favouriteList, setFavouriteList] = useState([{id: 10, name:"La mejor pizza de la historia mundial", type:"Pizza", price:123}])

    return(
        <>
            <MainHeader className="z-10"/>
            <div className="flex-1 flex flex-col justify-between min-h-screen mt-16 max-w-full">

                {/* Titulo */}
                <div className="m-4 md:ml-8">
                    <h1 className="font-bold text-xl"> Tus Favoritos:</h1>
                </div>

                {/* Lista de favoritos */}
                <div className="flex-1 flex flex-col ml-4 mr-4 md:ml-8 md:mr-8">
                    {favouriteList.length > 0 ? (
                        favouriteList.map((fav)=>(
                        <CompleteFavourite key={fav.id} favourite={fav}/>
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