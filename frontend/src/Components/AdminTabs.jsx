export default function AdminTabs({ selected, onSelect }) {
    const tabs = [
        { id: "admins", label: "Administradores" },
        { id: "products", label: "Productos" },
        { id: "orders", label: "Pedidos" },
    ];

    return (
        <div className="flex justify-center gap-8 border-b border-gray-300 w-full max-w-3xl mt-4">
            {tabs.map(tab => (
                <button
                    key={tab.id}
                    onClick={() => onSelect(tab.id)}
                    className={`relative pb-2 text-lg font-semibold transition-all 
                        ${selected === tab.id ? "text-orange-500" : "text-gray-500 hover:text-gray-700"}`}
                >
                    {tab.label}
                    {selected === tab.id && (
                        <span className="absolute left-0 bottom-0 w-full h-[3px] bg-orange-500 rounded-full"></span>
                    )}
                </button>
            ))}
        </div>
    );
}
