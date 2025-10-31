import { useCart } from "../Components/context/CartItems"

import MainHeader from "../Components/MainHeader"
import Footer from "../Components/Footer"
import OrderResumeItem from "../Components/OrderResumeItem"
import SmallButton from "../Components/SmallButton"
import OrderItem from "../Components/OrderItem"
import Button from "../Components/Button"

function NewOrder({}){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const {items} = useCart()
    const {removeItem} = useCart()

    const total = items.reduce((sum, item) => sum + item.precio, 0);

    return(
        <>
            <MainHeader className="z-10"/>
            <div className="flex flex-col pt-16 w-screen max-w-full min-h-[calc(100vh)] justify-between z-0">
                <div className="w-full flex flex-1 flex-col md:flex-row h-full items-center md:items-start justify-between gap-4">
                    <div className="w-full flex justify-center">
                        <div className="m-4 md:m-8 w-full">

                            {/* Items del pedido */}
                            {   
                                items.length === 0 ?
                                (
                                    <div className="flex justify-center flex-col w-96 gap-4">
                                        <h1 className="ml-2">Agrega artículos al carrito para verlos aquí</h1>
                                        <Button text="Volver a la página principal" isPrimary={true} route="/homepage"/>
                                    </div>
                                ):
                                (items.map((item) => (
                                    <OrderItem key={item.id} name={item.nombre} remove={() => removeItem(item.id)}/>
                                )))
                            }
                        </div>
                    </div>
                    <div className="w-full md:w-128 h-full bg-gray-100 md:shadow-2xl md:mr-16 md:mt-8 md:mb-8 md:rounded-2xl">
                        <div className="flex flex-col m-4 justify-between">

                            {/* Resumen del pedido */}
                            <h1 className="font-bold text-xl">Resumen de pedido:</h1>
                            <div className="mt-4">
                                {
                                    items.length == 0  ?
                                    <h1 className="flex items-center justify-center">No hay articulos en el carrito</h1>:
                                    (items.map((item) => (
                                        <OrderResumeItem name={item.nombre} price={item.precio}/>
                                    )))
                                }
                            </div>

                            {/* Total del pedido y botones */}
                            {items.length > 0 && (
                                <>
                                    <div className="flex flex-row justify-between items-center pt-4 border-t m-4">
                                        <h1 className="font-bold text-xl">Total:</h1>
                                        <h1 className="font-bold text-xl text-right">${total}</h1>
                                    </div>
                                    <div className="flex flex-row justify-center items-center">
                                        <SmallButton text="Seguir comprando" isPrimary={false} route="/homepage"/>
                                        <SmallButton text="Pasar al pago" isPrimary={true}/>
                                    </div>
                                </>
                                )
                            }

                        </div>
                    </div>
                </div>
                <Footer/>
            </div>
        </>
    )
}

export default NewOrder