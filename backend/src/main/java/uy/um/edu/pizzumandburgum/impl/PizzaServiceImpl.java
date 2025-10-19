package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.*;
import uy.um.edu.pizzumandburgum.mapper.PizzaMapper;
import uy.um.edu.pizzumandburgum.repository.PizzaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.PizzaProductoService;
import uy.um.edu.pizzumandburgum.service.PizzaService;

@Service
public class PizzaServiceImpl implements PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaProductoService pizzaProductoService;

    @Autowired
    private PizzaMapper pizzaMapper;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public PizzaResponseDTO crearPizza(PizzaResponseDTO pizza) {

        boolean tieneMasa = false;

        for (PizzaProducto pp: pizza.getIngredientes()){
            Producto p = pp.getProducto();
            if ("Masa".equalsIgnoreCase(p.getTipo())){
                tieneMasa = true;
            }
        }
        if (!tieneMasa){
            throw new SinMasaException();
        }
        Pizza nuevo = pizzaMapper.toEntity(pizza);
        float precio = this.fijarPrecio(nuevo.getId_creacion());
        nuevo.setPrecio(precio);
        Pizza guardado = pizzaRepository.save(nuevo);

        // Delegamos la creación de ingredientes al service especializado
        for (PizzaProducto pp : pizza.getIngredientes()) {
            Producto producto = productoRepository.findById(pp.getProducto().getIdProducto()).orElseThrow(()->new ProductoNoExisteException());
            pizzaProductoService.agregarIngrediente(
                    guardado,
                    pp.getProducto(),
                    pp.getCantidad()
            );
        }
        return pizzaMapper.toResponseDTO(guardado);
    }

    @Override
    public float fijarPrecio(Long idPizza) {
        Pizza pizza = pizzaRepository.findById(String.valueOf(idPizza))
                .orElseThrow(() -> new PizzaNoExisteException());

        PizzaResponseDTO pizzaDTO = pizzaMapper.toResponseDTO(pizza);
        float precioTotal = pizzaProductoService.calcularPrecio(pizzaDTO);
        pizza.setPrecio(precioTotal);
        pizzaRepository.save(pizza);

        return precioTotal;
    }
}
