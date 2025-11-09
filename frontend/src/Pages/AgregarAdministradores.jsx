import { useEffect, useState } from "react";

export default function AgregarAdministradores() {
    const [administradores, setAdministradores] = useState([]);
    const [mostrarFormulario, setMostrarFormulario] = useState(false);
    const [nuevoAdmin, setNuevoAdmin] = useState({
        email: "",
        contrasenia: "",
        nombre: "",
        apellido: "",
        telefono: "",
        fechaNac: "",
    });

    const cargarAdministradores = async () => {
        try {
            const respuesta = await fetch("http://localhost:8080/api/administrador/listar", {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (respuesta.ok) {
                setAdministradores(await respuesta.json());
            } else {
                alert("No se pudieron obtener los administradores.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al conectar con el servidor.");
        }
    };

    useEffect(() => {
        cargarAdministradores();
    }, []);

    const crearAdministrador = async () => {
        try {
            const respuesta = await fetch("http://localhost:8080/api/admin/crear", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify(nuevoAdmin),
            });

            if (respuesta.ok) {
                setMostrarFormulario(false);
                setNuevoAdmin({
                    email: "",
                    contrasenia: "",
                    nombre: "",
                    apellido: "",
                    telefono: "",
                    fechaNac: "",
                });
                cargarAdministradores();
            } else {
                alert("Los datos son incorrectos o el administrador ya existe.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al conectar con el servidor.");
        }
    };

    return (
        <div className="min-h-screen w-full bg-gray-50 flex flex-col items-center py-10 px-6">
            <h1 className="text-3xl font-extrabold text-gray-800 mb-6 text-center">
                Administradores
            </h1>

            <div className="w-full max-w-5xl bg-white rounded-2xl shadow-lg p-8 border border-gray-100">
                <div className="flex justify-center mb-8">
                    <button
                        onClick={() => setMostrarFormulario(!mostrarFormulario)}
                        className="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-3 px-6 rounded-full shadow-md transition-transform transform hover:scale-105"
                    >
                        {mostrarFormulario ? "Cancelar" : "+ Agregar nuevo administrador"}
                    </button>
                </div>

                {/* Formulario visible debajo del botón */}
                {mostrarFormulario && (
                    <div className="mb-8 bg-gray-50 p-6 rounded-2xl shadow-inner">
                        <h2 className="text-xl font-semibold mb-4 text-gray-700 text-center">
                            Nuevo administrador
                        </h2>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <input
                                type="text"
                                placeholder="Correo electrónico"
                                className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nuevoAdmin.email}
                                onChange={(e) =>
                                    setNuevoAdmin({ ...nuevoAdmin, email: e.target.value })
                                }
                            />
                            <input
                                type="password"
                                placeholder="Contraseña"
                                className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nuevoAdmin.contrasenia}
                                onChange={(e) =>
                                    setNuevoAdmin({ ...nuevoAdmin, contrasenia: e.target.value })
                                }
                            />
                            <input
                                type="text"
                                placeholder="Nombre"
                                className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nuevoAdmin.nombre}
                                onChange={(e) =>
                                    setNuevoAdmin({ ...nuevoAdmin, nombre: e.target.value })
                                }
                            />
                            <input
                                type="text"
                                placeholder="Apellido"
                                className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nuevoAdmin.apellido}
                                onChange={(e) =>
                                    setNuevoAdmin({ ...nuevoAdmin, apellido: e.target.value })
                                }
                            />
                            <input
                                type="text"
                                placeholder="Teléfono"
                                className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nuevoAdmin.telefono}
                                onChange={(e) =>
                                    setNuevoAdmin({ ...nuevoAdmin, telefono: e.target.value })
                                }
                            />
                            <input
                                type="date"
                                className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                                value={nuevoAdmin.fechaNac}
                                onChange={(e) =>
                                    setNuevoAdmin({ ...nuevoAdmin, fechaNac: e.target.value })
                                }
                            />
                        </div>

                        <div className="flex justify-end mt-6">
                            <button
                                onClick={crearAdministrador}
                                className="bg-orange-500 text-white rounded-full py-2 px-6 font-semibold hover:bg-orange-600 transition"
                            >
                                Guardar
                            </button>
                        </div>
                    </div>
                )}

                {/* Lista de administradores */}
                <div className="divide-y divide-gray-200">
                    {administradores.length > 0 ? (
                        administradores.map((admin) => (
                            <div
                                key={admin.email}
                                className="py-3 flex justify-between items-center hover:bg-gray-100 px-3 rounded-xl transition-colors"
                            >
                                <div>
                                    <p className="font-semibold text-gray-800">{admin.email}</p>
                                    {(admin.nombre || admin.apellido) && (
                                        <p className="text-sm text-gray-500">
                                            {admin.nombre} {admin.apellido}
                                        </p>
                                    )}
                                </div>
                                <span className="text-sm text-gray-400">
                                    {admin.telefono || ""}
                                </span>
                            </div>
                        ))
                    ) : (
                        <p className="text-gray-500 italic text-center py-4">
                            No hay administradores registrados.
                        </p>
                    )}
                </div>
            </div>
        </div>
    );
}
