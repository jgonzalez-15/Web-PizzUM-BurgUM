import { createContext, useContext, useState } from "react";

const CarritoContexto = createContext();

export const CarritoProveedor = ({ children }) => {
  const [items, setItems] = useState([]);

  const agregarItem = (item) => setItems((prev) => [...prev, item]);

  const eliminarItem = (id) => {
    setItems((prev) => {
      const actualizados = prev.filter(
          (item) => item.id !== id && item.idProducto !== id
      );
      const quedanCreaciones = actualizados.some((i) => i.tipo !== "Bebida");
      return quedanCreaciones
          ? actualizados
          : actualizados.filter((i) => i.tipo !== "Bebida");
    });
  };

  const limpiarCarrito = () => setItems([]);

  return (
      <CarritoContexto.Provider
          value={{ items, agregarItem, eliminarItem, limpiarCarrito }}
      >
        {children}
      </CarritoContexto.Provider>
  );
};

export const usarCarrito = () => useContext(CarritoContexto);
