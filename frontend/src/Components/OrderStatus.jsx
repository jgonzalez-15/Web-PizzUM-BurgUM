export default function OrderStatus({pedido}){
    return(
    <>
        <div className="w-full flex flex-row justify-between items-center bg-gray-50 rounded-2xl shadow-xl
        h-32">
            <div className="m-4 flex flex-col">
                <h1 className="font-bold text-xl">Pedido #{pedido.id}</h1>
                <h2>Fecha:{pedido.fecha}</h2>
            </div>
            <div className="m-4 flex flex-row gap-8 items-center">
                <div className="gap-8 md:flex flex-row items-center hidden">
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${["En Cola", "En Preparacion", "En Camino", "Entregado"].includes(pedido.estado) ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">En Cola</h1>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${["En Preparacion", "En Camino", "Entregado"].includes(pedido.estado) ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">En Preparacion</h1>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${["En Camino", "Entregado"].includes(pedido.estado) ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">En Camino</h1>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${["Entregado"].includes(pedido.estado) ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">Entregado</h1>
                    </div>
                </div>
                <div className="md:hidden">
                    {["En Cola"].includes(pedido.estado) ? (<h1 className="font-bold text-orange-400">En Cola</h1>):(<></>)}
                    {["En Preparacion"].includes(pedido.estado) ? (<h1 className="font-bold text-orange-400">En Preaparación</h1>):(<></>)}
                    {["En Camino"].includes(pedido.estado) ? (<h1 className="font-bold text-orange-400">En Camino</h1>):(<></>)}
                    {["Entregado"].includes(pedido.estado) ? (<h1 className="font-bold text-orange-400">Entregado</h1>):(<></>)}
                </div>
                {pedido.estado == "En Cola" && (
                <button className={`z-0 h-12 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base text-center bg-red-600 text-white`}>
                    <h2 className="m-2">
                        Cancelar
                    </h2>
                </button>)}
                {pedido.estado != "En Cola" && (
                <button className={`z-0 h-12 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base text-center bg-gray-300`}>
                    <h2 className="m-2">
                        Cancelar
                    </h2>
                </button>)}
            </div>
        </div>     
    </>
    )
}