export default function Ingredient({ nombre, precio, selected, toggleSelected }) {
  const handleClick = (e) => {
    e.preventDefault();
    toggleSelected();
  };

  return (
    <div className="m-2 min-w-24 min-h-24 md:min-w-48 md:min-h-48 flex items-center justify-center">
      <div
        className={`h-24 w-24 md:h-48 md:w-48 bg-gray-100 rounded-2xl flex flex-col justify-between
          items-center ${selected ? "border-orange-300 border-4" : ""} cursor-pointer`}
        onClick={handleClick}
      >
        <div className="h-full items-center flex m-2">
          <h1 className="font-bold text-xs md:text-xl text-center">{nombre}</h1>
        </div>
        <div className="m-2">
          <h1 className="text-sm md:text-xl text-center">${precio}</h1>
        </div>
      </div>
    </div>
  );
}