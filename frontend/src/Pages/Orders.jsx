import { useState } from "react"
import MainHeader from "../Components/MainHeader"
import OrderStatus from "../Components/OrderStatus"
import Footer from "../Components/Footer"
import SmallButton from "../Components/SmallButton"
import GetFetch from "../Components/GetFetch"

function Orders(){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const [orders, setOrders] = useState([{id: 1, date:"aaaaa", status: 2}, {id: 2, date:"bbbb", status: 0}])
    const [orderHistory, setOrdersHistory] = useState([])
    const [viewHistory, setViewHistory] = useState(false)
    
    return(
        <>
            <MainHeader className="z-10"/>
            <div className="flex flex-col pt-16 w-screen max-w-full min-h-[calc(100vh)] overflow-hidden justify-between z-0 gap-4">
                <h1 className=" ml-4 mt-4 md:ml-8 md:mt-8 w-full font-bold text-xl">Tus Pedidos:</h1>
                <div className="flex flex-1 flex-col ml-4 mr-4 md:ml-8 md:mr-8 gap-6 items-center mb-8">
                    {orders.length > 0 ? 
                        (orders.map((order) =>
                            <OrderStatus key={order.idPedido} 
                            id={order.idPedido}
                            date={order.fecha}
                            status={order.estado}
                            />
                        )) : (<h1>No tienes pedidos actuales</h1>)}
                    {viewHistory ? 
                    (<div className="w-full flex flex-col gap-6 items-center">
                        <h1 className=" ml-4 mr-4 md:ml-8 md:mt-8 w-full font-bold text-xl">Historial:</h1>
                        {orderHistory.map((order) =>
                            <OrderStatus key={order.idPedido} 
                            id={order.idPedido}
                            date={order.fecha}
                            status={order.estado}
                            />
                        )}
                        <button onClick={() => setViewHistory(false)} className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center max-w-96 bg-gray-300 text-black`}>
                            <h2 className="m-2 2xl:m-3">
                                Ocultar historial de pedidos
                            </h2>
                        </button>
                    </div>): 
                    (<button onClick={() => setViewHistory(true)} className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center max-w-96 bg-orange-400 text-white`}>
                        <h2 className="m-2 2xl:m-3">
                            Ver historial de pedidos
                        </h2>
                    </button>)}
                </div>
                <Footer/>
            </div>
        </>
    )
}

export default Orders