package uy.um.edu.pizzumandburgum.mapper.Historicos;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoClienteModificaciones;

@Component
public class HistoricoClienteMapper {
    public HistoricoClienteResponseDTO toResponseDTO (HistoricoClienteModificaciones historicoEntidad){
        HistoricoClienteResponseDTO historico = new HistoricoClienteResponseDTO();
        historico.setId(historicoEntidad.getId());
        historico.setFechaModificacion(historicoEntidad.getFechaModificacion());
        historico.setEmailCliente(historicoEntidad.getCliente().getEmail());
        historico.setTipoModificiacion(historicoEntidad.getTipoModificiacion());
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
        return historico;
    }
}
