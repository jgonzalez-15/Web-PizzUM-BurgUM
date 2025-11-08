import './App.css'
import { SessionContext } from './Components/context/SessionContext'
import { Routes, Route, Navigate, useNavigate } from 'react-router-dom'
import { CartProvider } from './Components/context/CartItems'
import { useContext, useEffect, useState } from 'react'

import HomePage from './Pages/HomePage'
import Design from './Pages/Design'
import Orders from './Pages/Orders'
import NewOrder from './Pages/NewOrder'
import Login from './Pages/Login'
import Backoffice from './Pages/Backoffice'
import Favoritos from './Pages/Favoritos'
import ViewCreation from './Pages/ViewCreation'
import Register from './Pages/Register'
import Options from './Pages/Options'
import Perfil from "./Pages/Perfil";
import CheckoutPage from './Pages/CheckoutPage';


function App() {
  const { sessionType, setSessionType, setSessionInfo } = useContext(SessionContext)
  const navigate = useNavigate()
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const refreshToken = async () => {
      try {
        const response = await fetch('http://localhost:8080/auth/refresh', {
          method: 'GET',
          credentials: 'include'
        });
        if (response.ok) {
          const data = await response.json();
          setSessionType(data.rol);
          setSessionInfo(data.info);
          localStorage.setItem("token", data.jwt);
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
        setIsLoading(false);
      }
    };

    refreshToken();
  }, []);

  let routes
  if (sessionType == "INVITADO"){
    routes = (
      <>
      <CartProvider>
        <Routes>
          <Route path='/' element={<Navigate to="/homepage" replace/>}/>
          <Route path='/homepage' element={<HomePage/>}/>
          <Route path='/design/pizza' element={<Navigate to="/login" replace/>}/>
          <Route path='/design/burger' element={<Navigate to="/login" replace/>}/>
          <Route path='/login' element={<Login/>}/>
          <Route path='/register' element={<Register/>}/>
          <Route path='/admin' element={<Navigate to="/login" replace/>}/>
          <Route path='/favoritos' element={<Navigate to="/login" replace/>}/>
        </Routes>
      </CartProvider>
    </>
    )
  }else if (sessionType == "CLIENTE"){
    routes = (
      <>
      <CartProvider>
        <Routes>
          <Route path='/' element={<Navigate to="/homepage" replace/>}/>
          <Route path='/homepage' element={<HomePage/>}/>
          <Route path='/design/pizza' element={<Design type='Pizza'/>}/>
          <Route path='/design/burger' element={<Design type='Burger'/>}/>
          <Route path='/viewOrders' element={<Orders/>}/>
          <Route path='/order' element={<NewOrder/>}/>
          <Route path='/checkout' element={<CheckoutPage/>}/>
          <Route path='/login' element={<Login/>}/>
          <Route path='/register' element={<Register/>}/>
          <Route path='/favoritos' element={<Favoritos/>}/>
          <Route path='/viewCreation' element={<ViewCreation/>}/>
          <Route path='/config' element={<Options/>}/>
          <Route path="/perfil" element={<Perfil/>} />

        </Routes>
      </CartProvider>
    </>
    )
  }
  else if (sessionType == "ADMIN"){
    routes = (
      <>
      <Routes>
          <Route path='/' element={<Navigate to="/admin" replace/>}/>
          <Route path='/login' element={<Login/>}/>
          <Route path='/admin' element={<Backoffice/>}/>
      </Routes>
      </>
    )
  }

  if (isLoading) {
    return null; // or return a loading spinner component if you have one
  }

  return (
    <>
      {routes}
    </>
  )
}

export default App
