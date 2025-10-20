export default function OrderItem({name, desc, remove}){
    return(
        <div className="m-2 md:mb-4 md:m-0">
        <div className="bg-gray-50/50 p-6 w-full shadow-md flex flex-row items-center 
                        rounded-2xl justify-between">
            <div className="flex flex-col">
                <h1 className="text-xl font-bold">{name}</h1>
                <p>{desc}</p>
            </div>
            <button className="h-6 w-6 flex items-center justify-center" onClick={()=>(null)}>
                <button className="h-12 w-12 text-red-600 font-bold" onClick={remove}>X</button>
            </button>
        </div>
        </div>
    )
}