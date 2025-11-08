import { useEffect, useState } from "react";
import AdminHeader from "../Components/AdminHeader";
import Footer from "../Components/Footer";
import AdminProductCard from "../Components/AdminProductCard";

function Backoffice() {
  const [activeTab, setActiveTab] = useState("productos");
  const [showModal, setShowModal] = useState(false);
  const [newProduct, setNewProduct] = useState({ nombre: "", precio: "", tipo: "Masa", sinTacc: false, visible: true });
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [adminList, setAdminList] = useState([]);
  const [filterType, setFilterType] = useState("Todos");
  const [newAdmin, setNewAdmin] = useState({ user: "", password: "" });
  const [showAdminModal, setShowAdminModal] = useState(false);
  const [orders, setOrders] = useState([]);
  const [editModal, setEditModal] = useState(false);
  const [editProduct, setEditProduct] = useState(null);

  // 👇 NUEVOS ESTADOS PARA VER DETALLES
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [showOrderModal, setShowOrderModal] = useState(false);

  const handleAddProduct = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/producto/crearProducto`, {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
        body: JSON.stringify(newProduct),
        credentials: "include",
      });
      if (response.ok) {
        getProducts();
        setShowModal(false);
      } else {
        const errorData = await response.json();
        alert(errorData.message || "Ocurrió un error al crear el producto");
      }
    } catch (error) {
      console.error("Error al crear producto:", error);
      alert("Error de conexión con el servidor");
    }
  };

  const openEditModal = (p) => {
    setEditProduct(p);
    setEditModal(true);
  };

  const handleEditProduct = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/producto/${editProduct.idProducto}/editar`, {
        method: "PUT",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
        body: JSON.stringify(editProduct),
        credentials: "include",
      });
      if (response.ok) {
        getProducts();
        setEditModal(false);
      } else {
        const error = await response.json();
        alert(error.message || "Error al editar producto");
      }
    } catch (error) {
      console.error("Error al editar producto:", error);
    }
  };

  const removeProduct = async (e, p) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/producto/${p}/eliminar`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
        credentials: "include",
      });
      if (response.ok) {
        getProducts();
      } else {
        alert("No se pudo eliminar el producto");
      }
    } catch (error) {
      console.error("Error al eliminar producto:", error);
    }
  };

  const handleAddAdmin = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/administrador/agregarAdmin", {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
        body: JSON.stringify(newAdmin),
        credentials: "include",
      });
      if (response.ok) {
        setShowAdminModal(false);
        getAdmins();
      } else {
        alert("Los datos son incorrectos o el administrador ya existe");
      }
    } catch (error) {
      console.error("Error al crear admin:", error);
    }
  };

  const getProducts = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/producto/listarAdmin", {
        method: "GET",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
      });
      if (response.ok) {
        const data = await response.json();
        setProducts(data);
      } else {
        alert("Ocurrió un error al obtener los productos");
      }
    } catch (error) {
      console.error("Error al obtener productos:", error);
    }
  };

  useEffect(() => {
    getProducts();
  }, []);

  function filter() {
    if (filterType !== "Todos") {
      setFilteredProducts(products.filter((p) => p.tipo === filterType));
    } else {
      setFilteredProducts(products);
    }
  }

  useEffect(() => {
    filter();
  }, [filterType, products]);

  const getOrders = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/pedido/enCurso", {
        method: "GET",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
        credentials: "include",
      });
      if (response.ok) {
        const data = await response.json();
        setOrders(data);
      } else {
        alert("Ocurrió un error al obtener los pedidos");
      }
    } catch (error) {
      console.error("Error al obtener pedidos:", error);
    }
  };

  useEffect(() => {
    getOrders();
  }, []);

  const getAdmins = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/administrador/listar", {
        method: "GET",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
      });
      if (response.ok) {
        const data = await response.json();
        setAdminList(data);
      } else {
        alert("Ocurrió un error al obtener los administradores");
      }
    } catch (error) {
      console.error("Error al obtener administradores:", error);
    }
  };

  useEffect(() => {
    getAdmins();
  }, []);

  const handleAdvanceState = async (e, id) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/pedido/${id}/cambiarEstado`, {
        method: "PUT",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem("token")}` },
        credentials: "include",
      });
      if (response.ok) {
        getOrders();
      } else {
        alert("Ocurrió un error al actualizar el estado del pedido");
      }
    } catch (error) {
      console.error("Error al actualizar pedido:", error);
    }
  };

  const handleViewDetails = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/pedido/ver/${id}`, {
        method: "GET",
        headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` },
        credentials: "include",
      });

      if (!response.ok) {
        alert("Error al obtener los detalles del pedido");
        return;
      }

      const data = await response.json();

      const items = [];

      const addItem = (nombre, tipo, precio, cantidad, ingredientes) => {
        items.push({ nombre, tipo, precio, cantidad, ingredientes });
      };

      if (Array.isArray(data.pizzas)) {
        data.pizzas.forEach((p) => {
          addItem(
              p.nombre || "Pizza",
              "Pizza",
              p.precio || 0,
              p.cantidad || 1,
              p.ingredientes || []
          );
        });
      }

      if (Array.isArray(data.hamburguesas)) {
        data.hamburguesas.forEach((h) => {
          addItem(
              h.nombre || "Hamburguesa",
              "Hamburguesa",
              h.precio || 0,
              h.cantidad || 1,
              h.ingredientes || []
          );
        });
      }

      if (Array.isArray(data.bebidas)) {
        data.bebidas.forEach((b) => {
          addItem(b.nombre || "Bebida", "Bebida", b.precio || 0, b.cantidad || 1);
        });
      }

      if (Array.isArray(data.productos)) {
        data.productos.forEach((p) => {
          addItem(p.nombre || "Producto", p.tipo || "Producto", p.precio || 0, p.cantidad || 1);
        });
      }

      setSelectedOrder({
        ...data,
        _items: items,
      });
      setShowOrderModal(true);
    } catch (error) {
      console.error("Error al obtener pedido:", error);
      alert("Error de conexión con el servidor");
    }
  };


  return (
      <>
        <AdminHeader />
        <div className="min-h-screen flex flex-col justify-between overflow-x-hidden">
          <div className="mt-20 mb-8 flex flex-col items-center px-4 sm:px-6 md:px-8">
            <div className="flex gap-3 mb-8 flex-wrap justify-center">
              {["productos", "pedidos", "administradores"].map((tab) => (
                  <button
                      key={tab}
                      className={`px-4 py-2 rounded-full border-2 font-bold transition-all ${activeTab === tab
                          ? "bg-orange-400 text-white border-orange-400"
                          : "border-gray-300 bg-white hover:bg-gray-100"
                      }`}
                      onClick={() => setActiveTab(tab)}
                  >
                    {tab.charAt(0).toUpperCase() + tab.slice(1)}
                  </button>
              ))}
            </div>

            {/* TAB DE PRODUCTOS */}
            {activeTab === "productos" && (
                <div className="flex flex-col gap-6 w-full max-w-3xl">
                  <button
                      onClick={() => setShowModal(true)}
                      className="bg-orange-400 text-white font-bold py-2 px-4 rounded-2xl hover:scale-105 transition-transform self-center"
                  >
                    Agregar producto
                  </button>
                  <select className="m-2" value={filterType} onChange={(e) => setFilterType(e.target.value)}>
                    <option value="Todos">Todos</option>
                    <option value="Masa">Masa para Pizza</option>
                    <option value="Salsa">Salsa para Pizza</option>
                    <option value="Topping">Topping para Pizza</option>
                    <option value="Pan">Pan para Hamburguesa</option>
                    <option value="Hamburguesa">Carne para Hamburguesa</option>
                    <option value="Salsa_Hamburguesa">Salsa para Hamburguesa</option>
                    <option value="Ingrediente">Ingrediente para Hamburguesa</option>
                    <option value="Bebida">Bebida</option>
                  </select>
                  {filteredProducts.map((p) => (
                      <AdminProductCard key={p.idProducto} product={p} onEdit={() => openEditModal(p)} onRemove={removeProduct} />
                  ))}
                </div>
            )}

            {/* TAB DE PEDIDOS */}
            {activeTab === "pedidos" && (
                <div className="flex flex-col gap-6 w-full max-w-3xl">
                  {orders.map((o) => (
                      <div key={o.id} className="w-full flex justify-between items-center bg-gray-50 rounded-2xl shadow-xl p-4">
                        <div>
                          <h1 className="font-bold text-lg">Pedido #{o.id}</h1>
                          <h2>Fecha: {o.fecha}</h2>
                          <h2 className="text-orange-400">Estado: {o.estado}</h2>
                        </div>
                        <div className="flex gap-2">
                          <button
                              className="bg-gray-200 text-black rounded-xl px-4 py-2 font-bold hover:scale-105 transition-transform"
                              onClick={() => handleViewDetails(o.id)}
                          >
                            Ver detalles
                          </button>
                          <button
                              className="bg-orange-400 text-white rounded-xl px-4 py-2 font-bold hover:scale-105 transition-transform"
                              onClick={(e) => handleAdvanceState(e, o.id)}
                          >
                            Avanzar estado
                          </button>
                        </div>
                      </div>
                  ))}
                </div>
            )}

            {/* TAB DE ADMINISTRADORES */}
            {activeTab === "administradores" && (
                <div className="flex flex-col items-center gap-6">
                  <button
                      onClick={() => setShowAdminModal(true)}
                      className="bg-orange-400 text-white font-bold py-2 px-4 rounded-2xl hover:scale-105 transition-transform"
                  >
                    Crear nuevo administrador
                  </button>
                  <div>
                    {adminList.map((admin) => (
                        <h1 className="font-bold text-center" key={admin.email}>
                          {admin.email}
                        </h1>
                    ))}
                  </div>
                </div>
            )}
          </div>
          <Footer />
        </div>

        {/* MODAL NUEVO PRODUCTO */}
        {showModal && (
            <div
                className="fixed inset-0 bg-black/40 flex justify-center items-center z-50"
                onClick={(e) => {
                  if (e.target === e.currentTarget) setShowModal(false);
                }}
            >
              <div className="bg-gray-100 rounded-2xl shadow-2xl p-6 w-72 md:w-96 flex flex-col items-center">
                <h1 className="font-bold text-xl mb-4 text-center">Nuevo Producto</h1>
                <input
                    type="text"
                    placeholder="Nombre"
                    className="bg-gray-200 rounded-2xl mt-1 mb-3 p-2 w-full"
                    value={newProduct.nombre}
                    onChange={(e) => setNewProduct({ ...newProduct, nombre: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="Precio"
                    className="bg-gray-200 rounded-2xl mt-1 mb-3 p-2 w-full"
                    value={newProduct.precio}
                    onChange={(e) => setNewProduct({ ...newProduct, precio: e.target.value === "" ? "" : parseFloat(e.target.value) })}
                />
                <select
                    className="m-2"
                    value={newProduct.tipo}
                    onChange={(e) => setNewProduct({ ...newProduct, tipo: e.target.value })}
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
                <label className="m-2">
                  <input type="checkbox" onChange={(e) => setNewProduct({ ...newProduct, sinTacc: e.target.checked })} />
                  Sin TACC
                </label>
                <label className="m-2">
                  <input
                      type="checkbox"
                      checked={newProduct.visible}
                      onChange={(e) => setNewProduct({ ...newProduct, visible: e.target.checked })}
                  />
                  Visible
                </label>
                <div className="flex gap-4">
                  <button
                      onClick={() => setShowModal(false)}
                      className="bg-gray-300 rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
                  >
                    Cancelar
                  </button>
                  <button
                      onClick={handleAddProduct}
                      className="bg-orange-400 text-white rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
                  >
                    Agregar
                  </button>
                </div>
              </div>
            </div>
        )}

        {/* MODAL EDITAR PRODUCTO */}
        {editModal && (
            <div
                className="fixed inset-0 bg-black/40 flex justify-center items-center z-50"
                onClick={(e) => {
                  if (e.target === e.currentTarget) setEditModal(false);
                }}
            >
              <div className="bg-gray-100 rounded-2xl shadow-2xl p-6 w-72 md:w-96 flex flex-col items-center">
                <h1 className="font-bold text-xl mb-4 text-center">Editar Producto</h1>
                <input
                    type="text"
                    placeholder="Nombre"
                    className="bg-gray-200 rounded-2xl mt-1 mb-3 p-2 w-full"
                    value={editProduct.nombre}
                    onChange={(e) => setEditProduct({ ...editProduct, nombre: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="Precio"
                    className="bg-gray-200 rounded-2xl mt-1 mb-3 p-2 w-full"
                    value={editProduct.precio}
                    onChange={(e) => setEditProduct({ ...editProduct, precio: parseFloat(e.target.value) })}
                />
                <label className="m-2">
                  <input
                      type="checkbox"
                      checked={editProduct.sinTacc}
                      onChange={(e) => setEditProduct({ ...editProduct, sinTacc: e.target.checked })}
                  />
                  Sin TACC
                </label>
                <label className="m-2">
                  <input
                      type="checkbox"
                      checked={editProduct.visible}
                      onChange={(e) => setEditProduct({ ...editProduct, visible: e.target.checked })}
                  />
                  Visible
                </label>
                <div className="flex gap-4 mt-4">
                  <button
                      onClick={() => setEditModal(false)}
                      className="bg-gray-300 rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
                  >
                    Cancelar
                  </button>
                  <button
                      onClick={handleEditProduct}
                      className="bg-orange-400 text-white rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
                  >
                    Guardar
                  </button>
                </div>
              </div>
            </div>
        )}

        {/* MODAL NUEVO ADMIN */}
        {showAdminModal && (
            <div
                className="fixed inset-0 bg-black/40 flex justify-center items-center z-50"
                onClick={(e) => {
                  if (e.target === e.currentTarget) setShowAdminModal(false);
                }}
            >
              <div className="bg-gray-100 rounded-2xl shadow-2xl p-6 w-72 md:w-96 flex flex-col items-center">
                <h1 className="font-bold text-xl mb-4 text-center">Nuevo Administrador</h1>
                <input
                    type="text"
                    placeholder="Usuario"
                    className="bg-gray-200 rounded-2xl mt-1 mb-3 p-2 w-full"
                    value={newAdmin.email}
                    onChange={(e) => setNewAdmin({ ...newAdmin, email: e.target.value })}
                />
                <input
                    type="password"
                    placeholder="Contraseña"
                    className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2 w-full"
                    value={newAdmin.contrasenia}
                    onChange={(e) => setNewAdmin({ ...newAdmin, contrasenia: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Nombre"
                    className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2 w-full"
                    value={newAdmin.nombre}
                    onChange={(e) => setNewAdmin({ ...newAdmin, nombre: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Apellido"
                    className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2 w-full"
                    value={newAdmin.apellido}
                    onChange={(e) => setNewAdmin({ ...newAdmin, apellido: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Teléfono"
                    className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2 w-full"
                    value={newAdmin.telefono}
                    onChange={(e) => setNewAdmin({ ...newAdmin, telefono: e.target.value })}
                />
                <input
                    type="date"
                    placeholder="Fecha de nacimiento"
                    className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2 w-full"
                    value={newAdmin.fechaNac}
                    onChange={(e) => setNewAdmin({ ...newAdmin, fechaNac: e.target.value })}
                />
                <div className="flex gap-4">
                  <button
                      onClick={() => setShowAdminModal(false)}
                      className="bg-gray-300 rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
                  >
                    Cancelar
                  </button>
                  <button
                      onClick={handleAddAdmin}
                      className="bg-orange-400 text-white rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
                  >
                    Crear
                  </button>
                </div>
              </div>
            </div>
        )}

        {/* MODAL DETALLES DE PEDIDO */}
        {showOrderModal && selectedOrder && (
            <div
                className="fixed inset-0 bg-black/40 flex justify-center items-center z-50"
                onClick={(e) => {
                  if (e.target === e.currentTarget) setShowOrderModal(false);
                }}
            >
              <div className="bg-white rounded-2xl shadow-2xl p-6 w-80 md:w-[600px] max-h-[80vh] overflow-y-auto">
                <h1 className="text-2xl font-extrabold text-gray-800 mb-4 text-center">
                  Detalles del Pedido #{selectedOrder.id}
                </h1>

                <div className="text-gray-700 space-y-2 mb-4">
                  <p><span className="font-semibold">Fecha:</span> {selectedOrder.fecha}</p>
                  <p><span className="font-semibold">Estado:</span> {selectedOrder.estado}</p>
                  <p><span className="font-semibold">Dirección:</span> {selectedOrder.domicilio?.direccion || "No especificada"}</p>
                  <p><span className="font-semibold">Pago:</span> {selectedOrder.estaPago ? "Pagado" : "No pagado"}</p>
                  {selectedOrder.medioDePago?.numeroTarjeta && (
                      <p>
                        <span className="font-semibold">Tarjeta:</span> **** **** **** {String(selectedOrder.medioDePago.numeroTarjeta).slice(-4)}
                      </p>
                  )}
                  <p><span className="font-semibold">Monto total:</span> ${Number(selectedOrder.precio ?? 0).toFixed(2)}</p>
                </div>

                {Array.isArray(selectedOrder._items) && selectedOrder._items.length > 0 ? (
                    <div className="bg-gray-50 rounded-xl p-4 shadow-inner">
                      <h2 className="text-lg font-semibold text-gray-800 mb-3">Productos:</h2>
                      <ul className="space-y-3">
                        {selectedOrder._items.map((item, i) => (
                            <li key={i} className="border-b pb-2">
                              <div className="flex justify-between">
                  <span className="text-gray-800 font-medium">
                    {item.tipo}: {item.nombre} x{item.cantidad}
                  </span>
                                <span className="text-gray-900 font-bold">
                    ${Number(item.precio).toFixed(2)}
                  </span>
                              </div>
                              {item.ingredientes && item.ingredientes.length > 0 && (
                                  <ul className="ml-4 mt-1 text-sm text-gray-600 list-disc">
                                    {item.ingredientes.map((ing, j) => (
                                        <li key={j}>
                                          {ing.nombre || ing.producto?.nombre || "Ingrediente"}{" "}
                                          {ing.cantidad ? `x${ing.cantidad}` : ""}
                                        </li>
                                    ))}
                                  </ul>
                              )}
                            </li>
                        ))}
                      </ul>
                    </div>
                ) : (
                    <p className="text-gray-500 italic text-center mt-4">
                      No hay productos registrados en este pedido.
                    </p>
                )}

                <div className="flex justify-center mt-6">
                  <button
                      onClick={() => setShowOrderModal(false)}
                      className="bg-orange-400 text-white rounded-2xl py-2 px-6 font-bold hover:scale-105 transition-transform"
                  >
                    Cerrar
                  </button>
                </div>
              </div>
            </div>
        )}

      </>
  );
}

export default Backoffice;
