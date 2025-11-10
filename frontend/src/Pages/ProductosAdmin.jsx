import { useState, useEffect } from "react";

export default function ProductosAdmin() {
    const [productos, setProductos] = useState([]);
    const [productosFiltrados, setProductosFiltrados] = useState([]);
    const [tipoFiltro, setTipoFiltro] = useState("Todos");
    const [mostrarFormulario, setMostrarFormulario] = useState(false);
    const [productoEditando, setProductoEditando] = useState(null);

    const [productoActual, setProductoActual] = useState({
        nombre: "",
        precio: "",
        tipo: "Masa",
        sinTacc: false,
        visible: true,
    });

    const cargarProductos = async () => {
        try {
            const respuesta = await fetch("http://localhost:8080/api/producto/listarAdmin", {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });
            if (respuesta.ok) {
                const datos = await respuesta.json();

                const productosOrdenados = datos.sort((a, b) =>
                    a.nombre.localeCompare(b.nombre, "es", { sensitivity: "base" })
                );

                setProductos(productosOrdenados);
            } else {
                alert("No se pudieron obtener los productos.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al conectar con el servidor.");
        }
    };


    useEffect(() => {
        cargarProductos();
    }, []);

    useEffect(() => {
        setProductosFiltrados(
            tipoFiltro === "Todos"
                ? productos
                : productos.filter((p) => p.tipo === tipoFiltro)
        );
    }, [tipoFiltro, productos]);

    const guardarProducto = async () => {
        const esEdicion = !!productoEditando;
        const url = esEdicion
            ? `http://localhost:8080/api/producto/${productoActual.idProducto}/editar`
            : "http://localhost:8080/api/producto/crearProducto";

        const metodo = esEdicion ? "PUT" : "POST";

        try {
            const respuesta = await fetch(url, {
                method: metodo,
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify(productoActual),
            });

            if (respuesta.ok) {
                setMostrarFormulario(false);
                setProductoEditando(null);
                setProductoActual({
                    nombre: "",
                    precio: "",
                    tipo: "Masa",
                    sinTacc: false,
                    visible: true,
                });
                cargarProductos();
            } else {
                alert("Error al guardar el producto.");
            }
        } catch (error) {
            console.error(error);
            alert("Error al conectar con el servidor.");
        }
    };

    const eliminarProducto = async (id) => {
        if (!confirm("¿Seguro que querés eliminar este producto?")) return;
        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/producto/${id}/eliminar`,
                {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                }
            );
            if (respuesta.ok) cargarProductos();
            else alert("No se pudo eliminar el producto.");
        } catch (error) {
            console.error(error);
            alert("Error al conectar con el servidor.");
        }
    };

    const iniciarEdicion = (producto) => {
        setProductoActual(producto);
        setProductoEditando(producto);
        setMostrarFormulario(true);
        window.scrollTo({ top: 0, behavior: "smooth" });
    };

    const iniciarNuevo = () => {
        setProductoActual({
            nombre: "",
            precio: "",
            tipo: "Masa",
            sinTacc: false,
            visible: true,
        });
        setProductoEditando(null);
        setMostrarFormulario(true);
        window.scrollTo({ top: 0, behavior: "smooth" });
    };

    const cancelarFormulario = () => {
        setMostrarFormulario(false);
        setProductoEditando(null);
    };

    return (
        <div className="min-h-screen w-full bg-gray-50 flex flex-col items-center py-12 px-4 md:px-8">
            <h1 className="text-3xl font-extrabold text-gray-800 mb-10 text-center">
                Panel de Productos
            </h1>

            <div className="w-full max-w-5xl bg-white rounded-2xl shadow-lg p-8 border border-gray-100">
                {/* Botones superiores */}
                <div className="flex flex-col md:flex-row gap-4 items-center justify-between mb-8">
                    <button
                        onClick={mostrarFormulario ? cancelarFormulario : iniciarNuevo}
                        className="bg-orange-500 hover:bg-orange-600 text-white font-bold py-3 px-6 rounded-full shadow-md transition-transform transform hover:scale-105 w-full md:w-auto"
                    >
                        {mostrarFormulario
                            ? "Cancelar"
                            : "+ Agregar producto"}
                    </button>

                    <select
                        className="bg-white border border-gray-300 rounded-xl py-2 px-3 text-gray-700 shadow-sm focus:ring-2 focus:ring-orange-400 w-full md:w-auto"
                        value={tipoFiltro}
                        onChange={(e) => setTipoFiltro(e.target.value)}
                    >
                        <option value="Todos">Todos</option>
                        <option value="Masa">Masa para Pizza</option>
                        <option value="Tamanio">Tamaño de Pizza</option>
                        <option value="Salsa">Salsa para Pizza</option>
                        <option value="Topping">Topping para Pizza</option>
                        <option value="Pan">Pan para Hamburguesa</option>
                        <option value="Hamburguesa">Carne para Hamburguesa</option>
                        <option value="Salsa_Hamburguesa">Salsa para Hamburguesa</option>
                        <option value="Ingrediente">Ingrediente para Hamburguesa</option>
                        <option value="Bebida">Bebida</option>
                    </select>
                </div>

                {/* Formulario */}
                {mostrarFormulario && (
                    <FormularioProducto
                        producto={productoActual}
                        setProducto={setProductoActual}
                        onGuardar={guardarProducto}
                        onCancelar={cancelarFormulario}
                        esEdicion={!!productoEditando}
                    />
                )}

                {/* Listado */}
                <div className="mt-10 grid grid-cols-1 md:grid-cols-2 xl:grid-cols-2 gap-6">
                    {productosFiltrados.length === 0 ? (
                        <p className="text-gray-500 italic text-center col-span-full">
                            No hay productos disponibles.
                        </p>
                    ) : (
                        productosFiltrados.map((producto) => (
                            <TarjetaProducto
                                key={producto.idProducto}
                                producto={producto}
                                onEditar={() => iniciarEdicion(producto)}
                                onEliminar={() => eliminarProducto(producto.idProducto)}
                            />
                        ))
                    )}
                </div>
            </div>
        </div>
    );
}

function FormularioProducto({ producto, setProducto, onGuardar, onCancelar, esEdicion }) {
    return (
        <div className="bg-gray-50 rounded-2xl p-6 shadow-inner mb-8 border border-gray-200">
            <h2 className="text-xl font-semibold mb-4 text-center text-gray-700">
                {esEdicion ? "Editar producto" : "Nuevo producto"}
            </h2>

            {producto.tipo != "Tamanio" ? (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <input
                    placeholder="Nombre"
                    className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                    value={producto.nombre}
                    onChange={(e) => setProducto({ ...producto, nombre: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="Precio"
                    className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                    value={producto.precio}
                    onChange={(e) =>
                        setProducto({
                            ...producto,
                            precio: e.target.value === "" ? "" : parseFloat(e.target.value),
                        })
                    }
                />
                <select
                    className="border border-gray-300 rounded-xl p-2 focus:ring-2 focus:ring-orange-400 outline-none"
                    value={producto.tipo}
                    onChange={(e) => setProducto({ ...producto, tipo: e.target.value })}
                >
                    <option value="Masa">Masa para Pizza</option>
                    <option value="Salsa">Salsa para Pizza</option>
                    <option value="Topping">Topping para Pizza</option>
                    <option value="Pan">Pan para Hamburguesa</option>
                    <option value="Hamburguesa">Carne para Hamburguesa</option>
                    <option value="Salsa_Hamburguesa">Salsa para Hamburguesa</option>
                    <option value="Ingrediente">Ingrediente para Hamburguesa</option>
                    <option value="Bebida">Bebida</option>
                </select>

                <div className="flex justify-around md:justify-start gap-6 col-span-full mt-2 flex-wrap">
                    <label className="flex items-center gap-2">
                        <input
                            type="checkbox"
                            checked={producto.sinTacc}
                            onChange={(e) => setProducto({ ...producto, sinTacc: e.target.checked })}
                        />
                        <span>Sin TACC</span>
                    </label>
                    <label className="flex items-center gap-2">
                        <input
                            type="checkbox"
                            checked={producto.visible}
                            onChange={(e) => setProducto({ ...producto, visible: e.target.checked })}
                        />
                        <span>Visible</span>
                    </label>
                </div>
            </div>
            ):
            (
                <>
                <input
                    type="number"
                    placeholder="Precio"
                    className="border border-gray-300 rounded-xl p-2 w-full focus:ring-2 focus:ring-orange-400 outline-none"
                    value={producto.precio}
                    onChange={(e) =>
                        setProducto({
                            ...producto,
                            precio: e.target.value === "" ? "" : parseFloat(e.target.value),
                        })
                        
                    }
                />
                <label className="flex items-center mt-2">
                        <input
                            type="checkbox"
                            checked={producto.visible}
                            onChange={(e) => setProducto({ ...producto, visible: e.target.checked })}
                        />
                        <span>Visible</span>
                </label>
                </>
            )}

            <div className="flex flex-col md:flex-row justify-end gap-4 mt-6">
                <button
                    onClick={onCancelar}
                    className="bg-gray-300 text-gray-800 rounded-full py-2 px-5 font-semibold hover:bg-gray-400 transition w-full md:w-auto"
                >
                    Cancelar
                </button>
                <button
                    onClick={onGuardar}
                    className="bg-orange-500 text-white rounded-full py-2 px-5 font-semibold hover:bg-orange-600 transition w-full md:w-auto"
                >
                    Guardar
                </button>
            </div>
        </div>
    );
}

function TarjetaProducto({ producto, onEditar, onEliminar }) {
    const oculto = producto.visible === false;

    return (
        <div
            className={`flex flex-col sm:flex-row justify-between items-start sm:items-center rounded-2xl shadow-md p-5 border transition-all hover:shadow-lg ${
                oculto ? "bg-red-100 border-red-400" : "bg-green-100 border-green-400"
            }`}
        >
            <div className="mb-4 sm:mb-0">
                <h2
                    className={`text-xl font-bold ${
                        oculto ? "text-red-700" : "text-green-700"
                    }`}
                >
                    {producto.nombre}{" "}
                    {oculto && (
                        <span className="text-sm text-red-600 font-semibold">(OCULTO)</span>
                    )}
                </h2>
                <p className="text-gray-700">
                    Precio:{" "}
                    <span className="font-semibold text-black">${producto.precio}</span>
                </p>
                <p className="text-gray-600 text-sm">Tipo: {producto.tipo}</p>
            </div>

            <div className="flex flex-wrap sm:flex-nowrap gap-3 w-full sm:w-auto">
                <button
                    className="bg-gray-400 text-white rounded-xl px-4 py-2 font-semibold hover:bg-gray-500 transition w-full sm:w-auto"
                    onClick={onEditar}
                >
                    Editar
                </button>
                {producto.tipo != "Tamanio" && (
                <button
                    className="bg-red-500 text-white rounded-xl px-4 py-2 font-semibold hover:bg-red-600 transition w-full sm:w-auto"
                    onClick={onEliminar}
                >
                    Eliminar
                </button>
                )}
            </div>
        </div>
    );
}
