import React, { useEffect } from "react";
import { ToastContainer, toast, Slide } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { connectWebSocket, disconnectWebSocket } from "./WebSocketService";

const Notificaciones = () => {
  useEffect(() => {
    const handlePedidoUpdate = (pedido) => {
      toast.info(`El pedido #${pedido.id} cambió su estado a "${pedido.estado}"`, {
        position: window.innerWidth < 768 ? "top-center" : "top-right",
        autoClose: 5000,
        theme: "colored",
        transition: Slide
      });
    };

    const handlePagoConfirmado = (pago) => {
      toast.success(
        `💳 Pago confirmado para el pedido #${pago.idPedido} — ${pago.medioDePago}\nMonto: $${pago.monto}`,
        {
          position: window.innerWidth < 768 ? "top-center" : "top-right",
          autoClose: 7000,
          theme: "colored",
          transition: Slide
        }
      );
    };

    connectWebSocket(handlePedidoUpdate, handlePagoConfirmado);

    return () => disconnectWebSocket();
  }, []);

  return <ToastContainer />;
};

export default Notificaciones;
