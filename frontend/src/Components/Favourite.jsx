function Favourite({title}){
    return(
        <div className="m-2 w-24 md:w-48">
            <div className="h-24 w-24 md:h-48 md:w-48 bg-gray-100 rounded-2xl">
            </div>
            <h2 className="font-bold mt-2 ml-2 mr-2 truncate">
                {title}
            </h2>
        </div>
    )
}

export default Favourite