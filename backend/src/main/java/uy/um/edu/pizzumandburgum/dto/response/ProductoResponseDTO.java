package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {
    private Long idProducto;
    private String tipo;
    private String nombre;
    private boolean sinTacc;
    private Float precio;
    private boolean estaActivo;
    private boolean visible;

}
