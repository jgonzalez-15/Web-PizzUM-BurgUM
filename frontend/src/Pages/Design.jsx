import MainHeader from "../Components/MainHeader"
import IngredientAdder from "../Components/IngredientAdder"
import { useState } from "react";
import { DesignContext } from "../Components/context/DesignContext";
import Button from "../Components/Button";

function Design({type}){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const[glutenFreeOnly, setGlutenFreeOnly] = useState(false)

    let content
    if (type == "Pizza"){
        content = (<>
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
                    <div className="flex justify-center m-2 md:m-4">
                        <h1 className="font-bold text-2xl">Armá tu nueva {type}</h1>
                    </div>
                    <div className="flex w-full max-w-screen justify-end-safe items-center">
                        <h1 className="mr-2">
                            Solo productos sin gluten
                        </h1>
                        <button
                            onClick={() => setGlutenFreeOnly((prev) => !prev)}
                            className={`h-6 w-6 rounded-lg font-semibold transition mr-16
                                ${glutenFreeOnly ? "bg-orange-400" : "bg-transparent"} border-2`}
                        >
                        </button>
                    </div>
                    {content}
                </div>
                <div className="flex flex-col md:flex-row justify-center items-center m-8 mb-16">
                    <Button text="Agregar a favoritos" isPrimary={false}/>
                    <div className="w-4"></div>
                    <Button text="Agregar al carrito" isPrimary={true}/>
                </div>
                <footer className="p-8 bg-gray-200 flex flex-col md:flex-row justify-center items-center md:items-start">
                        <p className="text-gray-600 ml-16 mr-16">Sobre nosotros</p>
                        <p className="text-gray-600 ml-16 mr-16">Contacto</p>
                        <p className="text-gray-600 ml-16 mr-16">PizzUM & BurgUM ©</p>
                </footer>
            </div>
        </DesignContext.Provider>
        </>
    )
}

export default Design