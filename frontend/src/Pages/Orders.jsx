import { useContext, useState, useEffect } from "react"
import { SessionContext } from "../Components/context/SessionContext";

import MainHeader from "../Components/MainHeader"
import OrderStatus from "../Components/OrderStatus"
import Footer from "../Components/Footer"

function Orders(){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const [orders, setOrders] = useState([])
    const [orderHistory, setOrdersHistory] = useState([])
    const [viewHistory, setViewHistory] = useState(false)
    const { sessionInfo } = useContext(SessionContext)
    const  email = sessionInfo.email

    const handleGetOrders = async () => {
        try{
          const response = await fetch(`http://localhost:8080/api/cliente/${email}/historial-pedidos`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
          });
    
          if (response.ok) {
            const data = await response.json();
            setOrders(data.map((o) => o.estado != "Entregado"));
            setOrdersHistory(data.map((o) => o.estado == "Entregado"))
          } else {
            alert("Ocurrió un error")
          }
            } catch (error){
                console.error("Error al obtener ingredientes:", error);
            }
        }
    
        {/* Obtener los pedidos al cargar la pagina */}
        useEffect(() => {
        if (email) {
            handleGetOrders();
        }
        }, [email]);
    
    return(
        <>
            <MainHeader className="z-10"/>
            <div className="flex flex-col pt-16 w-screen max-w-full min-h-[calc(100vh)] overflow-hidden justify-between z-0 gap-4">

                {/* Titulo */}
                <h1 className=" ml-4 mt-4 md:ml-8 md:mt-8 w-full font-bold text-xl">Tus Pedidos:</h1>

                {/* Listado de pedidos */}
                <div className="flex flex-1 flex-col ml-4 mr-4 md:ml-8 md:mr-8 gap-6 items-center mb-8">
                    {orders.length > 0 ? 
                        (orders.map((order) =>
                            <OrderStatus key={order.id} 
                            id={order.id}
                            date={order.fecha}
                            status={order.estado}
                            />
                        )) : (<h1>No tienes pedidos actuales</h1>)}

                    {/* Historial */}    
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
                        
                        {/* Boton ocultar historial */}
                        <button onClick={() => setViewHistory(false)} className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center max-w-96 bg-gray-300 text-black`}>
                            <h2 className="m-2 2xl:m-3">
                                Ocultar historial de pedidos
                            </h2>
                        </button>
                    </div>): (<>
                    
                    {/* Boton ver historial */}
                    <button onClick={() => setViewHistory(true)} className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center max-w-96 bg-orange-400 text-white`}>
                        <h2 className="m-2 2xl:m-3">
                            Ver historial de pedidos
                        </h2>
                    </button>
                    </>)}
                </div>
                <Footer/>
            </div>
        </>
    )
}

export default Orders