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
import Favoritos from "./Pages/Favoritos";
import VerCreacion from "./Pages/VerCreacion.jsx";
import Registrar from "./Pages/Registrar.jsx";
import Perfil from "./Pages/Perfil";
import CheckoutPage from "./Pages/PaginaPago.jsx";


import PanelAdministrador from "./Pages/PanelAdministrador.jsx";
import ProductosAdmin from "./Pages/ProductosAdmin.jsx";
import PedidosAdmin from "./Pages/PedidosAdmin.jsx";
import AgregarAdministradores from "./pages/AgregarAdministradores.jsx";

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
          <Route path="/" element={<Navigate to="/paginaPrincipal" replace />} />
          <Route path="/paginaPrincipal" element={<PaginaPrincipal />} />
          <Route path="/iniciarSesion" element={<InicioSesion />} />
          <Route path="/registrarse" element={<Registrar />} />

          {/* Invitado */}
          {sessionType === "INVITADO" && (
              <>
                <Route path="/diseniar/pizza" element={<Navigate to="/iniciarSesion" replace />} />
                <Route path="/diseniar/hamburguesa" element={<Navigate to="/iniciarSesion" replace />} />
                <Route path="/favoritos" element={<Navigate to="/iniciarSesion" replace />} />
                <Route path="/hacerUnPedido" element={<Navigate to="/iniciarSesion" replace />} />
                <Route path="/verPedidos" element={<Navigate to="/iniciarSesion" replace />} />
                <Route path="/paginaPago" element={<Navigate to="/iniciarSesion" replace />} />
              </>
          )}

          {/* Cliente */}
          {sessionType === "CLIENTE" && (
              <>
                <Route path="/diseniar/pizza" element={<DiseniarCreacion tipo="Pizza" />} />
                <Route path="/diseniar/burger" element={<DiseniarCreacion tipo="Hamburguesa" />} />
                <Route path="/verPedidos" element={<Pedidos />} />
                <Route path="/hacerUnPedido" element={<NuevoPedido />} />
                <Route path="/paginaPago" element={<CheckoutPage />} />
                <Route path="/favoritos" element={<Favoritos />} />
                <Route path="/verCreacion" element={<VerCreacion />} />
                <Route path="/perfil" element={<Perfil />} />
              </>
          )}

          {/* Admin */}
          {sessionType === "ADMIN" && (
              <>
                <Route path="/admin" element={<Navigate to="/panelAdministrador" replace />} />
                <Route path="/panelAdministrador" element={<PanelAdministrador />}>
                  <Route index element={<Navigate to="productos" replace />} />
                  <Route path="productos" element={<ProductosAdmin />} />
                  <Route path="pedidos" element={<PedidosAdmin />} />
                  <Route path="administradores" element={<AgregarAdministradores />} />
              </Route>
                </>
          )}

          {/* Fallback */}
          <Route path="*" element={<Navigate to="/paginaPrincipal" replace />} />
        </Routes>
      </CarritoProveedor>
  );
}

export default App;
