package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PizzaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.PizzaNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.SinMasaException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.SinSalsaException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.SinTamanioException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.PizzaMapper;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.PizzaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.PizzaProductoService;
import uy.um.edu.pizzumandburgum.service.Interfaces.PizzaService;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public PizzaResponseDTO crearPizza(PizzaRequestDTO dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.getClienteId()).orElseThrow(ClienteNoExisteException::new);

        Pizza pizza = new Pizza();
        boolean tieneTamanio = false;
        boolean tieneMasa = false;
        boolean tieneSalsa = false;

        for (PizzaProductoRequestDTO ppdto : dto.getIngredientes()) {
            Producto producto = productoRepository.findById(ppdto.getIdProducto()).orElseThrow(ProductoNoExisteException::new);
            if ("Tamanio".equals(producto.getTipo())) {
                tieneTamanio = true;
            } if ("Masa".equalsIgnoreCase(producto.getTipo())) {
                tieneMasa = true;
            } else if ("Salsa".equalsIgnoreCase(producto.getTipo())) {
                tieneSalsa = true;
            }
        }

        if (!tieneTamanio) {
            throw new SinTamanioException();
        }

        if (!tieneMasa) {
            throw new SinMasaException();
        }

        if (!tieneSalsa) {
            throw new SinSalsaException();
        }

        pizza.setCliente(cliente);
        Pizza guardado = pizzaRepository.save(pizza);

        for (PizzaProductoRequestDTO ppdto : dto.getIngredientes()) {
            pizzaProductoService.agregarIngrediente(
                    guardado.getId(),
                    ppdto
            );
        }
        Pizza temporal = pizzaRepository.save(guardado);
        float precio = this.fijarPrecio(temporal.getId());
        guardado.setPrecio(precio);
        pizzaRepository.save(temporal);

        Pizza pizzaActualizada = pizzaRepository.findByIdConIngredientes(temporal.getId())
                .orElseThrow(() -> new PizzaNoExisteException());

        return pizzaMapper.toResponseDTO(pizzaActualizada);
    }


    @Override
    public float fijarPrecio(Long idPizza) {
        Pizza pizza = pizzaRepository.findByIdConIngredientes(idPizza).orElseThrow(PizzaNoExisteException::new);

        float precioTotal = pizzaProductoService.calcularPrecio(pizza.getId());
        pizza.setPrecio(precioTotal);
        pizzaRepository.save(pizza);

        return precioTotal;
    }


    @Override
    public List<PizzaResponseDTO> listarPizzas() {
        List<Pizza>pizzas = pizzaRepository.findAll();
        List<PizzaResponseDTO> retornar = new ArrayList<>();

        for(Pizza pizza: pizzas){
            retornar.add(pizzaMapper.toResponseDTO(pizza));
        }
        return retornar;
    }

    @Override
    public List<ProductoResponseDTO> obtenerIngredientesPizza(Long idCreacion) {
        Pizza pizza = pizzaRepository.findById(idCreacion).orElseThrow(HamburguesaNoEncontradaException::new);
        List<PizzaProducto> ingredienteshp = pizza.getIngredientes();
        List<Producto>ingredientes = new ArrayList<>();
        List<ProductoResponseDTO> listaRetornar = new ArrayList<>();
        for (PizzaProducto pp: ingredienteshp){
            ingredientes.add(pp.getProducto());
        }
        for (Producto producto: ingredientes){
            ProductoResponseDTO retornar = productoMapper.toResponseDTO(producto);
            listaRetornar.add(retornar);
        }
        return listaRetornar;
    }
}
