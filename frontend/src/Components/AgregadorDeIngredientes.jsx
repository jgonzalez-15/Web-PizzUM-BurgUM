import { useContext } from "react";
import { DisenioContexto } from "./context/DisenioContexto.jsx";
import Ingrediente from "./Ingrediente.jsx";

export default function AgregadorDeIngredientes({
                                                    texto,
                                                    maximaSeleccion,
                                                    todosLosIngredientes,
                                                    setIngredientes,
                                                    tipo,
                                                }) {
    const { soloSinGluten } = useContext(DisenioContexto);

    const ingredientesPorTipo = todosLosIngredientes.filter(
        (i) => i.tipo === tipo
    );
    const ingredientesVisibles = soloSinGluten
        ? ingredientesPorTipo.filter((i) => i.sinTacc)
        : ingredientesPorTipo;

    const alternarSeleccion = (idProducto) => {
        setIngredientes((prev) =>

            prev.map((ingrediente) => {
                if (ingrediente.tipo !== tipo) return ingrediente;
                if (maximaSeleccion === 1) {
                    return { ...ingrediente, seleccionado: ingrediente.idProducto === idProducto };
                } else {
                    return ingrediente.idProducto === idProducto
                        ? { ...ingrediente, seleccionado: !ingrediente.seleccionado }
                        : ingrediente;
                }
            })
        );
    };

    return (
        <section className="flex flex-col max-w-full mb-10">
            <h2 className="font-bold text-xl text-gray-800 mb-4 ml-6 md:ml-16">
                {texto}
            </h2>

            <div className="flex overflow-x-auto gap-4 px-6 md:px-16 scrollbar-thin scrollbar-thumb-orange-300 scrollbar-track-gray-100">
                {ingredientesVisibles.length > 0 ? (
                    ingredientesVisibles.map((ingrediente) => (
                        <Ingrediente
                            key={ingrediente.idProducto}
                            nombre={ingrediente.nombre}
                            precio={ingrediente.precio}
                            seleccionado={ingrediente.seleccionado}
                            alternarSeleccionado={() => alternarSeleccion(ingrediente.idProducto)}
                            sinTacc={ingrediente.sinTacc}
                        />
                    ))
                ) : (
                    <p className="text-gray-500 italic ml-4">

                        {soloSinGluten
                            ? "No hay opciones sin TACC disponibles."
                            : "No hay ingredientes disponibles."}
                    </p>
                )}
            </div>
        </section>
    );
}
