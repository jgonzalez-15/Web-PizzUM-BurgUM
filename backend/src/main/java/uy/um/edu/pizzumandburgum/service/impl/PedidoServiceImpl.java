package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.*;
import uy.um.edu.pizzumandburgum.service.Interfaces.*;

import java.time.LocalDate;

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
    private PedidoCreacionRepository pedidoCreacionRepository;

    @Autowired
    private MedioDePagoService medioDePagoService;

    @Autowired
    private PedidoBebidaRepository pedidoBebidaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private CreacionRepository creacionRepository;
    @Override
    public PedidoResponseDTO realizarPedido(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(String.valueOf(dto.getIdCliente())).orElseThrow(() -> new ClienteNoExisteException());

        MedioDePago medioDePago = medioDePagoService.obtenerMedioDePago(
                cliente.getEmail(),
                dto.getIdMedioDePago()
        );

        Domicilio domicilioTemp = domicilioRepository.findById(dto.getIdDomicilio())
                .orElseThrow(() -> new DomicilioNoExisteException());

        Domicilio domicilio = clienteDomicilioService.obtenerDomicilio(
                cliente.getEmail(),
                domicilioTemp.getDireccion()
        );

        // 4. Crear el pedido base usando el mapper
        Pedido pedido = pedidoMapper.toEntity(dto);
        pedido.setEstado("En cola");
        pedido.setClienteAsignado(cliente);
        pedido.setDomicilio(domicilio);
        pedido.setMedioDePago(medioDePago);
        pedido.setFecha(LocalDate.now());

        // 5. GUARDAR PRIMERO para obtener el ID
        pedido = pedidoRepository.save(pedido);

        // 6. Procesar creaciones (hamburguesas/pizzas) y calcular precio
        float precioTotal = 0f;

        if (dto.getCreaciones() != null && !dto.getCreaciones().isEmpty()) {
            for (PedidoCreacionDTO creacionDto : dto.getCreaciones()) {
                // Agregar la creación al pedido
                pedidoCrecionService.agregarCreacion(
                        pedido.getIdPedido(),
                        creacionDto.getCreacion().getId(),
                        creacionDto.getCantidad()
                );

                // Buscar la creación para obtener su precio
                Creacion creacion = creacionRepository.findById(creacionDto.getCreacion().getId())
                        .orElseThrow(() -> new CreacionNoEncontradaException());

                precioTotal += creacion.getPrecio() * creacionDto.getCantidad();
            }
        }

        // 7. Procesar bebidas
        if (dto.getBebidas() != null && !dto.getBebidas().isEmpty()) {
            for (PedidoBebidaResponseDTO bebidaDto : dto.getBebidas()) {
                // Agregar la bebida al pedido
                pedidoBebidaService.agregarBebida(
                        pedido.getIdPedido(),
                        bebidaDto.getProducto().getIdProducto(),
                        bebidaDto.getCantidad()
                );

                // Buscar el producto para obtener su precio
                Producto bebida = productoRepository.findById(bebidaDto.getProducto().getIdProducto())
                        .orElseThrow(() -> new ProductoNoExisteException());

                precioTotal += bebida.getPrecio() * bebidaDto.getCantidad();
            }
        }

        // 8. Actualizar el precio y guardar
        pedido.setPrecio(precioTotal);
        pedido = pedidoRepository.save(pedido);

        // 9. Convertir a DTO y retornar
        return pedidoMapper.toResponseDTO(pedido);}

    @Override
    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new PedidoNoEncontradoException();
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    public String consultarEstado(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNoEncontradoException());
        return pedido.getEstado();
    }

    @Override
    public void cambiarEstado(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNoEncontradoException());
        pedido.setEstado(estado);
    }
}
