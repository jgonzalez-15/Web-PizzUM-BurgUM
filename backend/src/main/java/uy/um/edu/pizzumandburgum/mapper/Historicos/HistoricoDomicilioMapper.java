package uy.um.edu.pizzumandburgum.mapper.Historicos;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoDomicilioModificaciones;

@Component
public class HistoricoDomicilioMapper {
    public HistoricoDomicilioResponseDTO toResponseDTO(HistoricoDomicilioModificaciones historicoEntidad){
        HistoricoDomicilioResponseDTO historico = new HistoricoDomicilioResponseDTO();
        historico.setId(historicoEntidad.getId());
        historico.setIdDomicilio(historicoEntidad.getDomicilio().getId());
        historico.setTipoModificiacion(historicoEntidad.getTipoModificiacion());
        historico.setFechaModificacion(historicoEntidad.getFechaModificacion());
        historico.setDireccionVieja(historicoEntidad.getDireccionVieja());
        historico.setDireccionNueva(historicoEntidad.getDireccionNueva());
        return historico;
    }

}
