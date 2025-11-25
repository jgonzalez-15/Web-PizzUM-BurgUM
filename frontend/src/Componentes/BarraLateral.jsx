import { NavLink, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { ContextoSesion } from "./context/ContextoSesion.jsx";
import { usarCarrito } from "./context/ContextoCarrito.jsx";

export default function BarraLateral() {
    const { sessionType, setSessionType, setSessionInfo } = useContext(ContextoSesion);
    const navigate = useNavigate();
    const { limpiarCarrito } = usarCarrito()

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
                limpiarCarrito()
                localStorage.removeItem("token");
                setSessionInfo(null);
                setSessionType("INVITADO");
                navigate("/iniciarSesion");
            } else {
                alert("No se pudo cerrar la sesión correctamente.");
            }
        } catch (error) {
            console.error("Error al cerrar sesión:", error);
            alert("Ocurrió un error al intentar cerrar sesión.");
        }
    };

    const baseLink =
        "flex items-center px-6 py-3 text-[1.05rem] font-semibold rounded-2xl transition-all mb-2";
    const activo =
        "bg-orange-50 text-orange-600 shadow-inner border border-orange-200";
    const inactivo =
        "text-gray-700 hover:bg-gray-100 hover:text-orange-500 border border-transparent";

    return (
        <aside className="w-64 h-[calc(100vh-4rem)] flex flex-col justify-between bg-white border-r border-gray-200 rounded-r-2xl shadow-lg">
            <div className="flex flex-col flex-1 mt-4 px-3 overflow-y-auto">
                <NavLink
                    to="/paginaPrincipal"
                    className={({ isActive }) =>
                        `${baseLink} ${isActive ? activo : inactivo}`
                    }
                >
                    Inicio
                </NavLink>

                {sessionType === "CLIENTE" && (
                    <>
                        <NavLink
                            to="/hacerUnPedido"
                            className={({ isActive }) =>
                                `${baseLink} ${isActive ? activo : inactivo}`
                            }
                        >
                            Tu orden
                        </NavLink>

                        <NavLink
                            to="/verPedidos"
                            className={({ isActive }) =>
                                `${baseLink} ${isActive ? activo : inactivo}`
                            }
                        >
                            Tus pedidos
                        </NavLink>

                        <NavLink
                            to="/favoritos"
                            className={({ isActive }) =>
                                `${baseLink} ${isActive ? activo : inactivo}`
                            }
                        >
                            Tus favoritos
                        </NavLink>

                        <NavLink
                            to="/perfil"
                            className={({ isActive }) =>
                                `${baseLink} ${isActive ? activo : inactivo}`
                            }
                        >
                            Ver tu Perfil
                        </NavLink>
                    </>
                )}
            </div>

            <div className="p-5 border-t border-gray-200 bg-white">
                {sessionType === "INVITADO" ? (
                    <NavLink
                        to="/iniciarSesion"
                        className="block w-full text-center font-semibold text-white bg-orange-500 hover:bg-orange-600 transition-all py-3 rounded-2xl shadow-md active:scale-95"
                    >
                        Iniciar sesión
                    </NavLink>
                ) : (
                    <button
                        onClick={cerrarSesion}
                        className="w-full text-center font-semibold text-white bg-orange-500 hover:bg-orange-600 transition-all py-3 rounded-2xl shadow-md active:scale-95"
                    >
                        Cerrar sesión
                    </button>
                )}
            </div>
        </aside>
    );
}
