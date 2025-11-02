package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.controller.NotificacionesController;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.EstadoInvalidoException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.*;
import uy.um.edu.pizzumandburgum.service.Interfaces.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoBebidaService pedidoBebidaService;

    @Autowired
    private PedidoCrecionService pedidoCrecionService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteDomicilioService clienteDomicilioService;

    @Autowired
    private MedioDePagoService medioDePagoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private CreacionRepository creacionRepository;

    @Autowired
    private NotificacionesController notificacionesController;

    @Autowired
    private FavoritoRepository favoritoRepository;


    @Override
    public PedidoResponseDTO realizarPedido(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.getIdCliente()).orElseThrow(ClienteNoExisteException::new);

        MedioDePago medioDePago = medioDePagoService.obtenerMedioDePago(cliente.getEmail(), dto.getIdMedioDePago());

        Domicilio domicilioTemp = domicilioRepository.findById(dto.getIdDomicilio()).orElseThrow(DomicilioNoExisteException::new);

        Domicilio domicilio = clienteDomicilioService.obtenerDomicilio(cliente.getEmail(), domicilioTemp.getDireccion());

        Pedido pedido = pedidoMapper.toEntity(dto);
        pedido.setEstado("En Cola");
        pedido.setEstaPago(false);
        pedido.setClienteAsignado(cliente);
        pedido.setDomicilio(domicilio);
        pedido.setMedioDePago(medioDePago);
        pedido.setFecha(LocalDate.now());

        pedido = pedidoRepository.save(pedido);

        float precioTotal = 0f;

        if (dto.getCreaciones() != null && !dto.getCreaciones().isEmpty()) {
            for (PedidoCreacionDTO creacionDto : dto.getCreaciones()) {

                pedidoCrecionService.agregarCreacion(pedido.getId(), creacionDto.getCreacion().getId(), creacionDto.getCantidad());

                Creacion creacion = creacionRepository.findById(creacionDto.getCreacion().getId()).orElseThrow(CreacionNoEncontradaException::new);

                precioTotal += creacion.getPrecio() * creacionDto.getCantidad();
            }
        }


        if (dto.getBebidas() != null && !dto.getBebidas().isEmpty()) {
            for (PedidoBebidaResponseDTO bebidaDto : dto.getBebidas()) {
                pedidoBebidaService.agregarBebida(pedido.getId(), bebidaDto.getProducto().getIdProducto(), bebidaDto.getCantidad());

                Producto bebida = productoRepository.findById(bebidaDto.getProducto().getIdProducto()).orElseThrow(ProductoNoExisteException::new);

                precioTotal += bebida.getPrecio() * bebidaDto.getCantidad();
            }
        }

        pedido.setPrecio(precioTotal);
        pedido = pedidoRepository.save(pedido);
        cliente.getPedidos().add(pedido);

        return pedidoMapper.toResponseDTO(pedido);
    }

    @Override
    public void eliminarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(PedidoNoEncontradoException::new);

        List<PedidoCreacion> creacionesDelPedido = new ArrayList<>(pedido.getCreacionesPedido());

        for (PedidoCreacion pc : creacionesDelPedido) {
            Creacion creacion = pc.getCreacion();
            pedido.getCreacionesPedido().remove(pc);

            boolean esFavorita = favoritoRepository.existsByCreacionId(creacion.getId());

            if (!esFavorita) {
                creacionRepository.delete(creacion);
            }
        }

        pedidoRepository.delete(pedido);
    }

    @Override
    public String consultarEstado(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(PedidoNoEncontradoException::new);
        return pedido.getEstado();
    }

    @Override
    public void cambiarEstado(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(PedidoNoEncontradoException::new);

        String estadoActual = pedido.getEstado();
        String nuevoEstado;

        switch (estadoActual) {
            case "En Cola":
                nuevoEstado = "En Preparacion";
                break;
            case "En Preparacion":
                nuevoEstado = "En Camino";
                break;
            case "En Camino":
                nuevoEstado = "Entregado";
                break;
            case "Entregado":
                throw new EstadoInvalidoException();
            default:
                throw new EstadoInvalidoException();
        }
        notificacionesController.enviarNotificacion("Pedido " + id + " cambiado a " + nuevoEstado);
        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
    }

    @Override
    public List<PedidoResponseDTO> pedidosEnCurso() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Pedido> convertir = new ArrayList<>();
        List<PedidoResponseDTO> pedidoResponseDTOS = new ArrayList<>();
        for (Pedido pedido: pedidos){
            if (!pedido.getEstado().equals("Entregado")){
                convertir.add(pedido);
            }
        }
        for (Pedido pedido: convertir){
            pedidoResponseDTOS.add(pedidoMapper.toResponseDTO(pedido));
        }
        return pedidoResponseDTOS;
    }
}
