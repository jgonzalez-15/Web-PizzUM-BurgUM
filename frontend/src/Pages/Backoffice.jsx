import { useEffect, useState } from "react";

import AdminHeader from "../Components/AdminHeader";
import Footer from "../Components/Footer";
import AdminProductCard from "../Components/AdminProductCard";

function Backoffice() {
  const [activeTab, setActiveTab] = useState("productos");
  const [showModal, setShowModal] = useState(false);
  const [newProduct, setNewProduct] = useState({ nombre: "", precio: "", tipo: "Masa", sinTacc: false });
  const [products, setProducts] = useState([]);
  const [adminList, setAdminList] = useState("");

  const [newAdmin, setNewAdmin] = useState({ user: "", password: "" });
  const [showAdminModal, setShowAdminModal] = useState(false);

  const [orders, setOrders] = useState([
    { id: 1, date: "2025-10-25", status: 1 },
    { id: 2, date: "2025-10-24", status: 3 },
  ]);

  const handleAddProduct = async (e) => {
  e.preventDefault();
  try {
    const response = await fetch(`http://localhost:8080/api/producto/crearProducto`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newProduct),
      credentials: "include"
      });

      if (response.ok) {
        getProducts()
        setShowModal(false)
      } else {
        alert("No se pudo crear el producto");
      }
    } catch (error) {
      console.error("Error al crear producto:", error);
    }
  };

  function editProduct(){

  }

  const removeProduct = async (e, p) => {
  e.preventDefault();
  try {
    const response = await fetch(`http://localhost:8080/api/producto/${p}/eliminar`, {
      method: "DELETE",
      credentials: "include"
    });

    if (response.ok) {
      getProducts()
    } else {
      alert("No se pudo eliminar el producto");
    }
  } catch (error) {
    console.error("Error al eliminar producto:", error);
  }
};

  const handleAddAdmin = async (e) => {
    e.preventDefault();
    try{
      {/* Intentar crear el administrador */}
      const response = await fetch("http://localhost:8080/api/administrador/agregarAdmin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newAdmin),
        credentials: "include",
      });

      if (response.ok) {
        {/* Si crea cerrar el modal */}
        setShowAdminModal(false)
      } else {
        {/* Si no mostrar una alerta */}
        alert("Los datos son incorrectos o el administrador ya existe")
      }
    } catch (error){
      console.error("Error al crear admin:", error);
    }
  }

  const getProducts = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/producto/listar", {
        method: "GET",
        headers: { "Content-Type": "application/json" }
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

  return (
    <>
      <AdminHeader />
      <div className="min-h-screen flex flex-col justify-between overflow-x-hidden">
        <div className="mt-20 mb-8 flex flex-col items-center px-4 sm:px-6 md:px-8">

          {/* Selector */}
          <div className="flex gap-3 mb-8 flex-wrap justify-center">
            {["productos", "pedidos", "administradores"].map((tab) => (
              <button
                key={tab}
                className={`px-4 py-2 rounded-full border-2 font-bold transition-all ${
                  activeTab === tab
                    ? "bg-orange-400 text-white border-orange-400"
                    : "border-gray-300 bg-white hover:bg-gray-100"
                }`}
                onClick={() => setActiveTab(tab)}
              >
                {tab.charAt(0).toUpperCase() + tab.slice(1)}
              </button>
            ))}
          </div>

          {/* TAB: Productos */}
          {activeTab === "productos" && (
            <div className="flex flex-col gap-6 w-full max-w-3xl">
              {products.map((p) => (
                <AdminProductCard key={p.id} product={p} onEdit={editProduct} onRemove={removeProduct} />
              ))}
              <button
                onClick={() => setShowModal(true)}
                className="bg-orange-400 text-white font-bold py-2 px-4 rounded-2xl hover:scale-105 transition-transform self-center"
              >
                Agregar producto
              </button>
            </div>
          )}

          {/* TAB: Pedidos */}
          {activeTab === "pedidos" && (
            <div className="flex flex-col gap-6 w-full max-w-3xl">
              {orders.map((o) => (
                <div
                  key={o.id}
                  className="w-full flex justify-between items-center bg-gray-50 rounded-2xl shadow-xl p-4"
                >
                  <div>
                    <h1 className="font-bold text-lg">Pedido #{o.id}</h1>
                    <h2>Fecha: {o.fecha}</h2>
                  </div>
                  <button className="bg-orange-400 text-white rounded-xl px-4 py-2 font-bold hover:scale-105 transition-transform">
                    Avanzar estado
                  </button>
                </div>
              ))}
            </div>
          )}

          {/* TAB: Administradores */}
          {activeTab === "administradores" && (
            <div className="flex flex-col items-center gap-6">
              <button
                onClick={() => setShowAdminModal(true)}
                className="bg-orange-400 text-white font-bold py-2 px-4 rounded-2xl hover:scale-105 transition-transform"
              >
                Crear nuevo administrador
              </button>
            </div>
          )}
        </div>

        <Footer />
      </div>

      {/* MODAL: Producto */}
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
              value={newProduct.name}
              onChange={(e) => setNewProduct({ ...newProduct, nombre: e.target.value })}
            />
            <input
              type="number"
              placeholder="Precio"
              className="bg-gray-200 rounded-2xl mt-1 mb-3 p-2 w-full"
              value={newProduct.price}
              onChange={(e) => setNewProduct({ ...newProduct, precio: e.target.value })}
            />
            <select className="m-2" value={newProduct.tipo} onChange={(e) => setNewProduct({ ...newProduct, tipo: e.target.value })}>
              <option value="Masa">Masa para Pizza</option>
              <option value="Salsa">Salsa para Pizza</option>
              <option value="Topping">Topping para Pizza</option>
              <option value="Pan">Pan para Hamburguesa</option>
              <option value="Hamburguesa">Carne para Hamburguesa</option>
              <option value="Salsa_Hamburguesa">Salsa para Hamburguesa</option>
              <option value="Ingrediente">Ingrediente para Hamburguesa</option>
            </select>
            <label className="m-2">
            <input
                type="checkbox"
                onChange={(e) =>
                  setNewProduct({ ...newProduct, sinTacc: e.target.checked })
                }
              />
              Sin TACC
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

      {/* MODAL: Nuevo Admin */}
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
              placeholder="Teléfono"
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
    </>
  );
}

export default Backoffice;
