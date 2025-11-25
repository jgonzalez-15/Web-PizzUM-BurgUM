package uy.um.edu.pizzumandburgum.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.DTOs.Response.CreacionResponseDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreacionRequestDTO {
    private CreacionResponseDTO creacion;
    private int cantidad;
}
