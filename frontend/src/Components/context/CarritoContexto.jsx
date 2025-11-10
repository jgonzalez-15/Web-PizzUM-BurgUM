import { createContext, useContext, useState, useEffect } from "react";
import { v4 as uuidv4 } from "uuid";

const CarritoContexto = createContext();

export const CarritoProveedor = ({ children }) => {
  const [items, setItems] = useState(() => {
    try {
      const itemsGuardados = localStorage.getItem("carrito");
      return itemsGuardados ? JSON.parse(itemsGuardados) : [];
    } catch (error) {
      console.error("Error al cargar carrito:", error);
      return [];
    }
  });

  useEffect(() => {
    try {
      localStorage.setItem("carrito", JSON.stringify(items));
    } catch (error) {
      console.error("Error al guardar carrito:", error);
    }
  }, [items]);

  const agregarItem = (item) => {
    const itemConIdUnico = { ...item, _uuid: uuidv4() };
    setItems((prev) => [...prev, itemConIdUnico]);
  };

  const eliminarItem = (uuid) => {
    setItems((prev) => {
      const actualizados = prev.filter((item) => item._uuid !== uuid);
      const quedanCreaciones = actualizados.some((i) => i.tipo !== "Bebida");
      return quedanCreaciones
          ? actualizados
          : actualizados.filter((i) => i.tipo !== "Bebida");
    });
  };

  const limpiarCarrito = () => {
    setItems([]);
    localStorage.removeItem("carrito");
  };

  return (
      <CarritoContexto.Provider
          value={{ items, agregarItem, eliminarItem, limpiarCarrito }}
      >
        {children}
      </CarritoContexto.Provider>
  );
};

export const usarCarrito = () => useContext(CarritoContexto);