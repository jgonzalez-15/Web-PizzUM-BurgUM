import { useState, useEffect } from "react";
import SmallButton from "./SmallButton";

export default function ProductModal({ product, onClose }) {
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");

  useEffect(() => {
    if (product) {
      setName(product.name);
      setPrice(product.price);
    } else {
      setName("");
      setPrice("");
    }
  }, [product]);

  return (
    <>
      {/* Fondo semitransparente */}
      <div
        className="fixed inset-0 bg-black/25 flex items-center justify-center z-20"
        onClick={onClose}
      />

      {/* Modal principal */}
      <div className="fixed inset-0 flex items-center justify-center z-30">
        <div className="bg-gray-100 w-80 md:w-96 h-auto p-6 rounded-2xl shadow-2xl flex flex-col items-center">
          <h1 className="font-bold text-2xl mb-4">
            {product ? "Editar producto" : "Nuevo producto"}
          </h1>

          <form
            className="flex flex-col w-full"
            onSubmit={(e) => {
              e.preventDefault();
              onClose();
            }}
          >
            <label className="ml-2">Nombre:</label>
            <input
              type="text"
              className="bg-gray-200 rounded-2xl mt-1 mb-4 p-2"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />

            <label className="ml-2">Precio:</label>
            <input
              type="number"
              className="bg-gray-200 rounded-2xl mt-1 mb-6 p-2"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
              required
            />

            <div className="flex flex-row justify-center gap-4">
              <SmallButton text="Cancelar" isPrimary={false} onClick={onClose} />
              <SmallButton
                text={product ? "Guardar cambios" : "Agregar"}
                isPrimary={true}
                onClick={onClose}
              />
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
