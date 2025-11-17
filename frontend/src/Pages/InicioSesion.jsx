import { Link, useNavigate } from "react-router-dom";
import { useContext, useState } from "react";
import { SessionContext } from "../Components/context/SessionContext";
import { usarCarrito } from "../Components/context/CarritoContexto.jsx";

import PieDePagina from "../Components/PieDePagina.jsx";
import Encabezado from "../Components/Encabezado.jsx";

function InicioDeSesion() {
    if (window.pageYOffset > 0) window.scrollTo(0, 0);

    const [email, setEmail] = useState("");
    const [contrasenia, setContrasenia] = useState("");
    const [enviando, setEnviando] = useState(false);

    const navigate = useNavigate();
    const { setSessionType, setSessionInfo } = useContext(SessionContext);
    const { limpiarCarrito } = usarCarrito()

    const iniciarSesion = async (e) => {
        e.preventDefault();
        if (enviando) return;
        setEnviando(true);

        try {
            const respuesta = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, contrasenia }),
                credentials: "include",
            });

            if (!respuesta.ok) {
                alert("Usuario o contraseña incorrectos");
                return;
            }

            const data = await respuesta.json();

            const rol = String(data.rol || "").toUpperCase();

            setSessionType(data.rol);
            setSessionInfo(data.info);
            limpiarCarrito()
            localStorage.setItem("token", data.jwt);
            localStorage.setItem("sessionInfo", JSON.stringify(data.info));
            localStorage.setItem("sessionType", data.rol);

            if (rol === "ADMIN") {
                navigate("/admin");
            } else {
                navigate("/");
            }
        } catch (err) {
            console.error("Error al iniciar sesión:", err);
            alert("No se pudo iniciar sesión. Intentalo de nuevo.");
        } finally {
            setEnviando(false);
        }
    };

    return (
        <>
        <Encabezado />
        <div className="flex flex-col min-h-screen bg-gray-50 justify-between">
            <div className="flex flex-col pt-16 items-center justify-center flex-1">
                <div className="bg-white w-80 md:w-96 rounded-2xl m-8 shadow-xl p-8 border border-gray-200">
                    <h1 className="text-center text-2xl md:text-3xl font-extrabold text-gray-800 mb-6">
                        Iniciar sesión
                    </h1>

                    <form onSubmit={iniciarSesion} className="flex flex-col gap-4">
                        <div>
                            <label className="block text-sm font-semibold text-gray-700 mb-1">
                                Correo electrónico
                            </label>
                            <input
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="w-full bg-gray-100 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-orange-400"
                                required
                            />
                        </div>

                        <div>
                            <label className="block text-sm font-semibold text-gray-700 mb-1">
                                Contraseña
                            </label>
                            <input
                                type="password"
                                value={contrasenia}
                                onChange={(e) => setContrasenia(e.target.value)}
                                className="w-full bg-gray-100 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-orange-400"
                                required
                            />
                        </div>

                        <div className="flex justify-center gap-3 mt-4">
                            <button
                                type="button"
                                onClick={() => navigate("/")}
                                className="px-6 py-2 rounded-2xl bg-gray-200 hover:bg-gray-300 text-gray-800 font-semibold shadow-sm transition-transform duration-150 hover:scale-105"
                                disabled={enviando}
                            >
                                Cancelar
                            </button>

                            <button
                                type="submit"
                                className={`px-6 py-2 rounded-2xl text-white font-semibold shadow-md transition-transform duration-150 hover:scale-105 ${
                                    enviando ? "bg-orange-300 cursor-not-allowed" : "bg-orange-500 hover:bg-orange-600"
                                }`}
                                disabled={enviando}
                            >
                                {enviando ? "Ingresando..." : "Ingresar"}
                            </button>
                        </div>
                    </form>

                    <p className="text-center text-sm text-gray-600 mt-6">
                        ¿No tenés cuenta?{" "}
                        <Link
                            to="/registrarse"
                            className="text-orange-500 font-semibold hover:text-orange-600 transition-colors"
                        >
                            Registrate acá
                        </Link>
                    </p>
                </div>
            </div>

            <PieDePagina />
        </div>
        </>
    );
}

export default InicioDeSesion;
