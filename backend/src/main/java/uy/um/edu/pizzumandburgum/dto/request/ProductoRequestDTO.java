package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {
    private Long idProducto;
    private String tipo;
    private String nombre;
    private boolean sinTacc;
    private float precio;
}
