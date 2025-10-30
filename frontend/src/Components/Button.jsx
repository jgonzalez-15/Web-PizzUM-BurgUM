import { Link } from "react-router-dom"

function Button({text, isPrimary, route}){
    return(
        <Link to={route} className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 md:text-xl 2xl:text-2xl text-center ${isPrimary ? "bg-orange-400 text-white" : "bg-gray-300 text-black"}`}>
            <h2 className="m-2 2xl:m-3">
                {text}
            </h2>
        </Link>
    )
}

export default Button