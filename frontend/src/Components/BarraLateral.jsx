import { Link, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { SessionContext } from "./context/SessionContext";
import ItemDeLaBarraLateraI from "./ItemDeLaBarraLateraI.jsx";

function BarraLateral() {
    const { sessionType, setSessionType, setSessionInfo } = useContext(SessionContext);
    const navegar = useNavigate();

    const cerrarSesion = async (e) => {
        e.preventDefault();
        try {
            const respuesta = await fetch("http://localhost:8080/api/cliente/cerrarSesion", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                credentials: "include",
            });

            if (respuesta.ok) {
                localStorage.removeItem("token");
                setSessionInfo(null);
                setSessionType("INVITADO");
                navegar("/login");
            } else {
                alert("No se pudo cerrar la sesión correctamente.");
            }
        } catch (error) {
            console.error("Error al cerrar sesión:", error);
            alert("Ocurrió un error al intentar cerrar sesión.");
        }
    };

    return (
        <div className="h-[calc(100vh-4rem)] w-64 flex flex-col justify-between bg-white border-r border-gray-200 shadow-md">
            {/* Opciones del menú */}
            <div className="flex flex-col mt-4">
                <ItemDeLaBarraLateraI texto="Inicio" ruta="/homepage" />
                {sessionType === "CLIENTE" && (
                    <>
                        <ItemDeLaBarraLateraI texto="Tu orden" ruta="/order" />
                        <ItemDeLaBarraLateraI texto="Tus pedidos" ruta="/viewOrders" />
                        <ItemDeLaBarraLateraI texto="Tus favoritos" ruta="/favoritos" />
                        <ItemDeLaBarraLateraI texto="Ver tu Perfil" ruta="/perfil" />
                    </>
                )}
            </div>

            {/* Parte inferior del menú */}
            <div className="border-t border-gray-300 p-4">
                {sessionType === "INVITADO" ? (
                    <Link
                        to="/login"
                        className="block w-full text-center font-semibold text-gray-700 bg-orange-100 hover:bg-orange-200 transition-colors py-2 rounded-xl shadow-sm"
                    >
                        Iniciar sesión
                    </Link>
                ) : (
                    <button
                        onClick={cerrarSesion}
                        className="w-full text-center font-semibold text-white bg-orange-500 hover:bg-orange-600 transition-colors py-2 rounded-xl shadow-md"
                    >
                        Cerrar sesión
                    </button>
                )}
            </div>
        </div>
    );
}

export default BarraLateral;
