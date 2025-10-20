import { useLocation } from "react-router-dom"
import SmallButton from "../Components/SmallButton"
import { Link } from "react-router-dom"
import Footer from "../Components/Footer"

function Login(){
    return(
        <>
        <div className="flex flex-col h-screen w-screen justify-between">
            <div className="h-full flex flex-col items-center align-middle justify-center">
                <div className="flex flex-col justify-center align-middle items-center bg-gray-100 w-64 h-96 shadow-2xl rounded-2xl">
                    <h1 className="font-bold text-center md:text-2xl 2xl:text-5xl">LogIn</h1>
                    <form action="" className="flex flex-col justify-center m-8 mb-2">
                        <h4 className="ml-2">Correo electrónico:</h4>
                        <input type="text" className="bg-gray-200 rounded-2xl mt-1 mb-4 p-1"/>
                        <h4 className="ml-2">Contraseña:</h4>
                        <input type="password" className="bg-gray-200 rounded-2xl mt-1 mb-4 p-1"/>
                        <div className="flex flex-row justify-center mt-4">
                            <SmallButton text='Cancelar' isPrimary={false} route={useLocation().state?.from}/>
                            <SmallButton text='Ingresar' isPrimary={true} route={useLocation().state?.from}/>
                        </div>
                    </form>
                    <Link to='/register' className="m-1 text-blue-800/50 underline hover:text-blue-800">Registrarse</Link>
                </div>
            </div>
            <Footer/>
        </div>
        </>
    )
}

export default Login