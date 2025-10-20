// CartContext.tsx
import { createContext, useContext, useState } from 'react';

const CartContext = createContext();

export const useCart = () => useContext(CartContext);

export const CartProvider = ({ children }) => {
  const [items, setItems] = useState([]);

  const [cartId, setCartId] = useState(1);

  const addItem = (item) => {setItems((prev) => [...prev, item]), setCartId((cartId) => cartId+1)};

  const removeItem = (id) =>
    setItems((prev) => prev.filter((item) => item.id !== id));

  const clearCart = () => setItems([]);

  return (
    <CartContext.Provider value={{ items, addItem, removeItem, clearCart, cartId }}>
      {children}
    </CartContext.Provider>
  );
};