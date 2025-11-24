package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoResponseDTO {
    private Long id;
    private String nombre;
    private String idCliente;
    private Long idCreacion;
    private float precio;
    private String tipo;
    private List<Map<String, Object>> ingredientes;
    private Integer cantidadCarnes;
    private String tamanio;

}


