import { useState } from "react";
import { useLocation } from "react-router-dom";

import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";
import SmallButton from "../Components/SmallButton";
import AddToCartButton from "../Components/AddToCartButton"


export default function ViewCreation(){
    const  id = useLocation().state?.creationId
    const [name, setName] = useState("Hola")
    const [type, setType] = useState("Burger")

    let content

    if (type == "Pizza"){
        const [dough, setDough] = useState("Integral")
        const [sauce, setSauce] = useState("Tomate")
        const [toppings, setToppings] = useState([{id:1, name:"Aceitunas"},{id:1, name:"Pepperoni"},{id:1, name:"Queso"}])
        content = (
            <>
                <h1>Masa:</h1>
                <div className="ml-2">
                    ● {dough}
                </div>
                <h1>Salsa:</h1>
                <div className="ml-2">
                    ● {sauce}
                </div>
                <h1>Toppings:</h1>
                <div className="flex flex-row ml-2 mr-2 overflow-auto gap-8">
                    {toppings.map((topping)=>(
                        <div>
                        ● {topping.name}
                        </div>
                    ))
                    } 
                </div>
            </>
        )
    }


    else if (type=="Burger"){
        const [bread, setBread] = useState("Con sésamo")
        const [meat, setMeat] = useState("Res")
        const [quantMeat, setQuantMeat] = useState(2)
        const [sauces, setSauces] = useState([{id:1, name:"Ketchup"},{id:1, name:"Mayonesa"},{id:1, name:"Barbacoa"}])
        const [ingredients, setIngredients] = useState([{id:1, name:"Tomate"},{id:1, name:"Lechuga"}])

        content = (
            <>
                <h1>Pan</h1>
                <div className="ml-2">
                    ● {bread}
                </div>
                <h1>Carne</h1>
                <div className="ml-2">
                    ● {quantMeat}x {meat}
                </div>
                <h1>Salsas</h1>
                <div className="flex flex-row ml-2 mr-2 overflow-auto gap-8">
                    {sauces.map((sauce)=>(
                        <div>
                        ● {sauce.name}
                        </div>
                    ))
                    } 
                </div>
                <h1>Ingredientes</h1>
                <div className="flex flex-row ml-2 mr-2 overflow-auto gap-8">
                    {ingredients.map((ingredient)=>(
                        <div>
                        ● {ingredient.name}
                        </div>
                    ))
                    } 
                </div>
            </>
        )
    }

    return (
    <>
        <MainHeader/>
        <div className="w-full mt-16 flex flex-1 flex-col justify-between min-h-[calc(100vh-4rem)]">
            <div className="flex h-full ml-8 mr-8 md:ml-16 md:mr-16 mt-4 mb-4 flex-col gap-4">

                {/* Datos del favorito */}
                <h1 className="text-xl font-bold w-full text-center">{name}</h1>
                <div>
                    <h1 className="font-bold">Detalles</h1>
                    <div className="w-full m-4 mt-0 mb-4 flex flex-col gap-2">
                        {content}
                    </div>
                </div>
                <div className="flex flex-row gap-4 justify-center">
                    <SmallButton text="Eliminar de favotiros" isPrimary={false}/>
                    <AddToCartButton isPrimary={true}/>
                </div>

            </div>
            <Footer/>
        </div>
        
    </>
    )
}