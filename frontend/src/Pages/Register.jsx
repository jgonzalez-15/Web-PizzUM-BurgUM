import Footer from "../Components/Footer"
import Button from "../Components/Button"

export default function Register(){
    return(
    <>
        <div className="h-screen w-screen flex flex-col items-center mx-auto justify-between">

            {/* Titulo */}
            <h1 className="font-bold text-2xl mt-8 mb-8">Registrarse</h1>
            <div className="mb-8 overflow-auto items-center flex-1 w-full flex flex-col gap-8">

                {/* SECCION: Datos del usuario */}
                <div className="bg-gray-50 shadow-xl rounded-2xl w-[calc(100vw-4rem)] md:w-[calc(100vw-32rem)]">
                    <div className="m-8">
                        <h1 className="font-bold text-xl">Datos del usuario</h1>
                        <form action="" className="p-4">
                            <h1>Correo electrónico</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"/>
                            <h1>Contraseña</h1>
                            <input type="password" className="bg-gray-100 rounded-2xl w-full"/>
                            <h1>Repetir contraseña</h1>
                            <input type="password" className="bg-gray-100 rounded-2xl w-full"/>
                        </form>
                    </div>
                </div>

                {/* SECCION: Datos del domicilio */}
                <div className="bg-gray-50 shadow-xl rounded-2xl w-[calc(100vw-4rem)] md:w-[calc(100vw-32rem)]">
                    <div className="m-8">
                        <h1 className="font-bold text-xl overflow-auto">Datos del domicilio</h1>
                        <form action="" className="p-4">
                            <h1>Dirección</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"/>
                        </form>
                    </div>
                </div>

                {/* SECCION: Datos del pago */}
                <div className="bg-gray-50 shadow-xl rounded-2xl w-[calc(100vw-4rem)] md:w-[calc(100vw-32rem)]">
                    <div className="m-8">
                        <h1 className="font-bold text-xl overflow-auto">Datos del medio de pago</h1>
                        <form action="" className="p-4">
                            <h1>Número de tarjeta</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"/>
                            <h1>Titular</h1>
                            <input type="text" className="bg-gray-100 rounded-2xl w-full"/>
                            <h1>Vencimiento</h1>
                            <input type="month" className="bg-gray-100 rounded-2xl"/>
                        </form>
                    </div>
                </div>

                {/* Boton registrarse */}
                <Button text="Registrarse" isPrimary={true}/>
            </div>
            <Footer/>
        </div>
    </>    
    )
}