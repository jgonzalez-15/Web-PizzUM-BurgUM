package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.um.edu.pizzumandburgum.DTOs.Request.ClienteRegistrarRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;
import uy.um.edu.pizzumandburgum.Entidades.Domicilio;
import uy.um.edu.pizzumandburgum.Entidades.MedioDePago;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.ContraseniaInvalidaException;
import uy.um.edu.pizzumandburgum.Mappers.ClienteMapper;
import uy.um.edu.pizzumandburgum.Mappers.DomicilioMapper;
import uy.um.edu.pizzumandburgum.Mappers.MedioDePagoMapper;
import uy.um.edu.pizzumandburgum.Mappers.PedidoMapper;
import uy.um.edu.pizzumandburgum.Repositorios.ClienteRepository;
import uy.um.edu.pizzumandburgum.Repositorios.DomicilioRepository;
import uy.um.edu.pizzumandburgum.Repositorios.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.ClienteDomicilioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {
    @InjectMocks
    private ClienteServiceImpl service;

    @Mock
    private ClienteRepository clienteRepo;
    @Mock private ClienteMapper clienteMapper;
    @Mock private DomicilioRepository domicilioRepository;
    @Mock private DomicilioMapper domicilioMapper;
    @Mock private MedioDePagoRepository medioDePagoRepo;
    @Mock private MedioDePagoMapper medioMapper;
    @Mock private ClienteDomicilioService clienteDomicilioService;
    @Mock private PedidoMapper pedidoMapper;

    @Test
    void registrarCliente_ok() {
        ClienteRegistrarRequestDTO dto = new ClienteRegistrarRequestDTO();
        dto.setEmail("test@mail.com");
        dto.setContrasenia("password123");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setTelefono(123L);
        dto.setCedula(555L);
        dto.setDomicilios(List.of(new DomicilioRequestDTO("Calle 123")));
        dto.setMediosDePago(List.of(new MedioDePagoRequestDTO(1234L, LocalDate.now(), "Juan Perez")));

        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setEmail("test@mail.com");

        when(clienteRepo.existsByEmail("test@mail.com")).thenReturn(false);
        when(clienteRepo.save(any())).thenReturn(clienteGuardado);
        when(clienteRepo.findById("test@mail.com")).thenReturn(Optional.of(clienteGuardado));

        Domicilio domicilio = new Domicilio();
        domicilio.setId(1L);
        when(domicilioMapper.toEntity(any())).thenReturn(domicilio);
        when(domicilioRepository.saveAndFlush(any())).thenReturn(domicilio);

        MedioDePago medioDePago = new MedioDePago();
        when(medioMapper.toEntity(any())).thenReturn(medioDePago);

        when(clienteMapper.toResponseDTO(clienteGuardado))
                .thenReturn(new ClienteResponseDTO("test@mail.com", "Juan", "Perez", 123L, null, 555L));

        ClienteResponseDTO resp = service.registrarCliente(dto);

        assertNotNull(resp);
        assertEquals("test@mail.com", resp.getEmail());
    }

    @Test
    void login_ok() {
        Cliente cliente = new Cliente();
        cliente.setEmail("juan@mail.com");
        cliente.setContrasenia("12345678");

        when(clienteRepo.findById("juan@mail.com"))
                .thenReturn(Optional.of(cliente));

        ClienteResponseDTO resp = service.login("juan@mail.com", "12345678");

        assertNotNull(resp);
        assertEquals("juan@mail.com", resp.getEmail());
    }


    @Test
    void login_contraseniaIncorrecta() {
        Cliente cliente = new Cliente();
        cliente.setEmail("juan@mail.com");
        cliente.setContrasenia("correcta");

        when(clienteRepo.findById("juan@mail.com"))
                .thenReturn(Optional.of(cliente));

        assertThrows(ContraseniaInvalidaException.class,
                () -> service.login("juan@mail.com", "incorrecta"));
    }

    @Test
    void login_clienteNoExiste() {
        when(clienteRepo.findById("no@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ClienteNoExisteException.class,
                () -> service.login("no@mail.com", "12345678"));
    }

    @Test
    void editarPerfil_ok() {
        Cliente cliente = new Cliente();
        cliente.setEmail("juan@mail.com");
        cliente.setNombre("Juan");

        when(clienteRepo.findById("juan@mail.com"))
                .thenReturn(Optional.of(cliente));
        when(clienteRepo.save(cliente))
                .thenReturn(cliente);

        when(clienteMapper.toResponseDTO(cliente))
                .thenReturn(new ClienteResponseDTO("juan@mail.com", "Juan", null, null, null, null));

        ClienteUpdateDTO dto = new ClienteUpdateDTO();
        dto.setNombre("Juanito");

        ClienteResponseDTO resp = service.editarPerfil("juan@mail.com", dto);

        assertEquals("juan@mail.com", resp.getEmail());
        assertEquals("Juanito", cliente.getNombre());
    }

    @Test
    void editarPerfil_clienteNoExiste() {
        when(clienteRepo.findById("x@mail.com"))
                .thenReturn(Optional.empty());

        ClienteUpdateDTO dto = new ClienteUpdateDTO();
        dto.setNombre("Nuevo");

        assertThrows(ClienteNoExisteException.class,
                () -> service.editarPerfil("x@mail.com", dto));
    }


    @Test
    void historialPedido_ok() {
        Cliente cliente = new Cliente();
        cliente.setEmail("test@mail.com");

        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        cliente.setPedidos(List.of(pedido1, pedido2));

        when(clienteRepo.findById("test@mail.com"))
                .thenReturn(Optional.of(cliente));

        when(pedidoMapper.toResponseDTO(any()))
                .thenReturn(new PedidoResponseDTO());

        List<PedidoResponseDTO> lista = service.historialPedido("test@mail.com");

        assertEquals(2, lista.size());
    }


    @Test
    void historialPedido_clienteNoExiste() {
        when(clienteRepo.findById("x@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ClienteNoExisteException.class,
                () -> service.historialPedido("x@mail.com"));
    }




}