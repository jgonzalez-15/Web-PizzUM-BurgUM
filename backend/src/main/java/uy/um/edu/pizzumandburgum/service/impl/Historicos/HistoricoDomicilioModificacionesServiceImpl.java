package uy.um.edu.pizzumandburgum.service.impl.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoDomicilioModificaciones;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.repository.Historicos.HistoricoDomicilioModificacionesRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoDomicilioModificacionesService;

import java.time.LocalDate;

@Service
public class HistoricoDomicilioModificacionesServiceImpl implements HistoricoDomicilioModificacionesService {

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private HistoricoDomicilioModificacionesRepository historicoRepository;

    @Override
    public void registrarActualizacion(Domicilio dAnterior, Domicilio dNuevo) {
        Domicilio nuevo = domicilioRepository.findById(dNuevo.getId()).orElseThrow(DomicilioNoExisteException::new);
        Domicilio viejo =  domicilioRepository.findById(dAnterior.getId()).orElseThrow(DomicilioNoExisteException::new);
        HistoricoDomicilioModificaciones historicoDomicilioModificaciones = new HistoricoDomicilioModificaciones();

        historicoDomicilioModificaciones.setDomicilio(nuevo);
        historicoDomicilioModificaciones.setFechaModificacion(LocalDate.now());
        historicoDomicilioModificaciones.setDireccionNueva(nuevo.getDireccion());
        historicoDomicilioModificaciones.setDireccionVieja(viejo.getDireccion());
        historicoDomicilioModificaciones.setTipoModificiacion("Actualizacion");

        historicoRepository.save(historicoDomicilioModificaciones);
        nuevo.getHistorico().add(historicoDomicilioModificaciones);

    }

    @Override
    public void RegistrarAgregar(Domicilio dNuevo) {
        Domicilio nuevo = domicilioRepository.findById(dNuevo.getId()).orElseThrow(DomicilioNoExisteException::new);
        HistoricoDomicilioModificaciones historicoDomicilioModificaciones = new HistoricoDomicilioModificaciones();
        historicoDomicilioModificaciones.setDomicilio(nuevo);
        historicoDomicilioModificaciones.setFechaModificacion(LocalDate.now());
        historicoDomicilioModificaciones.setDireccionNueva(nuevo.getDireccion());
        historicoDomicilioModificaciones.setTipoModificiacion("Agregar");

        historicoRepository.save(historicoDomicilioModificaciones);
        nuevo.getHistorico().add(historicoDomicilioModificaciones);
    }

    @Override
    public void RegistrarEliminar(Domicilio dViejo) {
        Domicilio viejo =  domicilioRepository.findById(dViejo.getId()).orElseThrow(DomicilioNoExisteException::new);
        HistoricoDomicilioModificaciones historicoDomicilioModificaciones = new HistoricoDomicilioModificaciones();
        historicoDomicilioModificaciones.setDomicilio(viejo);
        historicoDomicilioModificaciones.setFechaModificacion(LocalDate.now());
        historicoDomicilioModificaciones.setDireccionVieja(viejo.getDireccion());
        historicoDomicilioModificaciones.setTipoModificiacion("Eliminar");

        historicoRepository.save(historicoDomicilioModificaciones);
        viejo.getHistorico().add(historicoDomicilioModificaciones);
    }
}
