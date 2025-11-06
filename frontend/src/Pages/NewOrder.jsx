import { useCart } from "../Components/context/CartItems";
import MainHeader from "../Components/MainHeader";
import Footer from "../Components/Footer";
import OrderResumeItem from "../Components/OrderResumeItem";
import SmallButton from "../Components/SmallButton";
import OrderItem from "../Components/OrderItem";
import Button from "../Components/Button";

export default function NewOrder() {
    if (window.pageYOffset > 0) {
        window.scrollTo(0, 0);
    }

    const { items, removeItem, addItem } = useCart();
    const total = items.reduce((sum, item) => sum + item.precio, 0);

    const handleAddDrink = (bebida) => {
        const drinkPrices = {
            "Coca-Cola": 300,
            "Sprite": 280,
            "Agua": 200,
            "Fanta": 290,
        };

        const newDrink = {
            id: Date.now(),
            nombre: bebida,
            tipo: "Bebida",
            precio: drinkPrices[bebida] || 250,
            ingredientes: [],
        };

        addItem(newDrink);
    };

    return (
        <>
            <MainHeader className="z-10" />
            <div className="flex flex-col pt-20 w-screen max-w-full min-h-[calc(100vh)] justify-between bg-gray-50">
                <div className="w-full flex flex-1 flex-col md:flex-row h-full items-start justify-between gap-8 px-6 md:px-16">

                    {/* Sección izquierda — productos */}
                    <div className="w-full md:w-2/3 flex flex-col items-center md:items-start">
                        <h1 className="text-3xl font-extrabold text-gray-800 mb-6 mt-2">
                            Tu carrito
                        </h1>

                        {items.length === 0 ? (
                            <div className="flex flex-col items-center justify-center text-center bg-white shadow-md rounded-2xl p-8 w-full md:w-2/3">
                                <h2 className="text-gray-600 mb-4">
                                    No hay artículos en tu carrito todavía.
                                </h2>
                                <Button
                                    text="Volver a la página principal"
                                    isPrimary={true}
                                    route="/homepage"
                                />
                            </div>
                        ) : (
                            <div className="w-full flex flex-col gap-4">
                                {items.map((item) => (
                                    <div
                                        key={item.id}
                                        className="bg-white p-5 rounded-2xl shadow-md hover:shadow-lg transition-all flex flex-col md:flex-row justify-between items-start md:items-center border border-gray-100"
                                    >
                                        <div className="flex flex-col gap-1 w-full">
                                            <h2 className="text-xl font-bold text-gray-800">
                                                {item.nombre}
                                            </h2>
                                            {item.tipo && (
                                                <p className="text-sm text-gray-500">
                                                    Tipo:{" "}
                                                    <span className="text-gray-700">{item.tipo}</span>
                                                </p>
                                            )}
                                            {item.ingredientes && item.ingredientes.length > 0 && (
                                                <p className="text-sm text-gray-500 truncate max-w-md">
                                                    Ingredientes:{" "}
                                                    <span className="text-gray-700">
                                                        {item.ingredientes.map((i) => i.nombre).join(", ")}
                                                    </span>
                                                </p>
                                            )}
                                            <p className="text-base font-semibold text-orange-600 mt-1">
                                                ${item.precio}
                                            </p>
                                        </div>

                                        <button
                                            onClick={() => removeItem(item.id)}
                                            className="mt-3 md:mt-0 bg-red-100 hover:bg-red-200 text-red-600 font-semibold px-3 py-1 rounded-xl transition-all text-sm"
                                        >
                                            Quitar
                                        </button>
                                    </div>
                                ))}

                                {/* Bloque adicional: agregar bebidas */}
                                <div className="mt-6 bg-white p-6 rounded-2xl shadow-md border border-gray-100 flex flex-col gap-4">
                                    <h3 className="font-semibold text-lg text-gray-800">
                                        Agregar bebida
                                    </h3>
                                    <p className="text-gray-600 text-sm">
                                        Elegí una bebida para acompañar tu pedido:
                                    </p>
                                    <div className="flex flex-wrap gap-3">
                                        {[
                                            { nombre: "Coca-Cola", precio: 300 },
                                            { nombre: "Sprite", precio: 280 },
                                            { nombre: "Agua", precio: 200 },
                                            { nombre: "Fanta", precio: 290 },
                                        ].map((bebida) => (
                                            <button
                                                key={bebida.nombre}
                                                className="flex flex-col items-center px-4 py-2 bg-orange-50 border border-orange-200 text-orange-600 font-semibold rounded-xl hover:bg-orange-100 transition-all text-sm"
                                                onClick={() => handleAddDrink(bebida.nombre)}
                                            >
                                                {bebida.nombre}
                                                <span className="text-xs font-normal text-gray-500 mt-1">
                                                    ${bebida.precio}
                                                </span>
                                            </button>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>

                    {/* Sección derecha — resumen */}
                    <div className="w-full md:w-1/3 bg-white shadow-xl rounded-2xl p-6 border border-gray-100 sticky top-24 self-start">
                        <h1 className="font-bold text-2xl mb-4 text-gray-800">
                            Resumen de pedido
                        </h1>

                        {items.length === 0 ? (
                            <p className="text-gray-500 text-center mt-10">
                                No hay artículos en el carrito
                            </p>
                        ) : (
                            <>
                                <div className="flex flex-col">
                                    {items.map((item) => (
                                        <div key={item.id} className="flex justify-between py-1">
                                            <span className="text-gray-700 font-medium">
                                                {item.nombre}
                                            </span>
                                            <span className="text-gray-700 font-medium">
                                                ${item.precio}
                                            </span>
                                        </div>
                                    ))}
                                </div>

                                <div className="flex flex-row justify-between items-center pt-6 border-t mt-4">
                                    <h2 className="font-bold text-xl text-gray-800">Total:</h2>
                                    <h2 className="font-bold text-xl text-orange-600">${total}</h2>
                                </div>

                                <div className="flex flex-col md:flex-row justify-center items-center gap-4 mt-6">
                                    <SmallButton
                                        text="Seguir comprando"
                                        isPrimary={false}
                                        route="/homepage"
                                    />
                                    <SmallButton text="Pasar al pago" isPrimary={true} />
                                </div>
                            </>
                        )}
                    </div>
                </div>

                <Footer />
            </div>
        </>
    );
}
