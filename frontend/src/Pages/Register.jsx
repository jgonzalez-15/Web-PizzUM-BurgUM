import { useState } from "react"

import Footer from "../Components/Footer"
import Button from "../Components/Button"

export default function Register(){
    const [email, setEmail] = useState("")
    const [nombre, setNombre] = useState("")
    const [apellido, setApellido] = useState("")
    const [contrasenia, setContrasenia] = useState("")
    const [confContrasenia, setConfContrasenia] = useState("")
    const [direccion, setDireccion] = useState("")
    const [numeroTarjeta, setNumeroTarjeta] = useState("")
    const [titular, setTitular] = useState("")
    const [vencimiento, setVencimiento] = useState("")
    const [domicilios, setDomicilios] = useState([])
    const [mediosDePagos, setMediosDePago] = useState([])

    const handleRegister = async () => {
    if (contrasenia == confContrasenia){
        try{
        const response = await fetch("http://localhost:8080/api/domicilio/crearDomicilio", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ direccion }),
            credentials: "include",
        });

        if (response.ok) {
            alert("Creación agregada a favoritos")
        } else {
            alert("Debes seleccionar al menos un ingrediente de los tipos Masa/Pan y Salsa/Tipo de carne")
        }
        } catch (error){
        console.error("Error al crear favorito:", error);
        }
    } else{
        alert("Las contraseñas no coinciden")
    }
    }
      
    return(
    <>
        <div className="h-screen w-screen flex flex-col items-center mx-auto justify-between">

            {/* Titulo */}
            <h1 className="font-bold text-2xl mt-8 mb-8">Registrarse</h1>
            <div className="mb-8 overflow-auto items-center flex-1 w-full flex flex-col gap-8">

                {/* SECCION: Datos del usuario */}
                <div className="bg-gray-50 shadow-xl rounded-2xl w-[calc(100vw-4rem)] md:w-[calc(100vw-32rem)]">
                    <div className="m-8">
                        <h1 className="font-bold text-xl">Datos del usuario</h1>
                        <form action="" className="p-4">
                            <h1>Correo electrónico</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"
                            value={email} onChange={(e) => setEmail(e.target.value)}/>
                            <h1>Nombre</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"
                            value={nombre} onChange={(e) => setNombre(e.target.value)}/>
                            <h1>Apellido</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"
                            value={apellido} onChange={(e) => setApellido(e.target.value)}/>
                            <h1>Contraseña</h1>
                            <input type="password" className="bg-gray-100 rounded-2xl w-full"
                            value={contrasenia} onChange={(e) => setContrasenia(e.target.value)}/>
                            <h1>Repetir contraseña</h1>
                            <input type="password" className="bg-gray-100 rounded-2xl w-full"
                            value={confContrasenia} onChange={(e) => setConfContrasenia(e.target.value)}/>
                        </form>
                    </div>
                </div>

                {/* SECCION: Datos del domicilio */}
                <div className="bg-gray-50 shadow-xl rounded-2xl w-[calc(100vw-4rem)] md:w-[calc(100vw-32rem)]">
                    <div className="m-8">
                        <h1 className="font-bold text-xl overflow-auto">Datos del domicilio</h1>
                        <form action="" className="p-4">
                            <h1>Dirección</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"
                            value={direccion} onChange={(e) => setDireccion(e.target.value)}/>
                        </form>
                    </div>
                </div>

                {/* SECCION: Datos del pago */}
                <div className="bg-gray-50 shadow-xl rounded-2xl w-[calc(100vw-4rem)] md:w-[calc(100vw-32rem)]">
                    <div className="m-8">
                        <h1 className="font-bold text-xl overflow-auto">Datos del medio de pago</h1>
                        <form action="" className="p-4">
                            <h1>Número de tarjeta</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"
                            value={numeroTarjeta} onChange={(e) => setNumeroTarjeta(e.target.value)}/>
                            <h1>Titular</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"
                            value={titular} onChange={(e) => setTitular(e.target.value)}/>
                            <h1>Vencimiento</h1>
                            <input type="month" className="bg-gray-100 rounded-2xl"
                            value={vencimiento} onChange={(e) => setVencimiento(e.target.value)}/>
                        </form>
                    </div>
                </div>

                {/* Boton registrarse */}
                <button className={`z-0 transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 md:text-xl text-center bg-orange-400 text-white`}
                onClick={handleRegister}>
                    <h2 className="m-2">
                        Registrarse
                    </h2>
                </button>
            </div>
            <Footer/>
        </div>
    </>    
    )
}