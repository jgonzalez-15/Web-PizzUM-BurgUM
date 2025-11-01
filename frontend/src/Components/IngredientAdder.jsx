import { useContext } from "react";
import { DesignContext } from "./context/DesignContext";

import Ingredient from "./Ingredient"

export default function IngredientAdder({ text, maxCount, allIngredients, setIngredients, tipo }) {
  const { glutenFreeOnly } = useContext(DesignContext);

  const filtered = allIngredients.filter(i => i.tipo === tipo);
  const filterGluten = glutenFreeOnly ? filtered.filter(i => i.sinTacc) : filtered;

  function toggleSelected(idProducto) {
    setIngredients(prev =>
      prev.map(ingred => {
        if (ingred.tipo !== tipo) return ingred;
        if (maxCount === 1) {
          return { ...ingred, selected: ingred.idProducto === idProducto };
        } else {
          return ingred.idProducto === idProducto
            ? { ...ingred, selected: !ingred.selected }
            : ingred;
        }
      })
    );
  }

  return (
    <div className="flex justify-center flex-col max-w-full mb-8">
      <div className="flex ml-16">
        <h2 className="font-bold text-xl">{text}</h2>
      </div>
      <div className="flex overflow-x-auto ml-16 mr-16">
        {filterGluten.map(ingredient => (
          <Ingredient
            key={ingredient.idProducto}
            nombre={ingredient.nombre}
            selected={ingredient.selected}
            precio={ingredient.precio}
            toggleSelected={() => toggleSelected(ingredient.idProducto)}
          />
        ))}
      </div>
    </div>
  );
}