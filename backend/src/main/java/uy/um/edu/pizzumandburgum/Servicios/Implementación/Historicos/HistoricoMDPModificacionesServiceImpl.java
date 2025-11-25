package uy.um.edu.pizzumandburgum.Servicios.Implementación.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.Entidades.Historicos.HistoricoMDPModificaciones;
import uy.um.edu.pizzumandburgum.Entidades.MedioDePago;
import uy.um.edu.pizzumandburgum.Excepciones.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.Repositorios.Historicos.HistoricoMDPModificacionesRepository;
import uy.um.edu.pizzumandburgum.Repositorios.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos.HistoricoMDPModificacionesService;

import java.time.LocalDate;

@Service
public class HistoricoMDPModificacionesServiceImpl implements HistoricoMDPModificacionesService {

    @Autowired
    private HistoricoMDPModificacionesRepository historicoRepository;

    @Autowired
    private MedioDePagoRepository mdpRepository;

    @Override
    public void registrarActualizacion(MedioDePago mdpAnterior, MedioDePago mdpNuevo) {
        MedioDePago viejo = mdpRepository.findById(mdpAnterior.getId()).orElseThrow(MedioDePagoNoExisteException::new);
        MedioDePago nuevo = mdpRepository.findById(mdpNuevo.getId()).orElseThrow(MedioDePagoNoExisteException::new);

        HistoricoMDPModificaciones historico = new HistoricoMDPModificaciones();

        historico.setFechaModificacion(LocalDate.now());
        historico.setMedioDePago(nuevo);
        historico.setNombreTitularNuevo(nuevo.getNombreTitular());
        historico.setNumeroNuevo(nuevo.getNumeroTarjeta());
        historico.setVencimientoNuevo(nuevo.getFechaVencimiento());
        historico.setVencimientoViejo(viejo.getFechaVencimiento());
        historico.setNombreTitularViejo(viejo.getNombreTitular());
        historico.setNumeroViejo(viejo.getNumeroTarjeta());
        historico.setTipoModificiacion("Actualizacion");
        historicoRepository.save(historico);
        nuevo.getHistorico().add(historico);
    }

    @Override
    public void RegistrarAgregar(MedioDePago mdpNuevo) {
        MedioDePago nuevo = mdpRepository.findById(mdpNuevo.getId()).orElseThrow(MedioDePagoNoExisteException::new);
        HistoricoMDPModificaciones historico = new HistoricoMDPModificaciones();

        historico.setFechaModificacion(LocalDate.now());
        historico.setMedioDePago(nuevo);
        historico.setNombreTitularNuevo(nuevo.getNombreTitular());
        historico.setNumeroNuevo(nuevo.getNumeroTarjeta());
        historico.setVencimientoNuevo(nuevo.getFechaVencimiento());

        historico.setTipoModificiacion("Agrego");
        historicoRepository.save(historico);
        nuevo.getHistorico().add(historico);
    }

    @Override
    public void RegistrarEliminar(MedioDePago mdpViejo) {
        MedioDePago viejo = mdpRepository.findById(mdpViejo.getId()).orElseThrow(MedioDePagoNoExisteException::new);
        HistoricoMDPModificaciones historico = new HistoricoMDPModificaciones();
        historico.setFechaModificacion(LocalDate.now());
        historico.setVencimientoViejo(viejo.getFechaVencimiento());
        historico.setNombreTitularViejo(viejo.getNombreTitular());
        historico.setNumeroViejo(viejo.getNumeroTarjeta());
        historico.setTipoModificiacion("Elimino");
        historico.setMedioDePago(viejo);
        historicoRepository.save(historico);
        viejo.getHistorico().add(historico);


    }
}
