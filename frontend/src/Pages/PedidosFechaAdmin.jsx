import { useState } from "react";

function PedidosFechaAdmin() {
    const [fechaInicio, setFechaInicio] = useState("");
    const [fechaFin, setFechaFin] = useState("");
    const [pedidos, setPedidos] = useState([]);
    const [cargando, setCargando] = useState(false);

    const buscarPedidos = async () => {
        if (!fechaInicio || !fechaFin)
            return alert("Seleccioná una fecha de inicio y una de fin.");
        setCargando(true);
        try {
            const respuesta = await fetch(
                `http://localhost:8080/api/pedido/rango/${fechaInicio}/${fechaFin}`,
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                    credentials: "include",
                }
            );

            if (respuesta.ok) {
                const data = await respuesta.json();
                setPedidos(data);
            } else {
                alert("Error al obtener los pedidos.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("Error al conectar con el servidor.");
        } finally {
            setCargando(false);
        }
    };

    return (
        <>
            <div className="pt-24 min-h-screen bg-gray-50 flex flex-col items-center px-6">
                <div className="w-full max-w-5xl bg-white p-8 shadow-xl rounded-2xl border border-gray-200">
                    <h1 className="text-3xl font-bold text-gray-800 mb-6 text-center">
                        Pedidos por rango de fechas
                    </h1>

                    <div className="flex flex-col md:flex-row justify-center gap-4 mb-6">
                        <input
                            type="date"
                            value={fechaInicio}
                            onChange={(e) => setFechaInicio(e.target.value)}
                            className="border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-orange-400 focus:outline-none"
                        />
                        <input
                            type="date"
                            value={fechaFin}
                            onChange={(e) => setFechaFin(e.target.value)}
                            className="border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-orange-400 focus:outline-none"
                        />
                        <button
                            onClick={buscarPedidos}
                            className="bg-orange-500 hover:bg-orange-600 text-white font-bold px-5 py-2 rounded-lg"
                        >
                            Buscar
                        </button>
                    </div>

                    {cargando ? (
                        <p className="text-gray-500 text-center italic">Cargando...</p>
                    ) : pedidos.length > 0 ? (
                        <div className="overflow-x-auto">
                        <table className="w-full border-collapse border border-gray-300 rounded-lg">
                            <thead className="bg-gray-100">
                            <tr>
                                <th className="p-2 border">ID</th>
                                <th className="p-2 border">Fecha</th>
                                <th className="p-2 border">Cliente</th>
                                <th className="p-2 border">Estado</th>
                                <th className="p-2 border">Precio</th>
                                <th className="p-2 border">Domicilio</th>
                                <th className="p-2 border">Calificación</th>
                            </tr>
                            </thead>
                            <tbody>
                            {pedidos.map((p) => (
                                <tr key={p.id} className="hover:bg-gray-50 border-b">
                                    <td className="p-2 text-center">{p.id}</td>
                                    <td className="p-2 text-center">{p.fecha}</td>
                                    <td className="p-2">{p.idClienteAsignado}</td>
                                    <td
                                        className={`p-2 text-center font-semibold ${
                                            p.estado === "Cancelado"
                                                ? "text-red-500"
                                                : p.estado === "Entregado"
                                                    ? "text-green-600"
                                                    : "text-yellow-600"
                                        }`}
                                    >
                                        {p.estado}
                                    </td>
                                    <td className="p-2 text-center">${p.precio.toFixed(2)}</td>
                                    <td className="p-2 text-center">{p.domicilio?.direccion}</td>
                                    {p.calificacion != 0 ? 
                                    (
                                        <td className="p-2 text-center">{p.calificacion}</td>
                                    ):
                                    (
                                        <td className="p-2 text-center">Sin calificar</td>
                                    )}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                        </div>
                    ) : (
                        <p className="text-gray-500 text-center italic">
                            No hay pedidos entre esas fechas.
                        </p>
                    )}
                </div>
            </div>
        </>
    );
}

export default PedidosFechaAdmin;
