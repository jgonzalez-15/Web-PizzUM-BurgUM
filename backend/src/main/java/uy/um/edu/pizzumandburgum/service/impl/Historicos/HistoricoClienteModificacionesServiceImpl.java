package uy.um.edu.pizzumandburgum.service.impl.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoClienteModificaciones;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.Historicos.HistoricoClienteMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.Historicos.HistoricoClienteModificacionesRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoClienteModificacionesService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricoClienteModificacionesServiceImpl implements HistoricoClienteModificacionesService {

    @Autowired
    private HistoricoClienteModificacionesRepository historicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HistoricoClienteMapper historicoMapper;

    @Override
    public void registrarActualizacion(Cliente cAnterior, Cliente cNuevo) {
        Cliente nuevo = clienteRepository.findById(cNuevo.getEmail()).orElseThrow(ClienteNoExisteException::new);
        Cliente viejo = clienteRepository.findById(cAnterior.getEmail()).orElseThrow(ClienteNoExisteException::new);

        HistoricoClienteModificaciones historico = new HistoricoClienteModificaciones();

        historico.setCliente(nuevo);
        historico.setFechaModificacion(LocalDate.now());
        historico.setNombreNuevo(nuevo.getNombre());
        historico.setApellidoNuevo(nuevo.getApellido());
        historico.setTelefonoNuevo(nuevo.getTelefono());
        historico.setContraseniaNueva(nuevo.getContrasenia());
        historico.setFechaNacNueva(nuevo.getFechaNac());

        historico.setNombreViejo(viejo.getNombre());
        historico.setApellidoViejo(viejo.getApellido());
        historico.setContraseniaVieja(viejo.getContrasenia());
        historico.setTelefonoViejo(viejo.getTelefono());
        historico.setFechaNacVieja(viejo.getFechaNac());

        historico.setTipoModificiacion("Actualizacion");
        historicoRepository.save(historico);
        nuevo.getHistorico().add(historico);
    }

    @Override
    public void RegistrarAgregar(Cliente cNuevo) {
         Cliente nuevo = clienteRepository.findById(cNuevo.getEmail()).orElseThrow(ClienteNoExisteException::new);
         HistoricoClienteModificaciones historico = new HistoricoClienteModificaciones();
        historico.setCliente(nuevo);
        historico.setFechaModificacion(LocalDate.now());
        historico.setNombreNuevo(nuevo.getNombre());
        historico.setApellidoNuevo(nuevo.getApellido());
        historico.setTelefonoNuevo(nuevo.getTelefono());
        historico.setContraseniaNueva(nuevo.getContrasenia());
        historico.setFechaNacNueva(nuevo.getFechaNac());

        historico.setTipoModificiacion("Agrego");

        historicoRepository.save(historico);

        nuevo.getHistorico().add(historico);
    }

    @Override
    public List<HistoricoClienteResponseDTO> listarHistoricosPorCliente(String emailCliente) {
        Cliente cliente = clienteRepository.findById(emailCliente).orElseThrow(()-> new ClienteNoExisteException());
        List<HistoricoClienteModificaciones>lista = historicoRepository.findByClienteOrderByFechaModificacionDesc(cliente);
        List<HistoricoClienteResponseDTO> retornar = new ArrayList<>();

        for (HistoricoClienteModificaciones hc: lista){
            retornar.add(historicoMapper.toResponseDTO(hc));
        }
        return retornar;

    }

    @Override
    public List<HistoricoClienteResponseDTO> listarTodosLosHistoricos() {
        List<HistoricoClienteModificaciones>lista = historicoRepository.findAllByOrderByFechaModificacionDesc();
        List<HistoricoClienteResponseDTO> retornar = new ArrayList<>();
        for (HistoricoClienteModificaciones hc: lista){
            retornar.add(historicoMapper.toResponseDTO(hc));
        }
        return retornar;
    }

    @Override
    public List<HistoricoClienteResponseDTO> listarHistoricosPorTipo(String tipoModificacion) {
        List<HistoricoClienteModificaciones>lista = historicoRepository.findByTipoModificacionOrderByFechaModificacionDesc(tipoModificacion);
        List<HistoricoClienteResponseDTO> retornar = new ArrayList<>();
        for (HistoricoClienteModificaciones hc: lista){
            retornar.add(historicoMapper.toResponseDTO(hc));
        }
        return retornar;
    }


}
