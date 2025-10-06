import { Link } from "react-router-dom"

function SidebarItem({text, route}){
    return(
        <div className="flex flex-row items-center m-4">
                <p className="font-bold mr-2 text-xs md:text-lg">●</p>
                <Link to={route} className="font-bold hover:underline">{text}</Link>
            </div>
    )
}

export default SidebarItem