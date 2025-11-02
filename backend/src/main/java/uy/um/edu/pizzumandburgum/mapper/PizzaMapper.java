package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PizzaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.PizzaProducto;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class PizzaMapper {

    @Autowired
    private PizzaProductoMapper pizzaProductoMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    public Pizza toEntity(PizzaRequestDTO dto) {
        Pizza pizza = new Pizza();
        Cliente cliente = clienteRepository.findByEmail(dto.getClienteId()).orElseThrow(ClienteNoExisteException::new);
        pizza.setCliente(cliente);
        pizza.setTamanio(dto.getTamanio());
        List<PizzaProducto>pizzaProductoList = new ArrayList<>();
        for (PizzaProductoRequestDTO pp: dto.getIngredientes()){
            PizzaProducto pizzaProducto = pizzaProductoMapper.toEntity(pp);
            pizzaProductoList.add(pizzaProducto);
        }
        pizza.setIngredientes(pizzaProductoList);
        return pizza;
    }

    public PizzaResponseDTO toResponseDTO(Pizza pizza) {
        ClienteResponseDTO clienteDTO = null;
        if (pizza.getCliente() != null) {
            clienteDTO = clienteMapper.toResponseDTO(pizza.getCliente());
        }
        List<PizzaProductoResponseDTO> ingredientesDTO = new ArrayList<>();
        if (pizza.getIngredientes() != null) {
            for (PizzaProducto pp : pizza.getIngredientes()) {
                ingredientesDTO.add(pizzaProductoMapper.toResponseDTO(pp));
            }
        }
        return new PizzaResponseDTO(pizza.getId(), pizza.getPrecio(),pizza.getTamanio(),clienteDTO,ingredientesDTO);
    }
}
