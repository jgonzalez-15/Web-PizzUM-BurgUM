package uy.um.edu.pizzumandburgum.service.impl;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.DomicilioUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoDomicilioModificaciones;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.SinAccesoAlDomicilioException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.UnicoDomicilioException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.DomicilioMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.repository.Historicos.HistoricoDomicilioModificacionesRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteDomicilioService;
import uy.um.edu.pizzumandburgum.service.Interfaces.DomicilioService;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoDomicilioModificacionesService;

import java.util.List;

@Service
public class DomicilioServiceImpl implements DomicilioService {
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Autowired
    private HistoricoDomicilioModificacionesService historicoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteDomicilioService clienteDomicilioService;

    @Override
    public DomicilioResponseDTO crearDomicilio(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion(dto.getDireccion());
        Domicilio guardado = domicilioRepository.save(domicilio);
        historicoService.RegistrarAgregar(domicilio);
        return domicilioMapper.toResponseDTO(guardado);
    }

    @Override
    public DomicilioResponseDTO editarPerfil(Long idDomicilio, DomicilioUpdateDTO dto) {
        Domicilio viejo = domicilioRepository.findById(idDomicilio).orElseThrow(DomicilioNoExisteException::new);
        Domicilio domicilio = domicilioRepository.findById(idDomicilio).orElseThrow(DomicilioNoExisteException::new);
        if (dto.getDireccion() != null){
            domicilio.setDireccion(dto.getDireccion());
        }
        domicilio.setHistorico(viejo.getHistorico());
        domicilioRepository.save(domicilio);
        historicoService.registrarActualizacion(viejo,domicilio);
        return domicilioMapper.toResponseDTO(domicilio);
    }

    @Transactional
    @Override
    public void eliminarDomicilio(Long domicilioId, String clienteId) {
        Domicilio domicilio = domicilioRepository.findById(domicilioId).orElseThrow(DomicilioNoExisteException::new);

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNoExisteException::new);


        if (!clienteDomicilioService.clienteTieneDomicilio(clienteId, domicilioId)) {
            throw new SinAccesoAlDomicilioException();
        }

        long cantidadDomicilios = domicilioRepository.countByClienteId(clienteId);
        if (cantidadDomicilios <= 1) {
            throw new UnicoDomicilioException();
        }
        historicoService.RegistrarEliminar(domicilio);

        clienteDomicilioService.eliminarDomicilioDeCliente(clienteId,domicilioId);
    }
}
