export default function OrderResumeItem({name, price}){
    return(
        <div className="flex flex-row justify-between items-center m-4">
            <h2 className="font-bold truncate">● {name}</h2>
            <h2 className="font-bold text-right">${price}</h2>
        </div>
    )
}