import { Link } from "react-router-dom"
import SidebarItem from "./SidebarItem"

function Sidebar(){
    return(
        <div className="h-full w-64 bg-gray-100 flex flex-col">
            <SidebarItem text='Inicio' route='/homepage'/>
            <SidebarItem text='Comprar' route='/newOrder'/>
            <SidebarItem text='Tus Pedidos' route='/orders'/>
            <SidebarItem text='Tus Favoritos' route='/favourites'/>
        </div>
    )
}

export default Sidebar