import { useState } from "react";

function Ingredient({name, glutenFree, selected, toggleSelected}){
    return(
        <div className="m-2 min-w-24 min-h-24 md:min-w-48 md:min-h-48 flex items-center justify-center">
            <div className={`h-24 w-24 md:h-48 md:w-48 bg-gray-100 rounded-2xl flex flex-col justify-center
                items-center ${selected ? "border-orange-300 border-4" : ""}`} onClick={toggleSelected}>
                <h1 className="font-bold m-1 text-xl">{name}</h1>
            </div>
        </div>
    )
}

export default Ingredient