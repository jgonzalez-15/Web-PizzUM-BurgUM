import { Link } from "react-router-dom"

function SmallButton({text, isPrimary, route}){
    return(
        <Link to={route} className={`transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base text-center max-w-64 ${isPrimary ? "bg-orange-400 text-white" : "bg-gray-300 text-black"}`}>
        <h2 className="m-2">
                {text}
            </h2>
        </Link>
    )
}

export default SmallButton