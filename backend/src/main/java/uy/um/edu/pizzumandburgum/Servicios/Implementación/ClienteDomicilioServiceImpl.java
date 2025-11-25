package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;
import uy.um.edu.pizzumandburgum.Entidades.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.Entidades.Domicilio;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;
import uy.um.edu.pizzumandburgum.Excepciones.Domicilio.DomicilioConPedidoEnCursoException;
import uy.um.edu.pizzumandburgum.Excepciones.Domicilio.PorLoMenosUnDomicilioException;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.Mappers.DomicilioMapper;
import uy.um.edu.pizzumandburgum.Repositorios.ClienteDomicilioRepository;
import uy.um.edu.pizzumandburgum.Repositorios.ClienteRepository;
import uy.um.edu.pizzumandburgum.Repositorios.DomicilioRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.ClienteDomicilioService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteDomicilioServiceImpl implements ClienteDomicilioService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private ClienteDomicilioRepository clienteDomicilioRepository;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Override
    public void agregarDomicilio(ClienteDomicilioRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getEmail()).orElseThrow(ClienteNoExisteException::new);
        Domicilio domicilio = domicilioRepository.findById(dto.getIdDomicilio()).orElseThrow(DomicilioNoExisteException::new);

        ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
        clienteDomicilio.setCliente(cliente);
        clienteDomicilio.setDomicilio(domicilio);

        cliente.getDomicilios().add(clienteDomicilio);
        domicilio.getClientes().add(clienteDomicilio);

        clienteDomicilioRepository.save(clienteDomicilio);
    }

    @Override
    public Domicilio obtenerDomicilio(String clienteId, String direccion) {
        Cliente cliente = clienteRepository.findByEmail(clienteId)
                .orElseThrow(ClienteNoExisteException::new);

        for (ClienteDomicilio cd : cliente.getDomicilios()) {
            Domicilio domicilio = cd.getDomicilio();
            if (domicilio.getDireccion().equalsIgnoreCase(direccion)) {
                return domicilio;
            }
        }

        return domicilioRepository.findAll().stream()
                .filter(d -> d.getDireccion().equalsIgnoreCase(direccion))
                .findFirst()
                .orElseThrow(DomicilioNoExisteException::new);
    }


    @Override
    public List<DomicilioResponseDTO> listarDomiciliosDeCliente(String emailCliente) {
        Cliente cliente = clienteRepository.findById(emailCliente).orElseThrow(ClienteNoExisteException::new);

        List<DomicilioResponseDTO> domicilios = new ArrayList<>();
        for (ClienteDomicilio cd : cliente.getDomicilios()) {
            DomicilioResponseDTO dto = domicilioMapper.toResponseDTO(cd.getDomicilio());
            domicilios.add(dto);
        }

        return domicilios;
    }

    @Override
    @Transactional
    public void eliminarDomicilioDeCliente(String emailCliente, Long idDomicilio) {
        ClienteDomicilio clienteDomicilio = clienteDomicilioRepository
                .findByCliente_EmailAndDomicilio_Id(emailCliente, idDomicilio)
                .orElseThrow(DomicilioNoExisteException::new);

        long cantidadDomicilios = clienteDomicilioRepository.countByCliente_Email(emailCliente);
        if (cantidadDomicilios <= 1) {
            throw new PorLoMenosUnDomicilioException();
        }

        Domicilio domicilio = clienteDomicilio.getDomicilio();
        for (Pedido pedido : domicilio.getPedidos()) {
            if (pedido.getEstado() != null && !pedido.getEstado().equalsIgnoreCase("Entregado")) {
                throw new DomicilioConPedidoEnCursoException();
            }
        }

        clienteDomicilioRepository.delete(clienteDomicilio);
    }

    @Override
    public boolean clienteTieneDomicilio(String clienteId, Long domicilioId) {
        return clienteDomicilioRepository.existsByCliente_EmailAndDomicilio_Id(clienteId, domicilioId);
    }
}
