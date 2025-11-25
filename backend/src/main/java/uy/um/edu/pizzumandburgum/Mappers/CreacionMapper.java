package uy.um.edu.pizzumandburgum.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.DTOs.Response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreacionMapper {

    @Autowired
    private ProductoMapper productoMapper;

    public CreacionResponseDTO toResponseDTO(Creacion creacion) {
        if (creacion == null) {
            return null;
        }

        List<ProductoResponseDTO> ingredientes = new ArrayList<>();
        String tipo = "";

        if (creacion instanceof Pizza) {
            Pizza pizza = (Pizza) creacion;
            tipo = "Pizza";
            for (PizzaProducto pp : pizza.getIngredientes()) {
                ingredientes.add(productoMapper.toResponseDTO(pp.getProducto()));
            }
        } else if (creacion instanceof Hamburguesa) {
            Hamburguesa hamburguesa = (Hamburguesa) creacion;
            tipo = "Hamburguesa";
            for (HamburguesaProducto hp : hamburguesa.getIngredientes()) {
                ingredientes.add(productoMapper.toResponseDTO(hp.getProducto()));
            }
        }

        return new CreacionResponseDTO(creacion.getId(), creacion.getPrecio(), tipo, ingredientes);
    }
}

