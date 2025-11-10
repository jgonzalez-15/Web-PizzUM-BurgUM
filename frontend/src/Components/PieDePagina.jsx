export default function PieDePagina() {
    return (
        <footer className="bg-gray-100 text-gray-700 w-full border-t border-gray-300 shadow-inner">
            <div className="max-w-6xl mx-auto flex flex-col md:flex-row justify-between items-center md:items-start gap-6 py-8 px-6 text-center md:text-left">

                {/* Sección "Sobre nosotros" */}
                <div>
                    <h3 className="font-bold text-lg mb-2 text-gray-800">Sobre nosotros</h3>
                    <p className="text-sm text-gray-600 max-w-xs">
                        En <span className="font-semibold text-orange-500">PizzUM & BurgUM </span>
                        trabajamos para ofrecerte la mejor experiencia gastronómica, combinando calidad, sabor y atención.
                    </p>
                </div>

                {/* Sección "Contacto" */}
                <div>
                    <h3 className="font-bold text-lg mb-2 text-gray-800">Contacto</h3>
                    <p className="text-sm text-gray-600">📞 27074461</p>
                    <p className="text-sm text-gray-600">📧 info@p&b.uy</p>
                    <p className="text-sm text-gray-600">📍 Montevideo, Uruguay</p>
                </div>

                {/* Sección de derechos */}
                <div className="text-sm text-gray-500 md:text-right">
                    <p className="font-semibold text-gray-700">Pizz<span className="text-orange-500">UM</span> & Burg<span className="text-orange-500">UM</span></p>
                    <p>© {new Date().getFullYear()} Todos los derechos reservados.</p>
                </div>
            </div>
        </footer>
    );
}
