import { createContext, useContext, useState } from "react";

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [items, setItems] = useState([]);

  const addItem = (item) => {
    setItems((prev) => [...prev, item]);
  };

  const removeItem = (id) => {
    setItems((prev) => {
      const updated = prev.filter(
          (item) => item.id !== id && item.idProducto !== id
      );
      const quedanCreaciones = updated.some((i) => i.tipo !== "Bebida");
      return quedanCreaciones
          ? updated
          : updated.filter((i) => i.tipo !== "Bebida");
    });
  };

  const clearCart = () => setItems([]);

  return (
      <CartContext.Provider value={{ items, addItem, removeItem, clearCart }}>
        {children}
      </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
