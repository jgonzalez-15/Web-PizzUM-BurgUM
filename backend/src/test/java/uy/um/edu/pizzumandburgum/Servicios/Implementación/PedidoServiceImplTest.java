package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.um.edu.pizzumandburgum.DTOs.Request.*;
import uy.um.edu.pizzumandburgum.DTOs.Response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.*;
import uy.um.edu.pizzumandburgum.Excepciones.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.Mappers.PedidoMapper;
import uy.um.edu.pizzumandburgum.Repositorios.*;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock private PedidoRepository pedidoRepo;
    @Mock private PedidoMapper pedidoMapper;
    @Mock private ClienteRepository clienteRepo;
    @Mock private ClienteDomicilioService clienteDomService;
    @Mock private MedioDePagoService medioPagoService;
    @Mock private DomicilioRepository domicilioRepo;
    @Mock private ProductoRepository productoRepo;
    @Mock private CreacionRepository creacionRepo;
    @Mock private PedidoBebidaService pedidoBebidaService;
    @Mock private PedidoCrecionService pedidoCreacionService;

    @Test
    void realizarPedido_ok() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setIdCliente("cliente@mail.com");
        dto.setIdDomicilio(1L);

        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");

        Domicilio dom = new Domicilio();

        Pedido pedido = new Pedido();

        when(clienteRepo.findByEmail("cliente@mail.com"))
                .thenReturn(Optional.of(cliente));

        when(domicilioRepo.findById(1L))
                .thenReturn(Optional.of(dom));

        when(clienteDomService.obtenerDomicilio(any(), any()))
                .thenReturn(dom);

        when(pedidoMapper.toEntity(dto))
                .thenReturn(pedido);

        when(pedidoRepo.save(any()))
                .thenReturn(pedido);

        when(pedidoMapper.toResponseDTO(any()))
                .thenReturn(new PedidoResponseDTO());

        PedidoResponseDTO resp = service.realizarPedido(dto);

        assertNotNull(resp);
    }

    @Test
    void realizarPedido_clienteNoExiste() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setIdCliente("noExiste@mail.com");

        when(clienteRepo.findByEmail("noExiste@mail.com"))
                .thenThrow(new ClienteNoExisteException());

        PedidoResponseDTO resp = service.realizarPedido(dto);
        assertNull(resp);
    }


    @Test
    void realizarPedido_domicilioNoExiste() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setIdCliente("cliente@mail.com");
        dto.setIdDomicilio(10L);

        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");

        when(clienteRepo.findByEmail("cliente@mail.com"))
                .thenReturn(Optional.of(cliente));

        when(domicilioRepo.findById(10L))
                .thenThrow(new DomicilioNoExisteException());

        assertThrows(DomicilioNoExisteException.class,
                () -> service.realizarPedido(dto));
    }

    @Test
    void consultarEstado_ok() {

        Pedido p = new Pedido();
        p.setEstado("En Preparacion");

        doReturn(Optional.of(p))
                .when(pedidoRepo).findById(5L);

        assertEquals("En Preparacion", service.consultarEstado(5L));
    }


    @Test
    void cambiarEstado_avanzaEstados() {
        Pedido p = new Pedido();
        p.setEstado("En Cola");

        when(pedidoRepo.findById(1L)).thenReturn(Optional.of(p));

        service.cambiarEstado(1L);
        assertEquals("En Preparacion", p.getEstado());

        service.cambiarEstado(1L);
        assertEquals("En Camino", p.getEstado());

        service.cambiarEstado(1L);
        assertEquals("Entregado", p.getEstado());
    }

    @Test
    void pedidosEnCurso_ok() {
        Pedido p1 = new Pedido();
        p1.setEstaPago(true);
        p1.setEstado("En Camino");

        Pedido p2 = new Pedido();
        p2.setEstaPago(true);
        p2.setEstado("Entregado");

        PedidoResponseDTO dto = new PedidoResponseDTO();

        when(pedidoRepo.findAll()).thenReturn(List.of(p1, p2));
        when(pedidoMapper.toResponseDTO(p1)).thenReturn(dto);

        List<PedidoResponseDTO> lista = service.pedidosEnCurso();

        assertEquals(1, lista.size());
    }

}
