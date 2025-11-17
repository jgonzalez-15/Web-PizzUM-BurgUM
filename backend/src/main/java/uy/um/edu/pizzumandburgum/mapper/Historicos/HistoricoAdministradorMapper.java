package uy.um.edu.pizzumandburgum.mapper.Historicos;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoAdministradorDTO;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoAdministradorModificaciones;

@Component
public class HistoricoAdministradorMapper {
    public HistoricoAdministradorDTO toResponseDTO (HistoricoAdministradorModificaciones historicoEntidad){
        HistoricoAdministradorDTO historico = new HistoricoAdministradorDTO();
        historico.setId(historicoEntidad.getId());
        historico.setFechaModificacion(historicoEntidad.getFechaModificacion());
        historico.setEmailAdministrador(historicoEntidad.getAdministrador().getEmail());
        historico.setTipoModificiacion(historicoEntidad.getTipoModificacion());
        historico.setNombreViejo(historicoEntidad.getNombreViejo());
        historico.setApellidoViejo(historicoEntidad.getApellidoViejo());
        historico.setFechaNacVieja(historicoEntidad.getFechaNacVieja());
        historico.setContraseniaVieja(historicoEntidad.getContraseniaVieja());
        historico.setTelefonoViejo(historicoEntidad.getTelefonoViejo());
        historico.setNombreNuevo(historicoEntidad.getNombreNuevo());
        historico.setApellidoNuevo(historicoEntidad.getApellidoNuevo());
        historico.setFechaNacNueva(historicoEntidad.getFechaNacNueva());
        historico.setContraseniaNueva(historicoEntidad.getContraseniaNueva());
        historico.setTelefonoNuevo(historicoEntidad.getTelefonoNuevo());
        historico.setDomicilioViejo(historicoEntidad.getDomicilioViejo());
        historico.setDomicilioNuevo(historico.getDomicilioNuevo());
        return historico;
    }
}
