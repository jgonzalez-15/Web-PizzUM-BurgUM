package uy.um.edu.pizzumandburgum.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.*;
import uy.um.edu.pizzumandburgum.dto.update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.PizzaNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.EmailNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.EmailYaRegistradoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.UsuarioNoEncontradoException;
import uy.um.edu.pizzumandburgum.mapper.*;
import uy.um.edu.pizzumandburgum.repository.*;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteService;
import uy.um.edu.pizzumandburgum.service.Interfaces.PedidoService;

import java.util.ArrayList;
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

    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    @Autowired
    private HamburguesaMapper hamburguesaMapper;

    @Autowired
    private CreacionMapper creacionMapper;

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private  PizzaMapper pizzaMapper;
    @Override
    public ClienteResponseDTO registrarCliente(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new EmailYaRegistradoException();
        }

        if (dto.getContrasenia().length() < 8){
            throw new ContraseniaInvalidaException();
        }

        Cliente nuevo = clienteMapper.toEntity(dto);

        Cliente guardado = clienteRepository.save(nuevo);

        return clienteMapper.toResponseDTO(guardado);
    }

    @Override
    public ClienteResponseDTO login(String email, String contrasenia) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(()->new ClienteNoExisteException());

        if (!Objects.equals(cliente.getContrasenia(), contrasenia)){
            throw new ContraseniaInvalidaException();
        } else if (!Objects.equals(cliente.getEmail(),email)) {
            throw new EmailNoExisteException();
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
    public ClienteResponseDTO editarPerfil(String email, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(() -> new ClienteNoExisteException());
        if (dto.getNombre() != null) {
            cliente.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            cliente.setApellido(dto.getApellido());
        }
        if (dto.getContrasenia() != null) {
            cliente.setContrasenia(dto.getContrasenia());
        }
        if (dto.getTelefono() != 0) {
            cliente.setTelefono(dto.getTelefono());
        }
        if (dto.getFechaNac() != null) {
            cliente.setFechaNac(dto.getFechaNac());
        }
        return clienteMapper.toResponseDTO(cliente);
    }

    @Override
    public List<Pedido> historialPedido(String email) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(() -> new ClienteNoExisteException());
        return cliente.getPedidos();
    }

    @Override
    public List<ClienteResponseDTO> listarClientes() {
        List <Cliente> clientes = clienteRepository.findAll();
        List <ClienteResponseDTO>retornar = new ArrayList<>();
        for (Cliente cliente: clientes){
            retornar.add(clienteMapper.toResponseDTO(cliente));
        }
        return retornar;
    }

    @Override
    public HamburguesaResponseDTO asociarHamburguesa(String emailCliente, Long idHamburguesa) {
        Cliente cliente = clienteRepository.findById(emailCliente)
                .orElseThrow(ClienteNoExisteException::new);
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa)
                .orElseThrow(()-> new HamburguesaNoEncontradaException());

        cliente.getCreaciones().add(hamburguesa);
        hamburguesa.setCliente(cliente);

        clienteRepository.save(cliente);

        return hamburguesaMapper.toResponseDTO(hamburguesa);
    }

    @Override
    public List<CreacionResponseDTO> mostrarCreaciones(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(()-> new ClienteNoExisteException());
        List<Creacion> creaciones = cliente.getCreaciones();
        List<CreacionResponseDTO> retornarlista = new ArrayList<>();
        for (Creacion creacion: creaciones){
            CreacionResponseDTO retornar = creacionMapper.toResponseDTO(creacion);
            retornarlista.add(retornar);
        }
        return retornarlista;
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosPorCliente(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(()-> new ClienteNoExisteException());
        List<Pedido> pedidos = cliente.getPedidos();
        List<PedidoResponseDTO> retornar = new ArrayList<>();
        for (Pedido pedido: pedidos){
            PedidoResponseDTO r = pedidoMapper.toResponseDTO(pedido);
            retornar.add(r);
        }
        return retornar;
    }

    @Override
    public PizzaResponseDTO asociarPizza(String email, Long idPizza) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(()-> new ClienteNoExisteException());
        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(()-> new PizzaNoExisteException());
        cliente.getCreaciones().add(pizza);
        pizza.setCliente(cliente);
        clienteRepository.save(cliente);
        return pizzaMapper.toResponseDTO(pizza);
    }
}


