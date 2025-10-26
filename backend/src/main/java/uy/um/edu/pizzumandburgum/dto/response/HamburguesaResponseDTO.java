package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HamburguesaResponseDTO {
    private long idCreacion;
    private int cantCarnes;
    private float precio;
    private boolean esFavorita;
    private List<HamburguesaProductoResponseDTO> ingredientes;

}
