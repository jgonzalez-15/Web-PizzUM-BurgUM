import React, { useEffect, useState } from "react";

function Perfil() {
    const [perfil, setPerfil] = useState({
        nombre: "",
        apellido: "",
        telefono: "",
        fechaNac: "",
        contrasenia: "",
        confirmarContrasenia: "",
    });

    const [domicilios, setDomicilios] = useState([]);
    const [nuevaDireccion, setNuevaDireccion] = useState("");
    const [metodosPago, setMetodosPago] = useState([]);
    const [nuevoPago, setNuevoPago] = useState({
        numeroTarjeta: "",
        nombreTitular: "",
        fechaVencimiento: "",
    });
    const [cargando, setCargando] = useState(true);

    const usuario = JSON.parse(localStorage.getItem("sessionInfo"));

    useEffect(() => {
        const cargarPerfil = async () => {
            try {
                const resPerfil = await fetch(
                    `http://localhost:8080/api/cliente/${usuario.email}/obtenerPerfil`,
                    { method: "GET", credentials: "include" }
                );
                if (resPerfil.ok) {
                    const data = await resPerfil.json();
                    setPerfil({
                        nombre: data.nombre,
                        apellido: data.apellido,
                        telefono: data.telefono,
                        fechaNac: data.fechaNac,
                        contrasenia: "",
                        confirmarContrasenia: "",
                    });
                }

                const resDom = await fetch(
                    `http://localhost:8080/api/clienteDomicilio/${usuario.email}/listar`,
                    { method: "GET", credentials: "include" }
                );
                if (resDom.ok) setDomicilios(await resDom.json());

                const resPago = await fetch(
                    `http://localhost:8080/api/medioDePago/${usuario.email}/listar`,
                    { method: "GET", credentials: "include" }
                );
                if (resPago.ok) setMetodosPago(await resPago.json());
            } catch (err) {
                console.error("Error cargando perfil:", err);
            } finally {
                setCargando(false);
            }
        };

        cargarPerfil();
    }, []);

    const handlePerfilChange = (e) => {
        setPerfil({ ...perfil, [e.target.name]: e.target.value });
    };

    const handlePagoChange = (e) => {
        setNuevoPago({ ...nuevoPago, [e.target.name]: e.target.value });
    };

    const guardarPerfil = async () => {
        if (perfil.contrasenia && perfil.contrasenia !== perfil.confirmarContrasenia) {
            alert("Las contraseñas no coinciden");
            return;
        }

        try {
            const body = {
                nombre: perfil.nombre,
                apellido: perfil.apellido,
                telefono: perfil.telefono,
                fechaNac: perfil.fechaNac,
                contrasenia: perfil.contrasenia || null,
            };

            const res = await fetch(
                `http://localhost:8080/api/cliente/${usuario.email}/perfil`,
                {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(body),
                    credentials: "include",
                }
            );

            res.ok
                ? alert("Perfil actualizado correctamente")
                : alert("Error al actualizar el perfil");
        } catch (err) {
            console.error("Error guardando perfil:", err);
        }
    };

    const agregarDomicilio = async () => {
        if (!nuevaDireccion.trim()) return alert("Ingresa una dirección válida");

        try {
            const resCrear = await fetch("http://localhost:8080/api/domicilio/crearDomicilio", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ direccion: nuevaDireccion }),
                credentials: "include",
            });
            if (!resCrear.ok) throw new Error("Error al crear domicilio");

            const domicilio = await resCrear.json();

            const resAsociar = await fetch(
                "http://localhost:8080/api/clienteDomicilio/asociarDomicilio",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        email: usuario.email,
                        idDomicilio: domicilio.id,
                    }),
                    credentials: "include",
                }
            );

            if (resAsociar.ok) {
                setDomicilios([...domicilios, domicilio]);
                setNuevaDireccion("");
            }
        } catch (err) {
            console.error("Error agregando domicilio:", err);
        }
    };

    const eliminarDomicilio = async (id) => {
        if (!window.confirm("¿Eliminar este domicilio?")) return;
        try {
            const res = await fetch(
                `http://localhost:8080/api/clienteDomicilio/eliminar/${usuario.email}/${id}`,
                { method: "DELETE", credentials: "include" }
            );
            if (res.ok) setDomicilios(domicilios.filter((d) => d.id !== id));
        } catch (err) {
            console.error("Error eliminando domicilio:", err);
        }
    };

    const agregarMetodoPago = async () => {
        const { nombreTitular, numeroTarjeta, fechaVencimiento } = nuevoPago;
        if (!nombreTitular || !numeroTarjeta || !fechaVencimiento)
            return alert("Completa todos los campos del método de pago");

        try {
            const body = {
                nombreTitular,
                numeroTarjeta: Number(numeroTarjeta),
                fechaVencimiento: `${fechaVencimiento}-01`,
            };

            const res = await fetch(
                `http://localhost:8080/api/medioDePago/${usuario.email}/agregar`,
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(body),
                    credentials: "include",
                }
            );

            if (res.ok) {
                const data = await res.json();
                setMetodosPago([...metodosPago, data]);
                setNuevoPago({
                    numeroTarjeta: "",
                    nombreTitular: "",
                    fechaVencimiento: "",
                });
            } else {
                alert("Error al agregar método de pago");
            }
        } catch (err) {
            console.error("Error agregando método de pago:", err);
        }
    };

    const eliminarMetodoPago = async (id) => {
        if (!window.confirm("¿Eliminar este método de pago?")) return;
        try {
            const res = await fetch(
                `http://localhost:8080/api/medioDePago/eliminar/${usuario.email}/${id}`,
                { method: "DELETE", credentials: "include" }
            );
            if (res.ok) setMetodosPago(metodosPago.filter((m) => m.id !== id));
            else alert("No se pudo eliminar el método de pago.");
        } catch (err) {
            console.error("Error eliminando método de pago:", err);
        }
    };

    return (
        <div style={contenedor}>
            <h2 style={titulo}>Mi Perfil</h2>

            <h3 style={subtitulo}>Datos personales</h3>
            <div style={dosColumnas}>
                <input name="nombre" value={perfil.nombre} onChange={handlePerfilChange} placeholder="Nombre" style={input} />
                <input name="apellido" value={perfil.apellido} onChange={handlePerfilChange} placeholder="Apellido" style={input} />
                <input name="telefono" value={perfil.telefono} onChange={handlePerfilChange} placeholder="Teléfono" style={input} />
                <input type="date" name="fechaNac" value={perfil.fechaNac || ""} onChange={handlePerfilChange} style={input} />
            </div>

            <div style={{ display: "grid", gap: "1rem", marginBottom: "2rem" }}>
                <input type="password" name="contrasenia" placeholder="Nueva contraseña" value={perfil.contrasenia} onChange={handlePerfilChange} style={input} />
                <input type="password" name="confirmarContrasenia" placeholder="Confirmar contraseña" value={perfil.confirmarContrasenia} onChange={handlePerfilChange} style={input} />
            </div>

            <h3 style={subtitulo}>Domicilios</h3>
            {domicilios.length === 0 ? (
                <p style={{ color: "#777" }}>No tenés domicilios registrados.</p>
            ) : (
                domicilios.map((d) => (
                    <div key={d.id} style={fila}>
                        <span>{d.direccion}</span>
                        <button onClick={() => eliminarDomicilio(d.id)} style={botonRojo}>Eliminar</button>
                    </div>
                ))
            )}
            <div style={{ display: "flex", gap: "1rem", marginTop: "1rem" }}>
                <input placeholder="Nueva dirección" value={nuevaDireccion} onChange={(e) => setNuevaDireccion(e.target.value)} style={{ ...input, flex: 1 }} />
                <button onClick={agregarDomicilio} style={botonNaranja}>+ Agregar domicilio</button>
            </div>

            <h3 style={{ ...subtitulo, marginTop: "2rem" }}>Métodos de pago</h3>
            {metodosPago.length === 0 ? (
                <p style={{ color: "#777" }}>No tenés métodos de pago registrados.</p>
            ) : (
                metodosPago.map((m, i) => (
                    <div key={m?.id || i} style={fila}>
                        <span>
                            {m?.numeroTarjeta
                                ? `${String(m.numeroTarjeta)} - ${m.nombreTitular} (vto: ${m.fechaVencimiento})`
                                : `Método inválido - ${m?.nombreTitular}`}
                        </span>
                        <button onClick={() => eliminarMetodoPago(m.id)} style={botonRojo}>Eliminar</button>
                    </div>
                ))
            )}

            <div style={tresColumnas}>
                <input name="numeroTarjeta" placeholder="Número de tarjeta" value={nuevoPago.numeroTarjeta} onChange={handlePagoChange} style={input} />
                <input name="nombreTitular" placeholder="Nombre del titular" value={nuevoPago.nombreTitular} onChange={handlePagoChange} style={input} />
                <input type="month" name="fechaVencimiento" placeholder="Vencimiento" value={nuevoPago.fechaVencimiento} onChange={handlePagoChange} style={input} />
            </div>

            <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "1rem" }}>
                <button onClick={agregarMetodoPago} style={botonNaranja}>+ Agregar método de pago</button>
            </div>

            <button onClick={guardarPerfil} style={botonGuardar}>Guardar cambios</button>
        </div>
    );
}

// === ESTILOS ===
const contenedor = {
    backgroundColor: "white",
    borderRadius: "20px",
    boxShadow: "0 10px 25px rgba(0,0,0,0.1)",
    width: "70%",
    margin: "5rem auto 3rem",
    padding: "2rem 3rem",
};
const titulo = {
    textAlign: "center",
    color: "#ff8000",
    fontWeight: "700",
    fontSize: "1.8rem",
    marginBottom: "2rem",
};
const subtitulo = { fontWeight: "600", marginBottom: "1rem" };
const dosColumnas = { display: "grid", gridTemplateColumns: "1fr 1fr", gap: "1rem", marginBottom: "1rem" };
const tresColumnas = { display: "grid", gridTemplateColumns: "1fr 1fr 1fr", gap: "1rem", marginTop: "1rem" };
const input = {
    backgroundColor: "#f6f6f6",
    border: "none",
    borderRadius: "10px",
    padding: "0.8rem 1rem",
    width: "100%",
    outline: "none",
    fontSize: "0.95rem",
};
const botonNaranja = {
    backgroundColor: "#ff8000",
    color: "white",
    border: "none",
    padding: "0.5rem 1rem",
    borderRadius: "10px",
    cursor: "pointer",
    fontWeight: "600",
};
const botonRojo = {
    backgroundColor: "#ff4d4d",
    color: "white",
    border: "none",
    padding: "0.4rem 0.8rem",
    borderRadius: "8px",
    cursor: "pointer",
};
const botonGuardar = {
    display: "block",
    backgroundColor: "#ff8000",
    color: "white",
    border: "none",
    borderRadius: "12px",
    padding: "0.8rem 2rem",
    fontWeight: "600",
    margin: "2rem auto 0",
    cursor: "pointer",
    fontSize: "1rem",
};
const fila = {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    border: "1px solid #ddd",
    borderRadius: "8px",
    padding: "0.5rem 1rem",
    marginBottom: "0.5rem",
};

export default Perfil;
