import { NavLink, Outlet } from "react-router-dom";
import EncabezadoAdmin from "../Components/EncabezadoAdmin.jsx";
import PieDePagina from "../Components/PieDePagina";

export default function PanelAdministrador() {
    const baseBtn =
        "px-5 py-3 rounded-full border-2 font-bold transition-all text-lg";
    const activo = "bg-orange-500 text-white border-orange-500 shadow-md";
    const inactivo = "border-gray-300 bg-white hover:bg-gray-100";

    return (
        <>
            <EncabezadoAdmin />
            <div className="min-h-screen flex flex-col justify-between bg-gray-50">
                <main className="flex flex-col items-center px-6 sm:px-8 md:px-10 py-24">
                    <h1 className="text-4xl font-extrabold text-gray-800 mb-10 text-center">
                        Panel de Administración
                    </h1>

                    <nav className="flex flex-wrap justify-center gap-4 mb-12">
                        <NavLink
                            to="productos"
                            className={({ isActive }) =>
                                `${baseBtn} ${isActive ? activo : inactivo}`
                            }
                        >
                            Productos
                        </NavLink>
                        <NavLink
                            to="pedidos"
                            className={({ isActive }) =>
                                `${baseBtn} ${isActive ? activo : inactivo}`
                            }
                        >
                            Pedidos
                        </NavLink>
                        <NavLink
                            to="administradores"
                            className={({ isActive }) =>
                                `${baseBtn} ${isActive ? activo : inactivo}`
                            }
                        >
                            Administradores
                        </NavLink>
                        <NavLink
                            to="pedidosFecha"
                            className={({ isActive }) =>
                                `${baseBtn} ${isActive ? activo : inactivo}`
                            }
                        >
                            Listar pedidos por fecha
                        </NavLink>
                    </nav>

                    <section className="w-full max-w-6xl bg-white rounded-2xl shadow-lg border border-gray-100 p-10">
                        <Outlet />
                    </section>
                </main>
                <PieDePagina />
            </div>
        </>
    );
}
