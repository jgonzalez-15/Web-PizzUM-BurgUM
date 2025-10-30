import { useState, useEffect, useContext } from "react";
import { DesignContext } from "../Components/context/DesignContext";
import { SessionContext } from "../Components/context/SessionContext";

import MainHeader from "../Components/MainHeader";
import IngredientAdder from "../Components/IngredientAdder";
import Footer from "../Components/Footer";
import AddToCartButton from "../Components/AddToCartButton";
import SmallButton from "../Components/SmallButton";

function Design({ type }) {
  useEffect(() => {
    if (window.pageYOffset > 0) {
      window.scrollTo(0, 0);
    }
  }, []);

  const [glutenFreeOnly, setGlutenFreeOnly] = useState(false);
  const [ingredients, setIngredients] = useState([]);
  const [cantCarnes, setCantCarnes] = useState(1);
  const [tamanio, setTamanio] = useState(1);
  const [creacion, setCreacion] = useState();
  const { sessionInfo } = useContext(SessionContext);
  const clienteId = sessionInfo?.email;

  useEffect(() => {
    const handleGetIngredients = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/producto/listar", {
          method: "GET",
          headers: { "Content-Type": "application/json" }
        });

        if (response.ok) {
          const data = await response.json();
          const newIngredients = data.map(item => ({
            ...item,
            selected: false
          }));
          setIngredients(newIngredients);
        } else {
          alert("Ocurrió un error");
        }
      } catch (error) {
        console.error("Error al obtener ingredientes:", error);
      }
    };

    handleGetIngredients();
  }, []);

  useEffect(() => {
    setCreacion(undefined);
  }, [ingredients, cantCarnes, tamanio]);

  useEffect(() => {
    setIngredients(prev =>
        prev.map(ingred => ({ ...ingred, selected: false }))
    );
    }, [glutenFreeOnly]);


  const handleNewCreation = async (favorita) => {
    const seleccionados = ingredients.filter(i => i.selected);
    const ingredientesFinales = seleccionados.map(i => ({
      idProducto: i.idProducto,
      cantidad: i.tipo === "Hamburguesa" ? cantCarnes : 1
    }));

    const payload = {
      clienteId,
      ingredientes: ingredientesFinales,
      esFavorita: favorita
    };

    if (type === "Pizza") {
      payload.tamanio = tamanio;
    }

    const endpoint =
      type === "Pizza"
        ? "http://localhost:8080/api/pizza/crear"
        : "http://localhost:8080/api/hamburguesa/crearHamburguesa";

    try {
      const response = await fetch(endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
        credentials: "include"
      });

      if (response.ok) {
        const data = await response.json();
        setCreacion(data);
        return data;
      } else {
        alert("Los datos son incorrectos");
      }
    } catch (error) {
      console.error("Error al crear la creación:", error);
    }
  };

  let content;
  if (type === "Pizza") {
    content = (
      <>
        <div className="ml-16 mb-8">
          <label className="text-xl font-bold block">
            Tamaño de Pizza:{" "}
            <select value={tamanio} onChange={(e) => setTamanio(e.target.value)}>
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
    );
  } else if (type === "Burger") {
    content = (
      <>
        <IngredientAdder text="Elegí tu pan" maxCount={1} allIngredients={ingredients} tipo="Pan" setIngredients={setIngredients} />
        <IngredientAdder text="Elegí tus carnes" maxCount={1} allIngredients={ingredients} tipo="Hamburguesa" setIngredients={setIngredients} />
        <div className="ml-16 mb-8">
          <label className="text-xl font-bold block">
            Cantidad de carnes:{" "}
            <select value={cantCarnes} onChange={(e) => setCantCarnes(parseInt(e.target.value))}>
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
  }

  return (
    <>
      <DesignContext.Provider value={{ glutenFreeOnly, setGlutenFreeOnly }}>
        <div className="h-full max-w-screen">
          <MainHeader className="z-10" />
          <div className="flex mt-16 h-full justify-center flex-col max-w-full">
            <div className="flex justify-center m-2 md:m-4">
              <h1 className="font-bold text-2xl">Armá tu nueva {type}</h1>
            </div>

            <div className="flex w-full max-w-screen justify-end-safe items-center">
              <h1 className="mr-2">Solo sin gluten</h1>
              <button
                onClick={() => setGlutenFreeOnly(prev => !prev)}
                className={`h-4 w-4 rounded-lg font-semibold transition mr-16 md:h-6 md:w-6 text-xs md:text-base
                  ${glutenFreeOnly ? "bg-orange-400" : "bg-transparent"} border-2`}
              ></button>
            </div>

            {content}
          </div>

          <div className="flex flex-col md:flex-row justify-center items-center m-8 mb-16">
            <button className={`transition-transform duration-100 ease-in-out hover:scale-102 rounded-2xl shadow-2xl font-bold m-1 text-sm md:text-base 2xl:text-xl text-center max-w-64 bg-gray-300 text-black`}
            onClick={async () => {
                const nueva = await handleNewCreation(true);
                if (nueva) alert("Creación agregada a favoritos");
              }}>
                <h2 className="m-2 2xl:m-3">
                    Agregar a favoritos
                </h2>
            </button>
            <div className="w-4"></div>
            <AddToCartButton isPrimary={true} item={creacion} handle={handleNewCreation} />
          </div>
          <Footer />
        </div>
      </DesignContext.Provider>
    </>
  );
}

export default Design;