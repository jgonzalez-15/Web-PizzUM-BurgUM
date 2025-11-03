import { Link } from "react-router-dom"

import AddToCartButton from "./AddToCartButton"

export default function CompleteFavourite({favourite}){
    return (
        <div className="flex flex-row gap-4 md:gap-8 overflow-auto">
            <div className="flex h-24 w-24 md:h-48 md:w-48 bg-gray-100 rounded-2xl"/>
            <div className="flex-1 h-24 md:h-48 justify-between flex-col overflow-auto">
                <h1 className="font-bold text-2xl truncate">{favourite.nombre}</h1>
                <h1>${favourite.precio}</h1> 
            </div>
            <div className="flex flex-col justify-center h-24 w-24 md:h-48 md:w-48">
                <Link to="/viewCreation" state={{creationId: favourite.id}} className={`h-full flex justify-center items-center transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-sm font-bold m-1 text-sm md:text-base text-center max-w-64 bg-gray-300 text-black`}>
                    <h2 className="m-1">
                        Detalles
                    </h2>
                </Link>
                <AddToCartButton item={favourite} isPrimary={true}/>
            </div>
        </div>
    )
}