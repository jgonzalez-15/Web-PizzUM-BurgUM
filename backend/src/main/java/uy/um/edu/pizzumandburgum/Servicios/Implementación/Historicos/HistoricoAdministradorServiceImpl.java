package uy.um.edu.pizzumandburgum.Servicios.Implementación.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Response.Historicos.HistoricoAdministradorDTO;
import uy.um.edu.pizzumandburgum.Entidades.Administrador;
import uy.um.edu.pizzumandburgum.Entidades.Historicos.HistoricoAdministradorModificaciones;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Administrador.AdministradorNoExiste;
import uy.um.edu.pizzumandburgum.Mappers.Historicos.HistoricoAdministradorMapper;
import uy.um.edu.pizzumandburgum.Repositorios.AdministradorRepository;
import uy.um.edu.pizzumandburgum.Repositorios.Historicos.HistoricoAdministradorRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos.HistoricoAdministradorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricoAdministradorServiceImpl implements HistoricoAdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private HistoricoAdministradorRepository historicoRepository;

    @Autowired
    private HistoricoAdministradorMapper historicoMapper;

    @Override
    public void registrarActualizacion(Administrador aAnterior, Administrador aNuevo) {
        Administrador nuevo = administradorRepository.findById(aNuevo.getEmail()).orElseThrow(AdministradorNoExiste::new);
        Administrador viejo = administradorRepository.findById(aAnterior.getEmail()).orElseThrow(AdministradorNoExiste::new);

        HistoricoAdministradorModificaciones historico = new HistoricoAdministradorModificaciones();

        historico.setAdministrador(nuevo);
        historico.setFechaModificacion(LocalDate.now());
        historico.setNombreNuevo(nuevo.getNombre());
        historico.setApellidoNuevo(nuevo.getApellido());
        historico.setTelefonoNuevo(nuevo.getTelefono());
        historico.setContraseniaNueva(nuevo.getContrasenia());
        historico.setFechaNacNueva(nuevo.getFechaNac());
        historico.setDomicilioNuevo(nuevo.getDomicilio().getDireccion());

        historico.setNombreViejo(viejo.getNombre());
        historico.setApellidoViejo(viejo.getApellido());
        historico.setContraseniaVieja(viejo.getContrasenia());
        historico.setTelefonoViejo(viejo.getTelefono());
        historico.setFechaNacVieja(viejo.getFechaNac());
        historico.setDomicilioViejo(viejo.getDomicilio().getDireccion());

        historico.setTipoModificacion("Actualizacion");
        historicoRepository.save(historico);
        nuevo.getHistorico().add(historico);
    }

    @Override
    public void RegistrarAgregar(Administrador aNuevo) {
        Administrador nuevo = administradorRepository.findById(aNuevo.getEmail()).orElseThrow(AdministradorNoExiste::new);
        HistoricoAdministradorModificaciones historico = new HistoricoAdministradorModificaciones();
        historico.setAdministrador(nuevo);
        historico.setFechaModificacion(LocalDate.now());
        historico.setNombreNuevo(nuevo.getNombre());
        historico.setApellidoNuevo(nuevo.getApellido());
        historico.setTelefonoNuevo(nuevo.getTelefono());
        historico.setContraseniaNueva(nuevo.getContrasenia());
        historico.setFechaNacNueva(nuevo.getFechaNac());
        historico.setDomicilioNuevo(nuevo.getDomicilio().getDireccion());
        historico.setTipoModificacion("Agrego");
        historicoRepository.save(historico);
        nuevo.getHistorico().add(historico);
    }

    @Override
    public void RegistrarEliminar(Administrador aViejo) {
        Administrador viejo = administradorRepository.findById(aViejo.getEmail()).orElseThrow(AdministradorNoExiste::new);
        HistoricoAdministradorModificaciones historico = new HistoricoAdministradorModificaciones();
        historico.setAdministrador(viejo);
        historico.setFechaModificacion(LocalDate.now());
        historico.setNombreViejo(viejo.getNombre());
        historico.setApellidoViejo(viejo.getApellido());
        historico.setContraseniaVieja(viejo.getContrasenia());
        historico.setTelefonoViejo(viejo.getTelefono());
        historico.setFechaNacVieja(viejo.getFechaNac());
        historico.setDomicilioViejo(viejo.getDomicilio().getDireccion());

        historico.setTipoModificacion("Eliminacion");
        historicoRepository.save(historico);
        viejo.getHistorico().add(historico);
    }

    @Override
    public List<HistoricoAdministradorDTO> listarHistoricosPorAdministrador(String emailAdministrador) {
        Administrador administrador = administradorRepository.findById(emailAdministrador).orElseThrow(()-> new AdministradorNoExiste());
        List<HistoricoAdministradorModificaciones>lista = historicoRepository.findByAdministradorOrderByFechaModificacionDesc(administrador);
        List<HistoricoAdministradorDTO> retornar = new ArrayList<>();

        for (HistoricoAdministradorModificaciones ha: lista){
            retornar.add(historicoMapper.toResponseDTO(ha));
        }
        return retornar;
    }

    @Override
    public List<HistoricoAdministradorDTO> listarTodosLosHistoricos() {
        List<HistoricoAdministradorModificaciones>lista = historicoRepository.findAllByOrderByFechaModificacionDesc();
        List<HistoricoAdministradorDTO> retornar = new ArrayList<>();
        for (HistoricoAdministradorModificaciones ha: lista){
            retornar.add(historicoMapper.toResponseDTO(ha));
        }
        return retornar;
    }

    @Override
    public List<HistoricoAdministradorDTO> listarHistoricosPorTipo(String tipoModificacion) {
        List<HistoricoAdministradorModificaciones>lista = historicoRepository.findByTipoModificacionOrderByFechaModificacionDesc(tipoModificacion);
        List<HistoricoAdministradorDTO> retornar = new ArrayList<>();
        for (HistoricoAdministradorModificaciones ha: lista){
            retornar.add(historicoMapper.toResponseDTO(ha));
        }
        return retornar;
    }
}
