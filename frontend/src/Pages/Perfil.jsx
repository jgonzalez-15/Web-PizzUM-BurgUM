import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";

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
    const [pagos, setPagos] = useState([]);
    const [nuevoPago, setNuevoPago] = useState({
        numeroTarjeta: "",
        nombreTitular: "",
        fechaVencimiento: "",
    });

    const navigate = useNavigate();
    const usuario = JSON.parse(localStorage.getItem("sessionInfo"));

    // Cargar datos del perfil al montar
    useEffect(() => {
        const cargarDatos = async () => {
            try {
                const respuestaPerfil = await fetch(`http://localhost:8080/api/cliente/obtenerPerfil`, {
                    headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`}});
                if (respuestaPerfil.ok) {
                    const data = await respuestaPerfil.json();
                    setDatos({
                        nombre: data.nombre,
                        apellido: data.apellido,
                        telefono: data.telefono,
                        fechaNac: data.fechaNac,
                        contrasenia: "",
                        confirmarContrasenia: "",
                    });
                }

                const respuestaDomicilio = await fetch(`http://localhost:8080/api/clienteDomicilio/${usuario.email}/listar`, {
                    headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`}});
                if (respuestaDomicilio.ok) setDomicilios(await respuestaDomicilio.json());

                const respuestaMedioDePago = await fetch(`http://localhost:8080/api/medioDePago/${usuario.email}/listar`, {
                    headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`}});
                if (respuestaMedioDePago.ok) setPagos(await respuestaMedioDePago.json());
            } catch (error) {
                console.error("Error al cargar el perfil:", error);
            }
        };

        cargarDatos();
    }, []);

    const guardarCambios = async () => {
        if (datos.contrasenia && datos.contrasenia !== datos.confirmarContrasenia) {
            alert("Las contraseñas no coinciden");
            return;
        }

        try {
            const body = {
                nombre: datos.nombre,
                apellido: datos.apellido,
                telefono: datos.telefono,
                fechaNac: datos.fechaNac,
                contrasenia: datos.contrasenia || null,
            };

            const respuesta = await fetch(`http://localhost:8080/api/cliente/perfil`, {
                method: "PUT",
                headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`},
                body: JSON.stringify(body),
                credentials: "include",
            });

            respuesta.ok
                ? alert("Perfil actualizado correctamente")
                : alert("Error al actualizar el perfil");
        } catch (error) {
            console.error("Error guardando perfil:", error);
        }
    };

    const agregarDomicilio = async () => {
        if (!direccion.trim()) return alert("Ingresá una dirección válida");

        try {
            const crear = await fetch("http://localhost:8080/api/domicilio/crearDomicilio", {
                method: "POST",
                headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`},
                body: JSON.stringify({ direccion }),
                credentials: "include"
            });

            if (!crear.ok) throw new Error("Error al crear domicilio");

            const nuevo = await crear.json();

            const asociar = await fetch("http://localhost:8080/api/clienteDomicilio/asociarDomicilio", {
                method: "POST",
                headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`},
                body: JSON.stringify({
                    email: usuario.email,
                    idDomicilio: nuevo.id,
                }),
                credentials: "include"
            });

            if (asociar.ok) {
                setDomicilios([...domicilios, nuevo]);
                setDireccion("");
            }
        } catch (error) {
            console.error("Error agregando domicilio:", error);
        }
    };

    const eliminarDomicilio = async (id) => {
        if (!window.confirm("¿Eliminar este domicilio?")) return;
        try {
            const respuesta = await fetch(`http://localhost:8080/api/clienteDomicilio/eliminar/${id}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`},
                credentials: "include"
            });
            if (respuesta.ok) {
                setDomicilios(domicilios.filter((d) => d.id !== id))
            } else {
                let mensajeError = "No se pudo eliminar el domicilio";
                try {
                    const data = await respuesta.json();
                    mensajeError = data.message || JSON.stringify(data);
                } catch {
                    const texto = await respuesta.text();
                    if (texto) mensajeError = texto;
                }
                alert(mensajeError);
            }
        } catch (error) {
            console.error("Error eliminando domicilio:", error);
            alert("Ocurrió un error al intentar eliminar el domicilio.");
        }
    };

    const agregarPago = async () => {
        const { nombreTitular, numeroTarjeta, fechaVencimiento } = nuevoPago;
        if (!nombreTitular || !numeroTarjeta || !fechaVencimiento)
            return alert("Completá todos los campos del método de pago");

        try {
            const respuesta = await fetch(`http://localhost:8080/api/medioDePago/agregar`, {
                method: "POST",
                headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`},
                body: JSON.stringify({
                    nombreTitular,
                    numeroTarjeta: Number(numeroTarjeta),
                    fechaVencimiento: `${fechaVencimiento}-01`,
                }),
                credentials: "include"
            });

            if (respuesta.ok) {
                const data = await respuesta.json();
                setPagos([...pagos, data]);
                setNuevoPago({ numeroTarjeta: "", nombreTitular: "", fechaVencimiento: "" });
            } else alert("Error al agregar método de pago");
        } catch (error) {
            console.error("Error agregando método de pago:", error);
        }
    };

    const eliminarPago = async (id) => {
        if (!window.confirm("¿Eliminar este método de pago?")) return;
        try {
            const respuesta = await fetch(`http://localhost:8080/api/medioDePago/eliminar/${id}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}`},
                credentials: "include"
            });
            if (respuesta.ok) {
                setPagos(pagos.filter((p) => p.id !== id));
            } else {
                let mensajeError = "No se pudo eliminar el método de pago.";
                try {
                    const data = await respuesta.json();
                    mensajeError = data.message || JSON.stringify(data);
                } catch {
                    const texto = await respuesta.text();
                    if (texto) mensajeError = texto;
                }
                alert(mensajeError);
            }
        } catch (error) {
            console.error("Error eliminando método de pago:", error);
        }
    };

    return (
        <>
            <MainHeader />

            <div className="pt-24 min-h-screen w-full flex flex-col justify-between items-center bg-white">
                {/* Título */}
                <h1 className="font-bold text-3xl mb-6 text-center text-gray-800">
                    Mi perfil
                </h1>

                {/* Contenido */}
                <div className="flex flex-col gap-8 w-[calc(100vw-4rem)] md:w-[calc(100vw-28rem)] max-w-3xl mb-8">
                    {/* Datos personales */}
                    <div className="bg-gray-50 shadow-2xl rounded-2xl p-8">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">
                            Datos personales
                        </h2>
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
                    <div className="bg-gray-50 shadow-2xl rounded-2xl p-8">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">Domicilios</h2>
                        {domicilios.length === 0 ? (
                            <p className="text-gray-500">No tenés domicilios registrados.</p>
                        ) : (
                            domicilios.map((d) => (
                                <div key={d.id} className="flex justify-between items-center border rounded-xl p-2 mb-2">
                                    <span>{d.direccion}</span>
                                    <button onClick={() => eliminarDomicilio(d.id)} className="bg-red-500 text-white px-3 py-1 rounded-lg">
                                        Eliminar
                                    </button>
                                </div>
                            ))
                        )}
                        <div className="flex gap-2 mt-3">
                            <input className="bg-gray-100 rounded-2xl p-2 flex-1" placeholder="Nueva dirección" value={direccion} onChange={(e) => setDireccion(e.target.value)} />
                            <button onClick={agregarDomicilio} className="bg-orange-400 text-white font-bold px-3 py-2 rounded-2xl">+ Agregar domicilio</button>
                        </div>
                    </div>

                    {/* Métodos de pago */}
                    <div className="bg-gray-50 shadow-2xl rounded-2xl p-8">
                        <h2 className="font-bold text-xl mb-4 text-gray-800">Métodos de pago</h2>
                        {pagos.length === 0 ? (
                            <p className="text-gray-500">No tenés métodos de pago registrados.</p>
                        ) : (
                            pagos.map((m) => (
                                <div key={m.id} className="flex justify-between items-center border rounded-xl p-2 mb-2">
                                    <span>{`${m.numeroTarjeta} - ${m.nombreTitular}`}</span>
                                    <button onClick={() => eliminarPago(m.id)} className="bg-red-500 text-white px-3 py-1 rounded-lg">
                                        Eliminar
                                    </button>
                                </div>
                            ))
                        )}
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mt-4">
                            <input className="bg-gray-100 rounded-2xl p-2" placeholder="Número de tarjeta" value={nuevoPago.numeroTarjeta} onChange={(e) => setNuevoPago({ ...nuevoPago, numeroTarjeta: e.target.value })} />
                            <input className="bg-gray-100 rounded-2xl p-2" placeholder="Nombre del titular" value={nuevoPago.nombreTitular} onChange={(e) => setNuevoPago({ ...nuevoPago, nombreTitular: e.target.value })} />
                            <input type="month" className="bg-gray-100 rounded-2xl p-2" value={nuevoPago.fechaVencimiento} onChange={(e) => setNuevoPago({ ...nuevoPago, fechaVencimiento: e.target.value })} />
                        </div>
                        <div className="flex justify-end mt-3">
                            <button onClick={agregarPago} className="bg-orange-400 text-white font-bold px-3 py-2 rounded-2xl">
                                + Agregar método de pago
                            </button>
                        </div>
                    </div>

                    {/* Guardar cambios */}
                    <button onClick={guardarCambios} className="w-full bg-orange-400 text-white font-bold py-3 rounded-2xl shadow-2xl hover:scale-105 transition-transform text-lg mt-4">
                        Guardar cambios
                    </button>
                </div>
            </div>

            <Footer />
        </>
    );
}
