import { useEffect, useState } from "react";

export default function AgregarAdministradores() {
    const [administradores, setAdministradores] = useState([]);
    const [mostrarFormulario, setMostrarFormulario] = useState(false);
    const [editando, setEditando] = useState(false);
    const [adminSeleccionado, setAdminSeleccionado] = useState(null);

    const [nuevoAdmin, setNuevoAdmin] = useState({
        email: "",
        contrasenia: "",
        nombre: "",
        apellido: "",
        telefono: "",
        fechaNac: "",
        cedula: "",
        domicilio: "",
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
        if (
            !nuevoAdmin.email ||
            !nuevoAdmin.contrasenia ||
            !nuevoAdmin.nombre ||
            !nuevoAdmin.apellido ||
            !nuevoAdmin.telefono ||
            !nuevoAdmin.fechaNac ||
            !nuevoAdmin.cedula ||
            !nuevoAdmin.domicilio
        ) {
            alert("Debes completar todos los campos.");
            return;
        }

        try {
            const respuesta = await fetch("http://localhost:8080/api/administrador/agregarAdmin", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify({
                    ...nuevoAdmin,
                    telefono: parseInt(nuevoAdmin.telefono),
                    cedula: parseInt(nuevoAdmin.cedula),
                }),
            });

            if (respuesta.ok) {
                cerrarFormulario();
                cargarAdministradores();
            } else {
                alert("Error al crear administrador.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al conectar con el servidor.");
        }
    };

    const eliminarAdministrador = async (email) => {
        if (!window.confirm(`¿Seguro que deseas eliminar a ${email}?`)) return;

        try {
            const respuesta = await fetch(`http://localhost:8080/api/administrador/eliminar/${email}`, {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (respuesta.ok) {
                cargarAdministradores();
            } else {
                alert("No se pudo eliminar el administrador.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al eliminar administrador.");
        }
    };

    const editarAdministrador = (admin) => {
        setEditando(true);
        setMostrarFormulario(true);
        setAdminSeleccionado(admin);

        setNuevoAdmin({
            email: admin.email ?? "",
            contrasenia: "",
            nombre: admin.nombre ?? "",
            apellido: admin.apellido ?? "",
            telefono: admin.telefono ?? "",
            fechaNac: admin.fechaNac ?? "",
            cedula: admin.cedula ?? "",
            domicilio: admin.domicilio ?? "",
        });
    };

    const actualizarAdministrador = async () => {
        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/administrador/editar/${adminSeleccionado.email}`,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                    body: JSON.stringify({
                        ...nuevoAdmin,
                        telefono: parseInt(nuevoAdmin.telefono),
                        cedula: parseInt(nuevoAdmin.cedula),
                    }),
                }
            );

            if (respuesta.ok) {
                cerrarFormulario();
                cargarAdministradores();
            } else {
                alert("No se pudo actualizar el administrador.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al actualizar administrador.");
        }
    };

    const cerrarFormulario = () => {
        setMostrarFormulario(false);
        setEditando(false);
        setAdminSeleccionado(null);
        setNuevoAdmin({
            email: "",
            contrasenia: "",
            nombre: "",
            apellido: "",
            telefono: "",
            fechaNac: "",
            cedula: "",
            domicilio: "",
        });
    };

    return (
        <div className="w-full bg-gray-50 flex flex-col items-center py-10 px-3 md:px-6">
            <h1 className="m-1 text-2xl md:text-3xl font-extrabold text-gray-800 mb-6 text-center">
                Administradores
            </h1>

            <div className="w-full max-w-5xl bg-white rounded-2xl shadow-xl p-4 pt-8 md:p-8 border border-gray-200">

                <div className="flex justify-center mb-8">
                    <button
                        onClick={() => {
                            setMostrarFormulario(!mostrarFormulario);
                            setEditando(false);
                        }}
                        className="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-3 px-6 rounded-full shadow transition"
                    >
                        {mostrarFormulario ? "Cerrar formulario" : "+ Nuevo administrador"}
                    </button>
                </div>

                {/* Formulario */}
                {mostrarFormulario && (
                    <div className="mb-8 bg-gray-50 p-6 rounded-2xl border border-gray-200 shadow-inner">
                        <h2 className="text-xl font-semibold mb-4 text-gray-800 text-center">
                            {editando ? "Editar administrador" : "Nuevo administrador"}
                        </h2>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            {[
                                ["Correo electrónico", "email", editando],
                                ["Contraseña", "contrasenia", false, "password"],
                                ["Nombre", "nombre"],
                                ["Apellido", "apellido"],
                                ["Teléfono", "telefono"],
                                ["Fecha de nacimiento", "fechaNac", false, "date"],
                                ["Cédula", "cedula"],
                                ["Domicilio", "domicilio"],
                            ].map(([label, field, disabled, type = "text"]) => (
                                <input
                                    key={field}
                                    type={type}
                                    placeholder={label}
                                    disabled={disabled}
                                    className="bg-white border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400"
                                    value={nuevoAdmin[field] ?? ""}
                                    onChange={(e) =>
                                        setNuevoAdmin({ ...nuevoAdmin, [field]: e.target.value })
                                    }
                                />
                            ))}
                        </div>

                        <div className="flex md:flex-row flex-col justify-end mt-6 gap-4">
                            <button
                                onClick={cerrarFormulario}
                                className="bg-gray-300 hover:bg-gray-400 text-gray-800 rounded-2xl py-2 px-6"
                            >
                                Cancelar
                            </button>

                            {editando ? (
                                <button
                                    onClick={actualizarAdministrador}
                                    className="bg-orange-500 hover:bg-orange-600 text-white rounded-2xl py-2 px-6"
                                >
                                    Guardar cambios
                                </button>
                            ) : (
                                <button
                                    onClick={crearAdministrador}
                                    className="bg-orange-500 hover:bg-orange-600 text-white rounded-2xl py-2 px-6"
                                >
                                    Crear administrador
                                </button>
                            )}
                        </div>
                    </div>
                )}

                {/* Lista */}
                <div className="divide-y divide-gray-200 flex flex-col md:flex-row justify-center md:justify-between md:gap-5">
                    {administradores.length > 0 ? (
                        administradores.map((admin) => (
                            <div
                                key={admin.email}
                                className="py-3 flex md:flex-row flex-col justify-between md:items-center w-full hover:bg-gray-100 px-3 rounded-xl transition md:bg-gray-50"
                            >
                                <div>
                                    <p className="font-semibold text-gray-800">{admin.nombre} {admin.apellido}</p>
                                    <p className="text-sm text-gray-500">
                                        {admin.email}
                                    </p>
                                </div>

                                <div className="mt-3 md:mt-0">
                                    <div className="flex gap-3">
                                        <button
                                            onClick={() => editarAdministrador(admin)}
                                            className="bg-orange-500 hover:bg-orange-600 text-white text-sm px-4 py-1.5 rounded-2xl font-semibold shadow"
                                        >
                                            Ver / Editar
                                        </button>

                                        <button
                                            onClick={() => eliminarAdministrador(admin.email)}
                                            className="bg-red-500 hover:bg-red-600 text-white text-sm px-4 py-1.5 rounded-2xl font-semibold shadow"
                                        >
                                            Eliminar
                                        </button>
                                    </div>
                                </div>
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
