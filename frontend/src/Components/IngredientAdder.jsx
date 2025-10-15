import { useContext, useState } from "react";
import Ingredient from "./Ingredient";
import { DesignContext } from "./context/DesignContext";
import { useEffect } from "react";

function IngredientAdder({ text, maxCount, ing}) {
  const [ingredients, setIngredients] = useState(ing);
  const {glutenFreeOnly} = useContext(DesignContext)
  const filterGluten = glutenFreeOnly ? ingredients.filter((i)=> i.glutenFree) : ingredients

  function toggleSelected(id) {
    setIngredients((prev) =>
      prev.map((ingred) => {
        if (maxCount === 1) {
          return { ...ingred, selected: ingred.id === id };
        } else {
          return ingred.id === id
            ? { ...ingred, selected: !ingred.selected }
            : ingred;
        }
      })
    );
  }

  useEffect(() => {
    setIngredients((prev) =>
      prev.map((ingred) => ({ ...ingred, selected: false }))
    );
  }, [glutenFreeOnly]);

  return (
    <div className="flex justify-center flex-col max-w-full mb-8">
      <div className="flex ml-16">
        <h2 className="font-bold text-xl">{text}</h2>
      </div>
      <div className="flex overflow-x-auto ml-16 mr-16">
        {filterGluten.map((ingredient) => (
          <Ingredient
            key={ingredient.id}
            id={ingredient.id}
            name={ingredient.name}
            selected={ingredient.selected}
            toggleSelected={() => toggleSelected(ingredient.id)}
          />
        ))}
      </div>
    </div>
  );
}

export default IngredientAdder;
