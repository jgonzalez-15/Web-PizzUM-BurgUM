import { useState } from "react";
import EncabezadoAdmin from "../Components/EncabezadoAdmin.jsx";
import PieDePagina from "../Components/PieDePagina.jsx";

function PedidosFechaAdmin() {
  const [fecha, setFecha] = useState("");
  const [pedidos, setPedidos] = useState([]);
  const [cargando, setCargando] = useState(false);

  const buscarPedidos = async () => {
    if (!fecha) return alert("Seleccioná una fecha.");
    setCargando(true);
    try {
      const respuesta = await fetch(
        `http://localhost:8080/api/pedido/por-fecha?fecha=${fecha}`,
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
            Pedidos por fecha
          </h1>

          {/* Filtro de fecha */}
          <div className="flex justify-center gap-4 mb-6">
            <input
              type="date"
              value={fecha}
              onChange={(e) => setFecha(e.target.value)}
              className="border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-orange-400 focus:outline-none"
            />
            <button
              onClick={buscarPedidos}
              className="bg-orange-500 hover:bg-orange-600 text-white font-bold px-5 py-2 rounded-lg"
            >
              Buscar
            </button>
          </div>

          {/* Resultados */}
          {cargando ? (
            <p className="text-gray-500 text-center italic">Cargando...</p>
          ) : pedidos.length > 0 ? (
            <table className="w-full border-collapse border border-gray-300 rounded-lg overflow-hidden">
              <thead className="bg-gray-100">
                <tr>
                  <th className="p-3 border">ID</th>
                  <th className="p-3 border">Cliente</th>
                  <th className="p-3 border">Estado</th>
                  <th className="p-3 border">Precio</th>
                  <th className="p-3 border">Domicilio</th>
                  <th className="p-3 border">Medio de Pago</th>
                  <th className="p-3 border">Acciones</th>
                </tr>
              </thead>
              <tbody>
                {pedidos.map((p) => (
                  <tr
                    key={p.id}
                    className="hover:bg-gray-50 transition border-b"
                  >
                    <td className="p-3 text-center">{p.id}</td>
                    <td className="p-3">{p.idClienteAsignado}</td>
                    <td
                      className={`p-3 text-center font-semibold ${
                        p.estado === "Cancelado"
                          ? "text-red-500"
                          : p.estado === "Entregado"
                          ? "text-green-600"
                          : "text-yellow-600"
                      }`}
                    >
                      {p.estado}
                    </td>
                    <td className="p-3 text-center">${p.precio.toFixed(2)}</td>
                    <td className="p-3 text-center">{p.domicilio}</td>
                    <td className="p-3 text-center">{p.idMedioDePago}</td>
                    <td className="p-3 text-center">
                      <button
                        onClick={() =>
                          alert(`Detalles del pedido #${p.id} próximamente`)
                        }
                        className="text-orange-500 hover:underline"
                      >
                        Ver detalle
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p className="text-gray-500 text-center italic">
              No hay pedidos para la fecha seleccionada.
            </p>
          )}
        </div>
      </div>
      <PieDePagina />
    </>
  );
}

export default PedidosFechaAdmin;
