export default function OrderStatus({id, date, status}){
    return(
    <>
        <div className="w-full flex flex-row justify-between items-center bg-gray-50 rounded-2xl shadow-xl
        h-32">
            <div className="m-4 flex flex-col">
                <h1 className="font-bold text-xl">Pedido #{id}</h1>
                <h2>Fecha:{date}</h2>
            </div>
            <div className="m-4 flex flex-row gap-8 items-center">
                <div className="gap-8 md:flex flex-row items-center hidden">
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${status in ["En Cola", "En Preparacion", "En camino", "Entregado"] ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">En Cola</h1>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${status in ["En Preparacion", "En camino", "Entregado"] ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">En Preparacion</h1>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${status in ["En camino", "Entregado"] ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">En Camino</h1>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className={`h-12 w-12 rounded-full ${status in ["Entregado"] ? "bg-orange-500" : "bg-gray-300"}`}/>
                        <h1 className="text-center">Entregado</h1>
                    </div>
                </div>
                <div className="md:hidden">
                    {status == 0 ? (<h1 className="font-bold text-orange-400">Pedido</h1>):(<></>)}
                    {status == 1 ? (<h1 className="font-bold text-orange-400">Preparando</h1>):(<></>)}
                    {status == 2 ? (<h1 className="font-bold text-orange-400">Enviando</h1>):(<></>)}
                    {status == 3 ? (<h1 className="font-bold text-orange-400">Recibido</h1>):(<></>)}
                </div>
                {status == 0 && (
                <button className={`z-0 h-12 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center bg-red-600 text-white`}>
                    <h2 className="m-2 2xl:m-3">
                        Cancelar
                    </h2>
                </button>)}
                {status != 0 && (
                <button className={`z-0 h-12 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center bg-gray-300`}>
                    <h2 className="m-2 2xl:m-3">
                        Cancelar
                    </h2>
                </button>)}
            </div>
        </div>     
    </>
    )
}