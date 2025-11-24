import { useState } from "react";
import { useNavigate } from "react-router-dom";
import PieDePagina from "../Components/PieDePagina.jsx";
import Encabezado from "../Components/Encabezado.jsx";

export default function Registrar() {
    const [email, setEmail] = useState("");
    const [nombre, setNombre] = useState("");
    const [apellido, setApellido] = useState("");
    const [contrasenia, setContrasenia] = useState("");
    const [confirmarContrasenia, setConfirmarContrasenia] = useState("");
    const [telefono, setTelefono] = useState("");
    const [fechaNacimiento, setFechaNacimiento] = useState("");
    const [direccion, setDireccion] = useState("");
    const [numeroTarjeta, setNumeroTarjeta] = useState("");
    const [nombreTitular, setNombreTitular] = useState("");
    const [fechaVencimiento, setFechaVencimiento] = useState("");
    const [cedula, setCedula] = useState("")

    const navigate = useNavigate();

    const registrarUsuario = async (e) => {
        e.preventDefault();

        if (!email || !nombre || !apellido || !contrasenia || !confirmarContrasenia || !telefono || !fechaNacimiento || !direccion || !numeroTarjeta || !nombreTitular || !fechaVencimiento || !cedula) {
            alert("Debes completar todos los campos.");
            return;
        }

        if (contrasenia !== confirmarContrasenia) {
            alert("Las contraseñas no coinciden.");
            return;
        }

        if (contrasenia.length < 8) {
            alert("La contraseña debe tener al menos 8 caracteres.");
            return;
        }

        try {
            const respuesta = await fetch("http://localhost:8080/api/cliente/registrar", {
                method: "POST",
                headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`},
                body: JSON.stringify({
                    email,
                    nombre,
                    apellido,
                    contrasenia,
                    telefono: parseInt(telefono),
                    fechaNac: fechaNacimiento,
                    domicilios: [{ direccion }],
                    mediosDePago: [
                        { numeroTarjeta: parseInt(numeroTarjeta), nombreTitular, fechaVencimiento: fechaVencimiento + "-01", },
                    ],
                    cedula: cedula
                }),
                credentials: "include",
            });

            if (respuesta.ok) {
                alert("Registro exitoso.");
                navigate("/iniciarSesion");
            } else {
                const datos = await respuesta.json().catch(() => ({}));
                alert(datos.message || "No se pudo completar el registro. Verificá los datos e intentá nuevamente.");
            }
        } catch (error) {
            console.error("Error al registrar usuario:", error);
            alert("Ocurrió un error al intentar registrarte. Intentá nuevamente.");
        }
    };

    return (
        <>
            < Encabezado />

            <div className="min-h-screen w-full flex flex-col justify-between items-center bg-white">
                {/* Botón volver al inicio */}
                <div className="sticky top-[70px] left-0 w-full flex justify-start px-10 py-2 z-30">
                    <button
                        onClick={() => navigate("/")}
                        className="bg-gray-200 text-gray-800 font-semibold rounded-2xl px-5 py-2 shadow-md hover:bg-gray-300 transition-all text-lg">
                        ← Volver al inicio
                    </button>
                </div>


                {/* Título */}
                <h1 className="font-bold text-3xl mt-20 mb-6 text-center text-gray-800">
                    Crear cuenta
                </h1>

                <form
                    onSubmit={registrarUsuario}
                    className="flex flex-col gap-10 w-[calc(100vw-4rem)] md:w-[calc(100vw-28rem)] max-w-3xl mb-16"
                >
                    {/* Datos del usuario */}
                    <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-200">
                        <h2 className="font-bold text-2xl mb-6 text-gray-800 text-center">
                            Datos personales
                        </h2>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
                            <input
                                type="email"
                                placeholder="Correo electrónico"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />

                            <input
                                type="text"
                                placeholder="Nombre"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nombre}
                                onChange={(e) => setNombre(e.target.value)}
                            />

                            <input
                                type="text"
                                placeholder="Apellido"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={apellido}
                                onChange={(e) => setApellido(e.target.value)}
                            />

                            <input
                                type="tel"
                                placeholder="Teléfono"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={telefono}
                                onChange={(e) => setTelefono(e.target.value)}
                            />

                            <input
                                type="date"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={fechaNacimiento}
                                onChange={(e) => setFechaNacimiento(e.target.value)}
                            />

                            <input
                                type="text"
                                placeholder="Cédula"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={cedula}
                                onChange={(e) => setCedula(e.target.value)}
                            />
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-5 mt-6">
                            <input
                                type="password"
                                placeholder="Contraseña"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={contrasenia}
                                onChange={(e) => setContrasenia(e.target.value)}
                            />

                            <input
                                type="password"
                                placeholder="Confirmar contraseña"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={confirmarContrasenia}
                                onChange={(e) => setConfirmarContrasenia(e.target.value)}
                            />
                        </div>
                    </div>

                    {/* Domicilio */}
                    <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-200">
                        <h2 className="font-bold text-2xl mb-6 text-gray-800 text-center">
                            Domicilio
                        </h2>

                        <input
                            type="text"
                            placeholder="Dirección completa"
                            className="bg-gray-100 rounded-xl w-full p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                            value={direccion}
                            onChange={(e) => setDireccion(e.target.value)}
                        />
                    </div>

                    {/* Medio de pago */}
                    <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-200">
                        <h2 className="font-bold text-2xl mb-6 text-gray-800 text-center">
                            Medio de pago
                        </h2>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
                            <input
                                type="text"
                                placeholder="Número de tarjeta"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={numeroTarjeta}
                                onChange={(e) => setNumeroTarjeta(e.target.value)}
                            />

                            <input
                                type="text"
                                placeholder="Titular de la tarjeta"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nombreTitular}
                                onChange={(e) => setNombreTitular(e.target.value)}
                            />

                            <input
                                type="month"
                                className="bg-gray-100 rounded-xl p-3 border border-gray-300 focus:ring-2 focus:ring-orange-400 outline-none"
                                value={fechaVencimiento}
                                onChange={(e) => setFechaVencimiento(e.target.value)}
                            />
                        </div>
                    </div>

                    {/* Botón para registrarse */}
                    <button
                        type="submit"
                        className="w-full bg-orange-500 text-white font-bold py-3 rounded-2xl shadow-xl hover:bg-orange-600 hover:scale-[1.02] transition-transform text-lg mt-2"
                    >
                        Registrarme
                    </button>
                </form>


                {/* PieDePagina */}
                <PieDePagina />
            </div>
        </>
    );
}
