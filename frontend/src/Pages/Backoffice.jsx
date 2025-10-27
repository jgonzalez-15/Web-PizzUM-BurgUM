import { use, useState } from "react"
import AdminHeader from "../Components/AdminHeader"
import Footer from "../Components/Footer"
import SmallButton from "../Components/SmallButton"

function Backoffice(){
    const [newAdmin, setNewAdmin] = useState({user:"Luca Benenati", password:"asbjkvsmo"})
    const [showUser, setShowUser] = useState(false)

    function showNewAdmin(){
        setShowUser(true)
    }

    return(
        <>
            <AdminHeader/>
            <div className="mt-16 min-h-[calc(100vh-4rem)] flex flex-col justify-between">
                <div></div>
                <Footer/>
            </div>
        </>
    )
}

export default Backoffice