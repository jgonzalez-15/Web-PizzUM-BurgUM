package uy.um.edu.pizzumandburgum.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HamburguesaResponseDTO {
    private long idCreacion;
    private int cantCarnes;
    private float precio;
    private List<HamburguesaProductoResponseDTO> ingredientes;
    private ClienteResponseDTO cliente;

}
