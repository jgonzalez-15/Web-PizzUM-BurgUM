import './App.css'
import HomePage from './Pages/HomePage'
import { Routes, Route, Link, Navigate } from 'react-router-dom'

function App() {
  return (
    <>
      <Routes>
        <Route path='/' element={<Navigate to="/homepage" replace/>}/>
        <Route path='/homepage' element={<HomePage/>}/>
      </Routes>
    </>
  )
}

export default App
