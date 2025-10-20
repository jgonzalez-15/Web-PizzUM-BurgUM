package uy.um.edu.pizzumandburgum.dto.response;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.Producto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaProductoResponseDTO {
    private int cantidad; // número de unidades de ese producto
    private PizzaResponseDTO pizza;
    private ProductoDTO producto;
}
