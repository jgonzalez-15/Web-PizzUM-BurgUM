import { NavLink, useNavigate } from "react-router-dom";

export default function BarraLateralAdmin({ cerrarMenu }) {
    const navigate = useNavigate();

    const cerrarSesion = () => {
        localStorage.removeItem("token");
        if (cerrarMenu) cerrarMenu();
        navigate("/iniciarSesion", { replace: true });
    };

    const baseLink =
        "flex items-center gap-3 px-5 py-3 text-[1.05rem] font-semibold rounded-2xl transition-all mb-2";
    const activo =
        "bg-orange-50 text-orange-600 shadow-inner border border-orange-200";
    const inactivo =
        "text-gray-700 hover:bg-gray-100 hover:text-orange-500 border border-transparent";

    return (
        <aside
            className="w-64 h-[calc(100vh-4rem)] flex flex-col justify-between bg-white border-r border-gray-200 rounded-r-2xl shadow-lg"
        >
            {/* Navegación principal */}
            <div className="flex flex-col flex-1 mt-4 px-3 overflow-y-auto">
                <NavLink
                    to="/panelAdministrador/productos"
                    onClick={cerrarMenu}
                    className={({ isActive }) => `${baseLink} ${isActive ? activo : inactivo}`}
                >
                    <span className="text-xl"></span> Productos
                </NavLink>

                <NavLink
                    to="/panelAdministrador/pedidos"
                    onClick={cerrarMenu}
                    className={({ isActive }) => `${baseLink} ${isActive ? activo : inactivo}`}
                >
                    <span className="text-xl"></span> Pedidos
                </NavLink>

                <NavLink
                    to="/panelAdministrador/administradores"
                    onClick={cerrarMenu}
                    className={({ isActive }) => `${baseLink} ${isActive ? activo : inactivo}`}
                >
                    <span className="text-xl"></span> Administradores
                </NavLink>
            </div>

            {/* Botón cerrar sesión */}
            <div className="p-5 border-t border-gray-200 bg-white">
                <button
                    onClick={cerrarSesion}
                    className="w-full bg-orange-500 text-white py-3 rounded-2xl font-semibold shadow-md hover:bg-orange-600 active:scale-95 transition-transform"
                >
                    Cerrar sesión
                </button>
            </div>
        </aside>
    );
}
