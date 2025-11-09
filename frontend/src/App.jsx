import "./App.css";
import { useContext, useEffect, useState } from "react";
import { Routes, Route, Navigate, useNavigate } from "react-router-dom";

import { SessionContext } from "./Components/context/SessionContext";
import { CarritoProveedor } from "./Components/context/CarritoContexto.jsx";

import PaginaPrincipal from "./Pages/PaginaPrincipal.jsx";
import DiseniarCreacion from "./Pages/DiseniarCreacion.jsx";
import Pedidos from "./Pages/Pedidos.jsx";
import NuevoPedido from "./Pages/NuevoPedido.jsx";
import InicioSesion from "./Pages/InicioSesion.jsx";
import Backoffice from "./Pages/Backoffice";
import Favoritos from "./Pages/Favoritos";
import VerCreacion from "./Pages/VerCreacion.jsx";
import Registrar from "./Pages/Registrar.jsx";
import Perfil from "./Pages/Perfil";
import CheckoutPage from "./Pages/PaginaPago.jsx";

function App() {
  const { sessionType, setSessionType, setSessionInfo } = useContext(SessionContext);
  const navigate = useNavigate();
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    const refrescarToken = async () => {
      try {
        const respuesta = await fetch("http://localhost:8080/auth/resfrescar", {
          method: "GET",
          credentials: "include",
        });

        if (respuesta.ok) {
          const datos = await respuesta.json();
          setSessionType(datos.rol);
          setSessionInfo(datos.info);
          localStorage.setItem("token", datos.jwt);
        } else {
          setSessionType("INVITADO");
          setSessionInfo(null);
          localStorage.removeItem("token");
        }
      } catch (error) {
        setSessionType("INVITADO");
        setSessionInfo(null);
        localStorage.removeItem("token");
      } finally {
        setCargando(false);
      }
    };

    refrescarToken();
  }, []);

  if (cargando) return null;

  return (
      <CarritoProveedor>
        <Routes>
          {/* Rutas comunes */}
          <Route path="/" element={<Navigate to="/homepage" replace />} />
          <Route path="/homepage" element={<PaginaPrincipal />} />
          <Route path="/login" element={<InicioSesion />} />
          <Route path="/register" element={<Registrar />} />

          {/* Invitado */}
          {sessionType === "INVITADO" && (
              <>
                <Route path="/design/pizza" element={<Navigate to="/login" replace />} />
                <Route path="/design/burger" element={<Navigate to="/login" replace />} />
                <Route path="/favoritos" element={<Navigate to="/login" replace />} />
                <Route path="/order" element={<Navigate to="/login" replace />} />
                <Route path="/viewOrders" element={<Navigate to="/login" replace />} />
                <Route path="/checkout" element={<Navigate to="/login" replace />} />
              </>
          )}

          {/* Cliente */}
          {sessionType === "CLIENTE" && (
              <>
                <Route path="/design/pizza" element={<DiseniarCreacion tipo="Pizza" />} />
                <Route path="/design/burger" element={<DiseniarCreacion tipo="Hamburguesa" />} />
                <Route path="/viewOrders" element={<Pedidos />} />
                <Route path="/order" element={<NuevoPedido />} />
                <Route path="/checkout" element={<CheckoutPage />} />
                <Route path="/favoritos" element={<Favoritos />} />
                <Route path="/verCreacion" element={<VerCreacion />} />
                <Route path="/perfil" element={<Perfil />} />
              </>
          )}

          {/* Admin */}
          {sessionType === "ADMIN" && (
              <>
                <Route path="/admin" element={<Backoffice />} />
              </>
          )}

          {/* Fallback */}
          <Route path="*" element={<Navigate to="/homepage" replace />} />
        </Routes>
      </CarritoProveedor>
  );
}

export default App;
