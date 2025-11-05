import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Footer from "../Components/Footer";

export default function Register() {
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

    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

        // Validaciones
        if (!email || !nombre || !apellido || !contrasenia || !confirmarContrasenia || !telefono || !fechaNacimiento || !direccion || !numeroTarjeta || !nombreTitular || !fechaVencimiento) {
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
            const response = await fetch("http://localhost:8080/api/cliente/registrar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
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
                }),
                credentials: "include",
            });

            if (response.ok) {
                alert("Registro exitoso.");
                navigate("/login");
            } else {
                const data = await response.json().catch(() => ({}));
                alert(data.message || "No se pudo completar el registro. Verificá los datos e intentá nuevamente.");
            }
        } catch (error) {
            console.error("Error al registrar usuario:", error);
            alert("Ocurrió un error al intentar registrarte. Intentá nuevamente.");
        }
    };

    return (
        <>
            <div className="min-h-screen w-full flex flex-col justify-between items-center bg-white">
                {/* Botón Volver */}
                <button
                    onClick={() => navigate("/")}
                    className="fixed top-4 left-4 z-20 bg-orange-400 text-white font-bold rounded-2xl px-4 py-2 shadow-xl hover:scale-105 transition-transform">
                    Volver al inicio
                </button>

                {/* Título */}
                <h1 className="font-bold text-3xl mt-20 mb-6 text-center text-gray-800">
                    Crear cuenta
                </h1>

                {/* Formulario */}
                <form onSubmit={handleRegister} className="flex flex-col gap-8 w-[calc(100vw-4rem)] md:w-[calc(100vw-28rem)] max-w-3xl mb-8">
                    {/* Datos del usuario */}
                    <div className="bg-gray-50 shadow-2xl rounded-2xl p-8">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">
                            Datos personales
                        </h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <input
                                type="email"
                                placeholder="Correo electrónico"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                            <input
                                type="text"
                                placeholder="Nombre"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={nombre}
                                onChange={(e) => setNombre(e.target.value)}
                            />
                            <input
                                type="text"
                                placeholder="Apellido"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={apellido}
                                onChange={(e) => setApellido(e.target.value)}
                            />
                            <input
                                type="tel"
                                placeholder="Teléfono"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={telefono}
                                onChange={(e) => setTelefono(e.target.value)}
                            />
                            <input
                                type="date"
                                placeholder="Fecha de nacimiento"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={fechaNacimiento}
                                onChange={(e) => setFechaNacimiento(e.target.value)}
                            />
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                            <input
                                type="password"
                                placeholder="Contraseña"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={contrasenia}
                                onChange={(e) => setContrasenia(e.target.value)}
                            />
                            <input
                                type="password"
                                placeholder="Confirmar contraseña"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={confirmarContrasenia}
                                onChange={(e) => setConfirmarContrasenia(e.target.value)}
                            />
                        </div>
                    </div>

                    {/* Domicilio */}
                    <div className="bg-gray-50 shadow-2xl rounded-2xl p-8">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">
                            Domicilio
                        </h2>
                        <input
                            type="text"
                            placeholder="Dirección completa"
                            className="bg-gray-100 rounded-2xl w-full p-2"
                            value={direccion}
                            onChange={(e) => setDireccion(e.target.value)}
                        />
                    </div>

                    {/* Medio de pago */}
                    <div className="bg-gray-50 shadow-2xl rounded-2xl p-8">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">
                            Medio de pago
                        </h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <input
                                type="text"
                                placeholder="Número de tarjeta"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={numeroTarjeta}
                                onChange={(e) => setNumeroTarjeta(e.target.value)}
                            />
                            <input
                                type="text"
                                placeholder="Titular de la tarjeta"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={nombreTitular}
                                onChange={(e) => setNombreTitular(e.target.value)}
                            />
                            <input
                                type="month"
                                className="bg-gray-100 rounded-2xl p-2"
                                value={fechaVencimiento}
                                onChange={(e) => setFechaVencimiento(e.target.value)}
                            />
                        </div>
                    </div>

                    {/* Botón final */}
                    <button
                        type="submit"
                        className="w-full bg-orange-400 text-white font-bold py-3 rounded-2xl shadow-2xl hover:scale-105 transition-transform text-lg mt-4"
                    >
                        Registrarse
                    </button>
                </form>

                {/* Footer */}
                <Footer />
            </div>
        </>
    );
}
