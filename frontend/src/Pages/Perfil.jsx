import React, { useEffect, useState } from "react";
import Encabezado from "../Components/Encabezado.jsx";
import PieDePagina from "../Components/PieDePagina.jsx";

export default function Perfil() {
    const [datos, setDatos] = useState({
        nombre: "",
        apellido: "",
        telefono: "",
        fechaNac: "",
        contrasenia: "",
        confirmarContrasenia: "",
    });

    const [domicilios, setDomicilios] = useState([]);
    const [direccion, setDireccion] = useState("");
    const [editandoDomicilio, setEditandoDomicilio] = useState(null);
    const [pagos, setPagos] = useState([]);
    const [editandoPago, setEditandoPago] = useState(null);
    const [nuevoPago, setNuevoPago] = useState({
        numeroTarjeta: "",
        nombreTitular: "",
        fechaVencimiento: "",
    });

    const usuario = JSON.parse(localStorage.getItem("sessionInfo"));

    useEffect(() => {
        cargarDatos();
    }, []);

    const cargarDatos = async () => {
        try {
            const headers = {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            };

            const perfil = await fetch("http://localhost:8080/api/cliente/obtenerPerfil", { headers });
            if (perfil.ok) {
                const data = await perfil.json();
                setDatos({
                    nombre: data.nombre,
                    apellido: data.apellido,
                    telefono: data.telefono,
                    fechaNac: data.fechaNac,
                    contrasenia: "",
                    confirmarContrasenia: "",
                });
            }

            const dom = await fetch("http://localhost:8080/api/clienteDomicilio/listar", { headers });
            if (dom.ok) setDomicilios(await dom.json());

            const pagosRes = await fetch("http://localhost:8080/api/medioDePago/listar", { headers });
            if (pagosRes.ok) setPagos(await pagosRes.json());
        } catch (error) {
            console.error("Error al cargar el perfil:", error);
        }
    };

    const guardarCambios = async () => {
        if (datos.contrasenia && datos.contrasenia !== datos.confirmarContrasenia)
            return alert("Las contraseñas no coinciden");

        try {
            const body = {
                nombre: datos.nombre,
                apellido: datos.apellido,
                telefono: datos.telefono,
                fechaNac: datos.fechaNac,
                contrasenia: datos.contrasenia || null,
            };

            const respuesta = await fetch("http://localhost:8080/api/cliente/perfil", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify(body),
                credentials: "include",
            });

            if (respuesta.ok) alert("Perfil actualizado correctamente");
            else alert("Error al actualizar el perfil");
        } catch (error) {
            console.error("Error guardando perfil:", error);
        }
    };

    const agregarDomicilio = async () => {
        if (!direccion.trim()) return alert("Ingresá una dirección válida");

        try {
            const crear = await fetch("http://localhost:8080/api/domicilio/crearDomicilio", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify({ direccion }),
                credentials: "include",
            });

            if (!crear.ok) throw new Error("Error al crear domicilio");
            const nuevo = await crear.json();

            const asociar = await fetch("http://localhost:8080/api/clienteDomicilio/asociarDomicilio", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify({ email: usuario.email, idDomicilio: nuevo.id }),
                credentials: "include",
            });

            if (asociar.ok) {
                setDireccion("");
                await cargarDatos();
            }
        } catch (error) {
            console.error("Error agregando domicilio:", error);
        }
    };

    const editarDomicilio = async (id, nuevaDireccion) => {
        if (!nuevaDireccion.trim()) return alert("Ingresá una dirección válida");
        try {
            const respuesta = await fetch(`http://localhost:8080/api/domicilio/editar/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify({ direccion: nuevaDireccion }),
                credentials: "include",
            });

            if (respuesta.ok) {
                setEditandoDomicilio(null);
                await cargarDatos();
            } else {
                alert("Error al editar domicilio");
            }
        } catch (error) {
            console.error("Error editando domicilio:", error);
        }
    };

    const eliminarDomicilio = async (id) => {
        if (!window.confirm("¿Eliminar este domicilio?")) return;
        try {
            const respuesta = await fetch(`http://localhost:8080/api/clienteDomicilio/eliminar/${id}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                credentials: "include",
            });
            if (respuesta.ok) {
                setDomicilios(domicilios.filter((d) => d.id !== id));
            } else {
                alert("Error al eliminar domicilio");
            }
        } catch (error) {
            console.error("Error eliminando domicilio:", error);
        }
    };

    const agregarPago = async () => {
        const { nombreTitular, numeroTarjeta, fechaVencimiento } = nuevoPago;
        if (!nombreTitular || !numeroTarjeta || !fechaVencimiento)
            return alert("Completá todos los campos del método de pago");

        try {
            const respuesta = await fetch("http://localhost:8080/api/medioDePago/agregar", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify({
                    nombreTitular,
                    numeroTarjeta: Number(numeroTarjeta),
                    fechaVencimiento: `${fechaVencimiento}-28`,
                }),
                credentials: "include",
            });

            if (respuesta.ok) {
                const data = await respuesta.json();
                setPagos([...pagos, data]);
                setNuevoPago({ numeroTarjeta: "", nombreTitular: "", fechaVencimiento: "" });
            }
        } catch (error) {
            console.error("Error agregando método de pago:", error);
        }
    };

    const editarPago = async (id, nuevoNombre, nuevaTarjeta, nuevaFecha) => {
        if (!nuevoNombre || !nuevaTarjeta || !nuevaFecha)
            return alert("Completá todos los campos");

        try {
            const respuesta = await fetch(`http://localhost:8080/api/medioDePago/editar/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify({
                    nombreTitular: nuevoNombre,
                    numeroTarjeta: Number(nuevaTarjeta),
                    fechaVencimiento: `${nuevaFecha}`,
                }),
                credentials: "include",
            });

            if (respuesta.ok) {
                const actualizado = await respuesta.json();
                setPagos(pagos.map((p) => (p.id === id ? actualizado : p)));
                setEditandoPago(null);
            } else {
                alert("Error al editar método de pago");
            }
        } catch (error) {
            console.error("Error editando método de pago:", error);
        }
    };

    const eliminarPago = async (id) => {
        if (!window.confirm("¿Eliminar este método de pago?")) return;
        try {
            const respuesta = await fetch(`http://localhost:8080/api/medioDePago/eliminar/${id}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                credentials: "include",
            });
            if (respuesta.ok) setPagos(pagos.filter((p) => p.id !== id));
        } catch (error) {
            console.error("Error eliminando método de pago:", error);
        }
    };

    return (
        <>
            <Encabezado />
            <div className="pt-24 min-h-screen w-full flex flex-col items-center bg-gray-50 pb-28">
                <h1 className="font-extrabold text-3xl mb-8 text-gray-800">Mi perfil</h1>

                <div className="flex flex-col gap-8 w-[calc(100vw-4rem)] md:w-[calc(100vw-28rem)] max-w-3xl mb-8">
                    {/* Datos personales */}
                    <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-200">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">Datos personales</h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <input className="bg-gray-100 rounded-2xl p-2" placeholder="Nombre" value={datos.nombre} onChange={(e) => setDatos({ ...datos, nombre: e.target.value })} />
                            <input className="bg-gray-100 rounded-2xl p-2" placeholder="Apellido" value={datos.apellido} onChange={(e) => setDatos({ ...datos, apellido: e.target.value })} />
                            <input className="bg-gray-100 rounded-2xl p-2" placeholder="Teléfono" value={datos.telefono} onChange={(e) => setDatos({ ...datos, telefono: e.target.value })} />
                            <input type="date" className="bg-gray-100 rounded-2xl p-2" value={datos.fechaNac || ""} onChange={(e) => setDatos({ ...datos, fechaNac: e.target.value })} />
                            <input type="password" className="bg-gray-100 rounded-2xl p-2" placeholder="Nueva contraseña" value={datos.contrasenia} onChange={(e) => setDatos({ ...datos, contrasenia: e.target.value })} />
                            <input type="password" className="bg-gray-100 rounded-2xl p-2" placeholder="Confirmar contraseña" value={datos.confirmarContrasenia} onChange={(e) => setDatos({ ...datos, confirmarContrasenia: e.target.value })} />
                        </div>
                    </div>

                    {/* Domicilios */}
                    <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-200">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">Domicilios</h2>

                        <div className="flex flex-col gap-3">
                            {domicilios.map((d) =>
                                editandoDomicilio === d.id ? (
                                    <div key={d.id} className="flex flex-wrap gap-2 items-center bg-gray-50 rounded-xl p-3 border border-gray-200">
                                        <input
                                            id={`edit-${d.id}`}
                                            className="flex-1 bg-white rounded-xl p-2 border border-gray-300"
                                            defaultValue={d.direccion}
                                        />
                                        <div className="flex gap-2">
                                            <button
                                                onClick={() => editarDomicilio(d.id, document.querySelector(`#edit-${d.id}`)?.value || d.direccion)}
                                                className="px-4 py-1.5 rounded-xl bg-orange-500 text-white hover:bg-orange-600 transition"
                                            >
                                                Guardar
                                            </button>
                                            <button
                                                onClick={() => setEditandoDomicilio(null)}
                                                className="px-4 py-1.5 rounded-xl bg-gray-300 text-gray-800 hover:bg-gray-400 transition"
                                            >
                                                Cancelar
                                            </button>
                                        </div>
                                    </div>
                                ) : (
                                    <div key={d.id} className="flex flex-wrap justify-between items-center border border-gray-200 rounded-xl p-3 bg-gray-50">
                                        <span className="truncate">{d.direccion}</span>
                                        <div className="flex gap-2 mt-2 sm:mt-0">
                                            <button
                                                onClick={() => setEditandoDomicilio(d.id)}
                                                className="px-4 py-1.5 rounded-xl bg-orange-500 text-white hover:bg-orange-600 transition"
                                            >
                                                Editar
                                            </button>
                                            <button
                                                onClick={() => eliminarDomicilio(d.id)}
                                                className="px-4 py-1.5 rounded-xl bg-red-500 text-white hover:bg-red-600 transition"
                                            >
                                                Eliminar
                                            </button>
                                        </div>
                                    </div>
                                )
                            )}
                        </div>

                        <div className="flex flex-wrap mt-4 gap-2">
                            <input
                                className="flex-1 bg-gray-100 rounded-xl p-2 border border-gray-200"
                                placeholder="Nueva dirección"
                                value={direccion}
                                onChange={(e) => setDireccion(e.target.value)}
                            />
                            <button
                                onClick={agregarDomicilio}
                                className="bg-orange-500 text-white px-5 py-2 rounded-xl hover:bg-orange-600 transition"
                            >
                                Agregar
                            </button>
                        </div>
                    </div>

                    {/* Métodos de pago */}
                    <div className="bg-white shadow-xl rounded-2xl p-8 border border-gray-200">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">Métodos de pago</h2>

                        <div className="flex flex-col gap-3">
                            {pagos.map((m) =>
                                editandoPago === m.id ? (
                                    <div key={m.id} className="flex flex-wrap gap-2 items-center bg-gray-50 p-3 border border-gray-200 rounded-xl">
                                        <input className="flex-1 bg-white rounded-xl p-2 border border-gray-300" defaultValue={m.numeroTarjeta} onChange={(e) => (m.numeroTarjeta = e.target.value)} />
                                        <input className="flex-1 bg-white rounded-xl p-2 border border-gray-300" defaultValue={m.nombreTitular} onChange={(e) => (m.nombreTitular = e.target.value)} />
                                        <input type="month" className="bg-white rounded-xl p-2 border border-gray-300" defaultValue={m.fechaVencimiento?.slice(0, 7)} onChange={(e) => (m.fechaVencimiento = e.target.value)} />
                                        <div className="flex gap-2">
                                            <button onClick={() => editarPago(m.id, m.nombreTitular, m.numeroTarjeta, m.fechaVencimiento)} className="px-4 py-1.5 rounded-xl bg-orange-500 text-white hover:bg-orange-600 transition">
                                                Guardar
                                            </button>
                                            <button onClick={() => setEditandoPago(null)} className="px-4 py-1.5 rounded-xl bg-gray-300 text-gray-800 hover:bg-gray-400 transition">
                                                Cancelar
                                            </button>
                                        </div>
                                    </div>
                                ) : (
                                    <div key={m.id} className="flex flex-wrap justify-between items-center border border-gray-200 rounded-xl p-3 bg-gray-50">
                                        <span className="truncate">{`${m.numeroTarjeta} - ${m.nombreTitular}`}</span>
                                        <div className="flex gap-2 mt-2 sm:mt-0">
                                            <button onClick={() => setEditandoPago(m.id)} className="px-4 py-1.5 rounded-xl bg-orange-500 text-white hover:bg-orange-600 transition">
                                                Editar
                                            </button>
                                            <button onClick={() => eliminarPago(m.id)} className="px-4 py-1.5 rounded-xl bg-red-500 text-white hover:bg-red-600 transition">
                                                Eliminar
                                            </button>
                                        </div>
                                    </div>
                                )
                            )}
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-3 gap-2 mt-4">
                            <input className="bg-gray-100 rounded-xl p-2 border border-gray-200" placeholder="Número de tarjeta" value={nuevoPago.numeroTarjeta} onChange={(e) => setNuevoPago({ ...nuevoPago, numeroTarjeta: e.target.value })} />
                            <input className="bg-gray-100 rounded-xl p-2 border border-gray-200" placeholder="Nombre del titular" value={nuevoPago.nombreTitular} onChange={(e) => setNuevoPago({ ...nuevoPago, nombreTitular: e.target.value })} />
                            <input type="month" className="bg-gray-100 rounded-xl p-2 border border-gray-200" value={nuevoPago.fechaVencimiento} onChange={(e) => setNuevoPago({ ...nuevoPago, fechaVencimiento: e.target.value })} />
                        </div>

                        <button
                            onClick={agregarPago}
                            className="mt-3 bg-orange-500 text-white px-5 py-2 rounded-xl hover:bg-orange-600 transition"
                        >
                            Agregar método
                        </button>
                    </div>
                </div>

                {/* Botón para guardar */}
                <div className="fixed bottom-4 right-4 z-40">
                    <button
                        onClick={guardarCambios}
                        className="bg-orange-500 text-white font-bold py-3 px-6 rounded-2xl shadow-xl hover:scale-105 transition-transform text-lg"
                    >
                        Guardar cambios
                    </button>
                </div>
            </div>

            <PieDePagina />
        </>
    );
}
