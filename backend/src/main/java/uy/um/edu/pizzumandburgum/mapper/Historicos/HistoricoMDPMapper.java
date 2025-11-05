package uy.um.edu.pizzumandburgum.mapper.Historicos;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoMDPRepsonseDTO;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoMDPModificaciones;

@Component
public class HistoricoMDPMapper {

    public HistoricoMDPRepsonseDTO toResponseDTO(HistoricoMDPModificaciones historico) {
        HistoricoMDPRepsonseDTO dto = new HistoricoMDPRepsonseDTO();

        dto.setId(historico.getId());
        dto.setIdMDP(historico.getMedioDePago().getId());
        dto.setFechaModificacion(historico.getFechaModificacion());
        dto.setTipoModificiacion(historico.getTipoModificiacion());

        dto.setNumeroViejo(historico.getNumeroViejo());
        dto.setVencimientoViejo(historico.getVencimientoViejo());
        dto.setDireccionViejo(historico.getDireccionViejo());

        dto.setNumeroNuevo(historico.getNumeroNuevo());
        dto.setVencimientoNuevo(historico.getVencimientoNuevo());
        dto.setDireccionNueva(historico.getDireccionNueva());

        return dto;
    }
}
