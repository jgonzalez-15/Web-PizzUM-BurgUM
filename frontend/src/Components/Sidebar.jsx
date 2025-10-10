import { Link } from "react-router-dom"
import SidebarItem from "./SidebarItem"
import { useLocation } from "react-router-dom"

function Sidebar(){
    const location = useLocation()
    return(
        <div className="h-[calc(100vh-4rem)] w-64 justify-between bg-gray-100 flex flex-col">
            <div className="flex flex-col">
            <SidebarItem text='Inicio' route='/homepage'/>
            <SidebarItem text='Comprar' route='/order'/>
            <SidebarItem text='Tus Pedidos' route='/viewOrders'/>
            <SidebarItem text='Tus Favoritos' route='/favourites'/>
            <SidebarItem text='Administración' route='/admin'/>
            </div>
            <div className="w-full border-t">
                <div className="w-full m-4">
                    <Link to='/login' className="font-bold" state={{from: location.pathname}}>Ingresar</Link>
                </div>
            </div>
        </div>
    )
}

export default Sidebar