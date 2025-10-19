package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.Pizza;

@Component
public class PizzaMapper {
    public Pizza toEntity(PizzaResponseDTO dto) {
        Pizza pizza = new Pizza();
        pizza.setCliente(dto.getCliente());
        pizza.setTamanio(dto.getTamanio());
        pizza.setPrecio(dto.getPrecio());
        pizza.setEsFavorita(dto.isEsFavorita());
        pizza.setCreacionesPedido(dto.getCreacionesPedido());
        pizza.setIngredientes(dto.getIngredientes());
        return pizza;
    }

    public PizzaResponseDTO toResponseDTO(Pizza pizza) {
        return new PizzaResponseDTO(pizza.getPrecio(),pizza.isEsFavorita(),pizza.getTamanio(),pizza.getCreacionesPedido(),pizza.getCliente(),pizza.getIngredientes());
    }
}
