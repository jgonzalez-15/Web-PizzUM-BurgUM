package uy.um.edu.pizzumandburgum.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.*;
import uy.um.edu.pizzumandburgum.dto.response.*;
import uy.um.edu.pizzumandburgum.dto.update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.PizzaNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.CampoObligatorioException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.EmailNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.EmailYaRegistradoException;
import uy.um.edu.pizzumandburgum.mapper.*;
import uy.um.edu.pizzumandburgum.repository.*;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteService;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoClienteModificacionesService;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;
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

    @Autowired
    private MedioDePagoMapper medioDePagoMapper;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Autowired
    private ClienteDomicilioRepository clienteDomicilioRepository;

    @Autowired
    private HistoricoClienteModificacionesService historicoService;

    @Override
    @Transactional
    public ClienteResponseDTO registrarCliente(ClienteRegistrarRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new EmailYaRegistradoException();
        }

        if (dto.getContrasenia().length() < 8){
            throw new ContraseniaInvalidaException();
        }

        if (dto.getDomicilios() == null || dto.getDomicilios().isEmpty()) {
            throw new CampoObligatorioException("Debe ingresar al menos un domicilio");
        }

        if (dto.getMediosDePago() == null || dto.getMediosDePago().isEmpty()) {
            throw new CampoObligatorioException("Debe ingresar al menos un medio de pago");
        }

        Cliente nuevo = new Cliente();
        nuevo.setEmail(dto.getEmail());
        nuevo.setContrasenia(dto.getContrasenia());
        nuevo.setApellido(dto.getApellido());
        nuevo.setNombre(dto.getNombre());
        nuevo.setTelefono(dto.getTelefono());
        nuevo.setFechaNac(dto.getFechaNac());
        Cliente guardado = clienteRepository.save(nuevo);

        for (MedioDePagoRequestDTO medioDePagoRequestDTO : dto.getMediosDePago()) {
            MedioDePago medioDePago = medioDePagoMapper.toEntity(medioDePagoRequestDTO);
            medioDePago.setCliente(guardado);
            medioDePagoRepository.save(medioDePago);
        }

        for (DomicilioRequestDTO domicilioRequestDTO : dto.getDomicilios()) {
            Domicilio domicilio = domicilioMapper.toEntity(domicilioRequestDTO);
            Domicilio domicilioGuardado = domicilioRepository.saveAndFlush(domicilio);

            ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
            clienteDomicilio.setCliente(guardado);
            clienteDomicilio.setDomicilio(domicilioGuardado);
            clienteDomicilioRepository.save(clienteDomicilio);
        }

        guardado = clienteRepository.findById(guardado.getEmail()).orElseThrow();
        return clienteMapper.toResponseDTO(guardado);
    }

    @Override
    public ClienteResponseDTO login(String email, String contrasenia) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(ClienteNoExisteException::new);

        if (!Objects.equals(cliente.getContrasenia(), contrasenia)){
            throw new ContraseniaInvalidaException();
        } else if (!Objects.equals(cliente.getEmail(),email)) {
            throw new EmailNoExisteException();
        }

        return new ClienteResponseDTO(cliente.getEmail(), cliente.getNombre(), cliente.getApellido(), cliente.getTelefono(), cliente.getFechaNac());
    }

    @Override
    public ClienteResponseDTO editarPerfil(String email, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(ClienteNoExisteException::new);
        Cliente nuevo = new Cliente();
        if (dto.getNombre() != null) {
            nuevo.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null) {
            nuevo.setApellido(dto.getApellido());
        }
        if (dto.getContrasenia() != null) {
            nuevo.setContrasenia(dto.getContrasenia());
        }
        if (dto.getTelefono() != 0) {
            nuevo.setTelefono(dto.getTelefono());
        }
        if (dto.getFechaNac() != null) {
            nuevo.setFechaNac(dto.getFechaNac());
        }
        return clienteMapper.toResponseDTO(cliente);
    }

    @Override
    public List<PedidoResponseDTO> historialPedido(String email) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(ClienteNoExisteException::new);
        List<Pedido> pedidos = cliente.getPedidos();
        List<PedidoResponseDTO> historialPedidos = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            historialPedidos.add(pedidoMapper.toResponseDTO(pedido));
        }
        return historialPedidos;
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
        Cliente cliente = clienteRepository.findById(emailCliente).orElseThrow(ClienteNoExisteException::new);
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa).orElseThrow(HamburguesaNoEncontradaException::new);

        cliente.getCreaciones().add(hamburguesa);
        hamburguesa.setCliente(cliente);

        clienteRepository.save(cliente);

        return hamburguesaMapper.toResponseDTO(hamburguesa);
    }

    @Override
    public List<CreacionResponseDTO> mostrarCreaciones(String email) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(ClienteNoExisteException::new);
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
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(ClienteNoExisteException::new);
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
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(ClienteNoExisteException::new);
        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(PizzaNoExisteException::new);
        cliente.getCreaciones().add(pizza);
        pizza.setCliente(cliente);
        clienteRepository.save(cliente);
        return pizzaMapper.toResponseDTO(pizza);
    }

    @Override
    public List<DomicilioResponseDTO> mostrarDomicilios(String idCliente) {
        Cliente cliente = clienteRepository.findByEmail(idCliente).orElseThrow(ClienteNoExisteException::new);
        List<DomicilioResponseDTO>domicilios = new ArrayList<>();
        for (ClienteDomicilio cd : cliente.getDomicilios()){
            Domicilio domicilio = domicilioRepository.findById(cd.getDomicilio().getId()).orElseThrow(DomicilioNoExisteException::new);
            DomicilioResponseDTO dto = domicilioMapper.toResponseDTO(domicilio);
            domicilios.add(dto);
        }
        return domicilios;
    }

    @Override
    public ClienteResponseDTO obtenerCliente(String email) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(ClienteNoExisteException::new);
        return new ClienteResponseDTO(cliente.getEmail(), cliente.getNombre(), cliente.getApellido(), cliente.getTelefono(), cliente.getFechaNac());
    }



}


