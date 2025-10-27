import './App.css'
import HomePage from './Pages/HomePage'
import Design from './Pages/Design'
import { Routes, Route, Navigate } from 'react-router-dom'
import Orders from './Pages/Orders'
import NewOrder from './Pages/NewOrder'
import Login from './Pages/Login'
import Backoffice from './Pages/Backoffice'
import Favourites from './Pages/Favourites'
import { CartProvider } from './Components/context/CartItems'
import ViewCreation from './Pages/ViewCreation'
import Register from './Pages/Register'
import { useState } from 'react'
import Options from './Pages/Options'

function App() {
  const [session, setSession] = useState("Client")

  let routes

  if (session === "Client" || session === "Guest"){
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
          <Route path='/login' element={<Login/>}/>
          <Route path='/register' element={<Register/>}/>
          <Route path='/admin' element={<Navigate to="/login" replace/>}/>
          <Route path='/favourites' element={<Favourites/>}/>
          <Route path='/viewCreation' element={<ViewCreation/>}/>
          <Route path='/config' element={<Options/>}/>
        </Routes>
      </CartProvider>
    </>
    )
  }
  else if (session == "Admin"){
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

  return (
    <>
      {routes}
    </>
  )
}

export default App
