package uy.um.edu.pizzumandburgum.mapper.Historicos;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoProductoModificacion;

@Component
public class HistoricoProductoMapper {
    public HistoricoProductoResponseDTO toResponseDTO(HistoricoProductoModificacion historico) {
        HistoricoProductoResponseDTO dto = new HistoricoProductoResponseDTO();

        dto.setId(historico.getId());
        dto.setIdProducto(historico.getProducto().getIdProducto());
        dto.setFechaModificacion(historico.getFechaModificacion());
        dto.setTipoModificiacion(historico.getTipoModificiacion());

        dto.setNombreAnterior(historico.getNombreAnterior());
        dto.setTipoAnterior(historico.getTipoAnterior());
        dto.setSinTaccAnterior(historico.isSinTaccAnterior());
        dto.setPrecioAnterior(historico.getPrecioAnterior());

        dto.setNombreNuevo(historico.getNombreNuevo());
        dto.setTipoNuevo(historico.getTipoNuevo());
        dto.setSinTaccNuevo(historico.isSinTaccNuevo());
        dto.setPrecioNuevo(historico.getPrecioNuevo());

        return dto;
    }
}
