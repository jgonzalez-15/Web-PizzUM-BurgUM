import './App.css'
import HomePage from './Pages/HomePage'
import Design from './Pages/Design'
import { Routes, Route, Link, Navigate } from 'react-router-dom'
import Orders from './Pages/Orders'
import NewOrder from './Pages/NewOrder'
import Login from './Pages/Login'
import Backoffice from './Pages/Backoffice'
import Favourites from './Pages/Favourites'

function App() {
  return (
    <>
      <Routes>
        <Route path='/' element={<Navigate to="/homepage" replace/>}/>
        <Route path='/homepage' element={<HomePage/>}/>
        <Route path='/design/pizza' element={<Design type='Pizza'/>}/>
        <Route path='/design/burger' element={<Design type='Burger'/>}/>
        <Route path='/viewOrders' element={<Orders/>}/>
        <Route path='/order' element={<NewOrder/>}/>
        <Route path='/login' element={<Login/>}/>
        <Route path='/admin' element={<Backoffice/>}/>
        <Route path='/favourites' element={<Favourites/>}/>
      </Routes>
    </>
  )
}

export default App
