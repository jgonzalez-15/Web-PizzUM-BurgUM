package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioConPedidoEnCursoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.ClienteDomicilioMapper;
import uy.um.edu.pizzumandburgum.mapper.DomicilioMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteDomicilioRepository;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteDomicilioService;

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
    private ClienteDomicilioMapper clienteDomicilioMapper;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Override
    public ClienteDomicilioResponseDTO agregarDomicilio(ClienteDomicilioRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getEmail()).orElseThrow(ClienteNoExisteException::new);
        Domicilio domicilio = domicilioRepository.findById(dto.getIdDomicilio()).orElseThrow(DomicilioNoExisteException::new);

        ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
        clienteDomicilio.setCliente(cliente);
        clienteDomicilio.setDomicilio(domicilio);

        cliente.getDomicilios().add(clienteDomicilio);
        domicilio.getClientes().add(clienteDomicilio);

        clienteDomicilioRepository.save(clienteDomicilio);

        return clienteDomicilioMapper.toResponseDTO(clienteDomicilio);
    }

    @Override
    public Domicilio obtenerDomicilio(String clienteId, String direccion) {
        Cliente cliente = clienteRepository.findByEmail(clienteId).orElseThrow(ClienteNoExisteException::new);
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion(direccion);
        for (ClienteDomicilio d : cliente.getDomicilios() ){
            if (d.getDomicilio().equals(domicilio.getDireccion())){
               return domicilioRepository.findById(d.getDomicilio().getDireccion()).orElseThrow(DomicilioNoExisteException::new);
            }
        }
        return null;
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
    public void eliminarDomicilioDeCliente(String emailCliente, Long idDomicilio) {
        Cliente cliente = clienteRepository.findById(emailCliente).orElseThrow(ClienteNoExisteException::new);

        Domicilio domicilio = domicilioRepository.findById(idDomicilio).orElseThrow(DomicilioNoExisteException::new);

        ClienteDomicilio clienteDomicilio = null;
        for (ClienteDomicilio cd : cliente.getDomicilios()) {
            if (cd.getDomicilio().getId().equals(idDomicilio)) {
                clienteDomicilio = cd;
                break;
            }
        }

        if (clienteDomicilio == null) {
            throw new DomicilioNoExisteException();
        }

        for (Pedido pedido : domicilio.getPedidos()) {
            if (pedido.getEstado() != null && !pedido.getEstado().equalsIgnoreCase("Entregado")) {
                throw new DomicilioConPedidoEnCursoException();
            }
        }

        cliente.getDomicilios().remove(clienteDomicilio);
        domicilio.getClientes().remove(clienteDomicilio);

        clienteRepository.save(cliente);
        domicilioRepository.save(domicilio);
    }
}
