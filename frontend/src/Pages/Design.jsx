import { useState } from "react";
import { DesignContext } from "../Components/context/DesignContext";

import MainHeader from "../Components/MainHeader"
import IngredientAdder from "../Components/IngredientAdder"
import Footer from "../Components/Footer";
import AddToCartButton from "../Components/AddToCartButton";
import SmallButton from "../Components/SmallButton";

function Design({type}){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const[glutenFreeOnly, setGlutenFreeOnly] = useState(false)

    let content
    if (type == "Pizza"){
        content = (<>
                    <div className="ml-16 mb-8">
                    <label style={{ display: "block"}} className="text-xl font-bold">
                    Cantidad de carnes:{" "}
                    <select>
                        <option value="1">Chica</option>
                        <option value="2">Mediana</option>
                        <option value="3">Grande</option>
                    </select>
                    </label>
                    </div>
                    <IngredientAdder text="Elegí tu masa" maxCount={1} ing={[
                        {id: 1, name:"Masa 1", selected:false, glutenFree:true},
                        {id: 2, name:"Masa 2", selected:false, glutenFree:false},
                        {id: 3, name:"Masa 3", selected:false, glutenFree:false}]}/>
                    <IngredientAdder text="Elegí tu salsa" maxCount={1} ing={[
                        {id: 1, name:"Salsa 1", selected:false, glutenFree:false},
                        {id: 2, name:"Salsa 2", selected:false, glutenFree:false},
                        {id: 3, name:"Salsa 3", selected:false, glutenFree:false},
                        {id: 4, name:"Salsa 4", selected:false, glutenFree:true}]}/>
                    <IngredientAdder text="Elegí tus toppings" maxCount={0} ing={[
                        {id: 1, name:"Topping 1", selected:false, glutenFree:false},
                        {id: 2, name:"Topping 2", selected:false, glutenFree:true},
                        {id: 3, name:"Topping 3", selected:false, glutenFree:false},
                        {id: 4, name:"Topping 4", selected:false, glutenFree:true},
                        {id: 5, name:"Topping 5", selected:false, glutenFree:false},
                        {id: 6, name:"Topping 6", selected:false, glutenFree:false}]}/> 
                    </>)
    } else if (type == "Burger"){
        content = (<>
                    <IngredientAdder text="Elegí tu masa" maxCount={1} ing={[
                        {id: 1, name:"Pan 1", selected:false, glutenFree:true},
                        {id: 2, name:"Pan 2", selected:false, glutenFree:false},
                        {id: 3, name:"Pan 3", selected:false, glutenFree:false}]}/>
                    <IngredientAdder text="Elegí tus carnes" maxCount={1} ing={[
                        {id: 1, name:"Carne 1", selected:false, glutenFree:true},
                        {id: 2, name:"Carne 2", selected:false, glutenFree:false}]}/>
                    <div className="ml-16 mb-8">
                    <label style={{ display: "block"}} className="text-xl font-bold">
                    Cantidad de carnes:{" "}
                    <select>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                    </label>
                    </div>
                    <IngredientAdder text="Elegí tus salsas" maxCount={1} ing={[
                        {id: 1, name:"Salsa 1", selected:false, glutenFree:false},
                        {id: 2, name:"Salsa 2", selected:false, glutenFree:false},
                        {id: 3, name:"Salsa 3", selected:false, glutenFree:false},
                        {id: 4, name:"Salsa 4", selected:false, glutenFree:true}]}/>
                    <IngredientAdder text="Elegí tus toppings" maxCount={0} ing={[
                        {id: 1, name:"Topping 1", selected:false, glutenFree:false},
                        {id: 2, name:"Topping 2", selected:false, glutenFree:true},
                        {id: 3, name:"Topping 3", selected:false, glutenFree:false},
                        {id: 4, name:"Topping 4", selected:false, glutenFree:true},
                        {id: 5, name:"Topping 5", selected:false, glutenFree:false},
                        {id: 6, name:"Topping 6", selected:false, glutenFree:false}]}/> 
                    </>)
    }

    return(
        <>
        <DesignContext.Provider value={{glutenFreeOnly, setGlutenFreeOnly}}>
            <div className="h-full max-w-screen">
                <MainHeader className="z-10"/>
                <div className="flex mt-16 h-full justify-center flex-col max-w-full">

                    {/* Titulo */}
                    <div className="flex justify-center m-2 md:m-4">
                        <h1 className="font-bold text-2xl">Armá tu nueva {type}</h1>
                    </div>

                    {/* Filtro Gluten */}
                    <div className="flex w-full max-w-screen justify-end-safe items-center">
                        <h1 className="mr-2">
                            Solo sin gluten
                        </h1>
                        <button
                            onClick={() => setGlutenFreeOnly((prev) => !prev)}
                            className={`h-4 w-4 rounded-lg font-semibold transition mr-16 md:h-6 md:w-6 text-xs md:text-base
                                ${glutenFreeOnly ? "bg-orange-400" : "bg-transparent"} border-2`}
                        >
                        </button>
                    </div>
                    
                    {/* Contenido */}
                    {content}
                </div>

                {/* Botones */}
                <div className="flex flex-col md:flex-row justify-center items-center m-8 mb-16">
                    <SmallButton text="Agregar a favoritos" isPrimary={false}/>
                    <div className="w-4"></div>
                    <AddToCartButton isPrimary={true} item={{name : `Nueva ${type}`, description: "esto es una creacion", price: 130}}/>
                </div>
                <Footer/>
            </div>
        </DesignContext.Provider>
        </>
    )
}

export default Design