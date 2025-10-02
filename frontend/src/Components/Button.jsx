import { Link } from "react-router-dom"

function Button({text, isPrimary}){
    return(
        <button className={`rounded-2xl shadow-2xl font-bold m-1 ${isPrimary ? "bg-orange-400 text-white" : "bg-gray-300 text-black"}`}>
            <h2 className="m-2">
                {text}
            </h2>
        </button>
    )
}

export default Button