import { Link, useNavigate } from "react-router-dom"
import { useContext, useState } from "react";
import { SessionContext } from "../Components/context/SessionContext";

import SmallButton from "../Components/SmallButton"
import Footer from "../Components/Footer"

function Login(){
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const [email, setEmail] = useState("");
    const [contrasenia, setContrasenia] = useState("");
    const navigate = useNavigate();
    const { setSessionType, setSessionInfo } = useContext(SessionContext)

    {/* Funcion de iniciar sesion */}
    const handleLogin = async (e) => {
    e.preventDefault();
    try {
      {/* Intentar iniciar sesion como cliente */}
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, contrasenia }),
        credentials: "include",
      });

      if (response.ok) {
        {/* Si igresa actualizar info de la sesion y volver a la homepage */}
          const data = await response.json();
          setSessionType(data.rol)
          setSessionInfo(data.info)
          localStorage.setItem("token", data.jwt)
          navigate("/");
      } else {
          {/* Si no ingresa alertar */}
          alert("Usuario o contraseña incorrectos");
      }
    } catch (error) {
      console.error("Error al iniciar sesión:", error);
    }};

    return(
        <>
        <div className="flex flex-col h-screen w-screen justify-between">
            <div className="h-full flex flex-col items-center align-middle justify-center">

                {/* Container */}
                <div className="flex flex-col justify-center align-middle items-center bg-gray-100 w-64 h-96 shadow-2xl rounded-2xl">
                    <h1 className="font-bold text-center md:text-2xl">Ingresar</h1>

                    {/* Formulario */}
                    <form action="" className="flex flex-col justify-center m-8 mb-2" onSubmit={handleLogin}>
                        <h4 className="ml-2">Correo electrónico:</h4>
                        <input
                        type="text"
                        className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        />
                        <h4 className="ml-2">Contraseña:</h4>
                        <input
                        type="password"
                        className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2"
                        value={contrasenia}
                        onChange={(e) => setContrasenia(e.target.value)}
                        />

                        {/* Botones */}
                        <div className="flex flex-row justify-center mt-4">
                            <SmallButton text='Cancelar' isPrimary={false} route="/"/>
                            <button type="submit" className={`transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base text-center max-w-64 bg-orange-400 text-white`}>
                                <h2 className="m-2">
                                    Ingresar
                                </h2>
                            </button>
                        </div>
                    </form>

                    {/* Boton Registrarse */}
                    <Link to='/register' className="m-1 text-blue-800/50 underline hover:text-blue-800">Registrarse</Link>
                </div>
            </div>
            <Footer/>
        </div>
        </>
    )
}

export default Login