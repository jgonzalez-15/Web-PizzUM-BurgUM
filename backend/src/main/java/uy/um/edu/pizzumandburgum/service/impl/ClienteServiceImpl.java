package uy.um.edu.pizzumandburgum.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.EmailYaRegistradoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.UsuarioNoEncontradoException;
import uy.um.edu.pizzumandburgum.mapper.ClienteMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.repository.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteService;
import uy.um.edu.pizzumandburgum.service.Interfaces.PedidoService;

import java.util.List;
import java.util.Objects;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MedioDePagoRepository medioDePagoRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;
    @Override
    public ClienteResponseDTO registrarCliente(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new EmailYaRegistradoException();
        }

        if (dto.getContrasenia().length() < 8){
            throw new ContraseniaInvalidaException();
        }
        // Convertir DTO → Entity
        Cliente nuevo = clienteMapper.toEntity(dto);

        // Guardar en la base
        Cliente guardado = clienteRepository.save(nuevo);

        // Convertir Entity → DTO de respuesta
        return clienteMapper.toResponseDTO(guardado);
    }

    @Override
    public ClienteResponseDTO login(String email, String contrasenia) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(()->new ClienteNoExisteException());

        if (!Objects.equals(cliente.getContrasenia(), contrasenia)){
            throw new ContraseniaInvalidaException();
        }
        return new ClienteResponseDTO(
                cliente.getEmail(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getTelefono(),
                cliente.getFechaNac()
        );

    }

    @Override
    public HamburguesaResponseDTO diseñarHamburguesa(HamburguesaResponseDTO hamburguesaResponseDTO) {
        return null;
    }

    @Override
    public PedidoResponseDTO realizarPedido(PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteAsignado().getEmail()).orElseThrow(()->new UsuarioNoEncontradoException());
        MedioDePago medioDePago = medioDePagoRepository.findById(pedidoRequestDTO.getMedioDePago().getNumero()).orElseThrow(() -> new MedioDePagoNoExisteException());
        Domicilio domicilio = domicilioRepository.findById(pedidoRequestDTO.getDomicilio().getId()).orElseThrow(() -> new DomicilioNoExisteException());
        return pedidoService.realizarPedido(pedidoRequestDTO.getClienteAsignado().getEmail(),pedidoRequestDTO.getDomicilio().getId(),pedidoRequestDTO.getIdPedido(),pedidoRequestDTO.getMedioDePago().getNumero());

    }

    @Override
    public List<Pedido> historialPedido(String email) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(() -> new ClienteNoExisteException());
        return cliente.getPedidos();
    }


}
