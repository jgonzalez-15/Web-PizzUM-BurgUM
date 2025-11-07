import { Link, useNavigate } from "react-router-dom"
import { useContext } from "react"
import { SessionContext } from "./context/SessionContext"

import SidebarItem from "./SidebarItem"

function Sidebar(){
    const { sessionType, setSessionType, setSessionInfo } = useContext(SessionContext)
    const navigate = useNavigate();

    const handleSignOut = async (e) => {
    e.preventDefault();
    try {
        const response = await fetch(`http://localhost:8080/api/cliente/cerrarSesion`, {
        method: "POST",
        headers: { "Content-Type": "application/json" ,
            'Authorization': `Bearer ${localStorage.getItem("token")}`
        },
        credentials: "include"
        });

        if (response.ok) {
            setSessionInfo({})
            setSessionType("INVITADO")
            navigate("/login")
        } else {
            alert("No se pudo cerrar la sesion");
        }
        } catch (error) {
        console.error("Error al cerrar la sesion:", error);
        alert("Error al cerrar sesión.");
        }
    };


    return(
        <div className="h-[calc(100vh-4rem)] w-64 justify-between bg-gray-100 flex flex-col">
            <div className="flex flex-col">
            <SidebarItem text='Inicio' route='/homepage'/>
            {sessionType == "CLIENTE" && (
                <>
                    <SidebarItem text='Comprar' route='/order'/>
                    <SidebarItem text='Tus Pedidos' route='/viewOrders'/>
                    <SidebarItem text='Tus Favoritos' route='/favoritos'/>
                    <SidebarItem text="Mi Perfil" route="/perfil" />

                </>
            )}
            </div>
            <div className="w-full border-t">
                <div className="w-full m-4">
                    {sessionType == "INVITADO" ? (
                            <Link to='/login' className="font-bold">Ingresar</Link>
                        ):(
                            <button className="font-bold" onClick={handleSignOut}>Cerrar Sesión</button>
                        )}
                    
                </div>
            </div>
        </div>
    )
}

export default Sidebar