import { useState } from "react";
import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";

function Options() {
  if (window.pageYOffset > 0) window.scrollTo(0, 0);

  const [user] = useState({
    name: "Juan Pérez",
    email: "juan@example.com",
  });

  const [passwords, setPasswords] = useState({ old: "", new: "", confirm: "" });

  const [paymentMethods, setPaymentMethods] = useState([
    { id: 1, type: "Tarjeta Visa terminada en 1234" },
  ]);

  const [addresses, setAddresses] = useState([
    { id: 1, address: "Av. Siempre Viva 742" },
  ]);

  const [showModal, setShowModal] = useState(false);
  const [modalType, setModalType] = useState(null);
  const [modalInput, setModalInput] = useState("");

  // ========= HANDLERS =========

  function changePassword(e) {
  }

  function openModal(type) {
    setModalType(type);
    setModalInput("");
    setShowModal(true);
  }

  function handleSaveModal() {
    if (!modalInput.trim()) return;

    if (modalType === "pago") {
        
    } else if (modalType === "domicilio") {

    }

    setShowModal(false);
    setModalInput("");
  }

  function handleRemove(type, id) {
  }

  // ========= RENDER =========

  return (
    <>
      <MainHeader className="z-10" />
      <div className="flex flex-col min-h-screen justify-between overflow-x-hidden">
        <div className="mt-20 mb-8 px-4 sm:px-6 md:px-8 w-full flex flex-col items-center">
          <h1 className="font-bold text-2xl mb-8 text-center">Opciones de Cuenta</h1>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8 w-full max-w-5xl">

            {/* Columna izquierda */}
            <div className="flex flex-col items-center gap-8">

              {/* Información del usuario */}
              <div className="bg-gray-100 w-72 md:w-96 p-6 rounded-2xl shadow-2xl text-center">
                <h1 className="font-bold text-xl mb-4">Información del Usuario</h1>
                <p><strong>Nombre:</strong> {user.name}</p>
                <p><strong>Correo:</strong> {user.email}</p>
              </div>

              {/* Cambiar contraseña */}
              <div className="bg-gray-100 w-72 md:w-96 p-6 rounded-2xl shadow-2xl">
                <h1 className="font-bold text-xl mb-4 text-center">Cambiar Contraseña</h1>
                <form onSubmit={changePassword} className="flex flex-col">
                  <label className="ml-2">Actual:</label>
                  <input
                    type="password"
                    className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2"
                    value={passwords.old}
                    onChange={(e) => setPasswords({ ...passwords, old: e.target.value })}
                  />
                  <label className="ml-2">Nueva:</label>
                  <input
                    type="password"
                    className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2"
                    value={passwords.new}
                    onChange={(e) => setPasswords({ ...passwords, new: e.target.value })}
                  />
                  <label className="ml-2">Confirmar:</label>
                  <input
                    type="password"
                    className="bg-gray-200 rounded-2xl mt-1 mb-6 p-2"
                    value={passwords.confirm}
                    onChange={(e) => setPasswords({ ...passwords, confirm: e.target.value })}
                  />
                  <button
                    type="submit"
                    className="bg-orange-400 text-white rounded-2xl py-2 font-bold hover:scale-105 transition-transform"
                  >
                    Guardar
                  </button>
                </form>
              </div>
            </div>

            {/* Columna derecha */}
            <div className="flex flex-col items-center gap-8">

              {/* Medios de pago */}
              <div className="bg-gray-100 w-72 md:w-96 p-6 rounded-2xl shadow-2xl flex flex-col gap-2">
                <h1 className="font-bold text-xl text-center mb-2">Medios de Pago</h1>
                {paymentMethods.map((m) => (
                  <div key={m.id} className="flex justify-between bg-gray-200 p-2 rounded-xl">
                    <p>{m.type}</p>
                    <button
                      onClick={() => handleRemove("pago", m.id)}
                      className="text-red-600 font-bold"
                    >
                      X
                    </button>
                  </div>
                ))}
                <button
                  onClick={() => openModal("pago")}
                  className="bg-orange-400 text-white rounded-2xl py-2 font-bold hover:scale-105 transition-transform mt-2"
                >
                  Agregar
                </button>
              </div>

              {/* Domicilios */}
              <div className="bg-gray-100 w-72 md:w-96 p-6 rounded-2xl shadow-2xl flex flex-col gap-2">
                <h1 className="font-bold text-xl text-center mb-2">Domicilios</h1>
                {addresses.map((a) => (
                  <div key={a.id} className="flex justify-between bg-gray-200 p-2 rounded-xl">
                    <p>{a.address}</p>
                    <button
                      onClick={() => handleRemove("domicilio", a.id)}
                      className="text-red-600 font-bold"
                    >
                      X
                    </button>
                  </div>
                ))}
                <button
                  onClick={() => openModal("domicilio")}
                  className="bg-orange-400 text-white rounded-2xl py-2 font-bold hover:scale-105 transition-transform mt-2"
                >
                  Agregar
                </button>
              </div>
            </div>
          </div>
        </div>

        <Footer />
      </div>

      {/* ===== MODAL ===== */}
      {showModal && (
        <div
          className="fixed inset-0 bg-black/40 flex justify-center items-center z-50"
          onClick={(e) => {
            if (e.target === e.currentTarget) setShowModal(false);
          }}
        >
          <div className="bg-gray-100 rounded-2xl shadow-2xl p-6 w-72 md:w-96 flex flex-col items-center">
            <h1 className="font-bold text-xl mb-4 text-center">
              Agregar {modalType === "pago" ? "medio de pago" : "domicilio"}
            </h1>
            <input
              type="text"
              placeholder={
                modalType === "pago" ? "Ej: Tarjeta Mastercard..." : "Ej: Calle Falsa 123..."
              }
              className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2 w-full"
              value={modalInput}
              onChange={(e) => setModalInput(e.target.value)}
            />
            <div className="flex gap-4">
              <button
                onClick={() => setShowModal(false)}
                className="bg-gray-300 rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
              >
                Cancelar
              </button>
              <button
                onClick={handleSaveModal}
                className="bg-orange-400 text-white rounded-2xl py-2 px-4 font-bold hover:scale-105 transition-transform"
              >
                Guardar
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default Options;
