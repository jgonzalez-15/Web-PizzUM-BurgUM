import { useState, useEffect, useContext } from "react";
import { DesignContext } from "../Components/context/DesignContext";
import { SessionContext } from "../Components/context/SessionContext";
import Encabezado from "../Components/Encabezado.jsx";
import IngredientAdder from "../Components/IngredientAdder.jsx";
import PieDePagina from "../Components/PieDePagina.jsx";
import { usarCarrito } from "../Components/context/CarritoContexto.jsx";

function DiseniarCreacion({ tipo }) {
  const { sessionInfo } = useContext(SessionContext);
  const { agregarItem } = usarCarrito();
  const clienteId = sessionInfo?.email;

  const [glutenFreeOnly, setGlutenFreeOnly] = useState(false);
  const [ingredients, setIngredients] = useState([]);
  const [cantCarnes, setCantCarnes] = useState(1);
  const [tamanio, setTamanio] = useState("1");
  const [creacion, setCreacion] = useState();
  const [nombre, setNombre] = useState("Nueva creación");

  useEffect(() => window.scrollTo(0, 0), []);

  useEffect(() => {
    const obtenerIngredientes = async () => {
      try {
        const respuesta = await fetch("http://localhost:8080/api/producto/listar", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        });
        if (respuesta.ok) {
          const productos = await respuesta.json();
          setIngredients(productos.map((item) => ({ ...item, selected: false })));
        } else alert("Error al obtener los ingredientes");
      } catch {
        alert("No se pudo conectar con el servidor");
      }
    };
    obtenerIngredientes();
  }, []);

  useEffect(() => setCreacion(undefined), [ingredients, cantCarnes, tamanio]);
  useEffect(() => setIngredients((prev) => prev.map((i) => ({ ...i, selected: false }))), [glutenFreeOnly]);

  const crearNuevaCreacion = async (favorita) => {
    const seleccionados = ingredients.filter((i) => i.selected);

    if (tipo === "Pizza") {
      const tieneMasa = seleccionados.some((i) => i.tipo === "Masa");
      const tieneSalsa = seleccionados.some((i) => i.tipo === "Salsa");
      if (!tieneMasa || !tieneSalsa)
        return alert("Debes seleccionar una masa y una salsa.");
    } else if (tipo === "Hamburguesa") {
      const tienePan = seleccionados.some((i) => i.tipo === "Pan");
      const tieneCarne = seleccionados.some((i) => i.tipo === "Hamburguesa");
      if (!tienePan || !tieneCarne)
        return alert("Debes seleccionar un pan y una carne.");
    }

    const ingredientesFinales = seleccionados.map((i) => ({
      idProducto: i.idProducto,
      cantidad: i.tipo === "Hamburguesa" ? cantCarnes : 1,
    }));

    const datosEnvio = {
      clienteId,
      ingredientes: ingredientesFinales,
      esFavorita: favorita,
      nombre,
    };

    if (tipo === "Pizza")
      datosEnvio.tamanio =
          tamanio === "1" ? "Chica" : tamanio === "2" ? "Mediana" : "Grande";

    const endpoint =
        tipo === "Pizza"
            ? "http://localhost:8080/api/pizza/crear"
            : "http://localhost:8080/api/hamburguesa/crearHamburguesa";

    try {
      const respuesta = await fetch(endpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(datosEnvio),
        credentials: "include",
      });

      if (!respuesta.ok) {
        alert("Ocurrió un error al crear la creación");
        return null;
      }

      const nuevaCreacion = await respuesta.json();
      nuevaCreacion.nombre = nombre;
      nuevaCreacion.tipo = tipo;
      setCreacion(nuevaCreacion);

      if (favorita) await agregarFavorito(nuevaCreacion.idCreacion);
      return nuevaCreacion;
    } catch {
      alert("No se pudo conectar con el servidor");
      return null;
    }
  };

  const agregarFavorito = async (idCreacion) => {
    try {
      const respuesta = await fetch("http://localhost:8080/api/favorito/agregar", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify({ clienteId, nombre, idCreacion }),
        credentials: "include",
      });
      if (respuesta.ok) alert("Creación agregada a favoritos");
      else alert("Error al agregar favorito");
    } catch {
      alert("No se pudo conectar con el servidor");
    }
  };

  const content =
      tipo === "Pizza" ? (
          <>
            <div className="mt-4 mb-8 flex justify-start px-10">
              <label className="text-lg font-semibold text-gray-700">
                Tamaño de pizza:{" "}
                <select
                    value={tamanio}
                    onChange={(e) => setTamanio(e.target.value)}
                    className="ml-2 border-2 rounded-lg p-2 bg-gray-50 text-gray-800 focus:ring-2 focus:ring-orange-400 outline-none"
                >
                  <option value="1">Chica</option>
                  <option value="2">Mediana</option>
                  <option value="3">Grande</option>
                </select>
              </label>
            </div>
            <IngredientAdder text="Elegí tu masa" maxCount={1} allIngredients={ingredients} tipo="Masa" setIngredients={setIngredients} />
            <IngredientAdder text="Elegí tu salsa" maxCount={1} allIngredients={ingredients} tipo="Salsa" setIngredients={setIngredients} />
            <IngredientAdder text="Elegí tus toppings" maxCount={0} allIngredients={ingredients} tipo="Topping" setIngredients={setIngredients} />
          </>
      ) : (
          <>
            <IngredientAdder text="Elegí tu pan" maxCount={1} allIngredients={ingredients} tipo="Pan" setIngredients={setIngredients} />
            <IngredientAdder text="Elegí tus carnes" maxCount={1} allIngredients={ingredients} tipo="Hamburguesa" setIngredients={setIngredients} />
            <div className="mt-4 mb-8 flex justify-start px-10">
              <label className="text-lg font-semibold text-gray-700">
                Cantidad de carnes:{" "}
                <select
                    value={cantCarnes}
                    onChange={(e) => setCantCarnes(parseInt(e.target.value))}
                    className="ml-2 border-2 rounded-lg p-2 bg-gray-50 text-gray-800 focus:ring-2 focus:ring-orange-400 outline-none"
                >
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                </select>
              </label>
            </div>
            <IngredientAdder text="Elegí tus salsas" maxCount={0} allIngredients={ingredients} tipo="Salsa_Hamburguesa" setIngredients={setIngredients} />
            <IngredientAdder text="Elegí tus ingredientes" maxCount={0} allIngredients={ingredients} tipo="Ingrediente" setIngredients={setIngredients} />
          </>
      );

  return (
      <DesignContext.Provider value={{ glutenFreeOnly, setGlutenFreeOnly }}>
        <div className="min-h-screen bg-white flex flex-col">
          <Encabezado />

          <main className="flex flex-col items-center mt-16 mb-20 px-6">
            <div className="bg-white rounded-3xl shadow-lg p-10 w-full max-w-5xl border border-gray-100">
              <h1 className="text-4xl font-extrabold text-gray-800 text-center mb-4">
                Creá tu nueva creación
              </h1>
              <p className="text-gray-500 text-center mb-8">
                Personalizala con tus ingredientes favoritos
              </p>

              {/* Switch sin gluten */}
              <div className="flex justify-end items-center gap-3 mb-8">
                <span className="text-gray-700 font-medium">Solo sin gluten</span>
                <div
                    className={`w-12 h-6 flex items-center rounded-full p-1 cursor-pointer transition-all ${
                        glutenFreeOnly ? "bg-orange-500" : "bg-gray-300"
                    }`}
                    onClick={() => setGlutenFreeOnly(!glutenFreeOnly)}
                >
                  <div
                      className={`bg-white w-4 h-4 rounded-full shadow-md transform transition-transform ${
                          glutenFreeOnly ? "translate-x-6" : "translate-x-0"
                      }`}
                  ></div>
                </div>
              </div>

              {/* Sección ingredientes */}
              <section className="w-full space-y-10">{content}</section>

              <hr className="my-10 border-gray-300" />

              {/* Nombrar creación */}
              <section className="flex flex-col items-center mt-12 mb-8">
                <h2 className="text-lg font-semibold text-gray-700 mb-2">
                  Nombrá tu creación
                </h2>
                <input
                    type="text"
                    className="bg-gray-50 rounded-xl p-3 w-80 text-center text-gray-800 border border-gray-200 focus:ring-2 focus:ring-orange-400 outline-none"
                    value={nombre}
                    onChange={(e) => setNombre(e.target.value)}
                />
              </section>

              {/* Botones */}
              <div className="flex flex-col md:flex-row justify-center items-center gap-6 mt-8">
                <button
                    onClick={() => crearNuevaCreacion(true)}
                    className="w-56 bg-orange-500 text-white font-bold py-3 px-6 rounded-2xl shadow-md transition-transform transform hover:scale-105 hover:bg-orange-600"
                >
                  Agregar a favoritos
                </button>

                <button
                    onClick={async () => {
                      const nueva = await crearNuevaCreacion(false);
                      if (nueva) {
                        agregarItem(nueva);
                        alert(`${nueva.nombre} agregado al carrito`);
                      }
                    }}
                    className="w-56 bg-orange-500 text-white font-bold py-3 px-6 rounded-2xl shadow-md transition-transform transform hover:scale-105 hover:bg-orange-600"
                >
                  Agregar al carrito
                </button>
              </div>
            </div>
          </main>

          <PieDePagina />
        </div>
      </DesignContext.Provider>
  );
}

export default DiseniarCreacion;
