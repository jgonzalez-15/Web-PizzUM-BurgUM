package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Request.PagoDummyRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PagoDummyResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.MedioDePago;
import uy.um.edu.pizzumandburgum.Entidades.PagoDummy;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;
import uy.um.edu.pizzumandburgum.Excepciones.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.Excepciones.Pedido.PedidoPagoException;
import uy.um.edu.pizzumandburgum.Repositorios.PagoDummyRepository;
import uy.um.edu.pizzumandburgum.Repositorios.PedidoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.MedioDePagoService;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.PagoDummyService;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PagoDummyServiceImpl implements PagoDummyService {

    @Autowired
    private PagoDummyRepository pagoDummyRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MedioDePagoService medioDePagoService;

    @Override
    public PagoDummyResponseDTO procesarPago(PagoDummyRequestDTO request) {
        Pedido pedido = pedidoRepository.findById(request.getIdPedido()).orElseThrow(PedidoNoEncontradoException::new);

        if (pedido.isEstaPago()){
            throw new PedidoPagoException();
        }
        MedioDePago medioDePago = medioDePagoService.obtenerMedioDePago(pedido.getClienteAsignado().getEmail(),pedido.getMedioDePago().getId());

        if (medioDePago.getNumeroTarjeta() == null){
            throw new MedioDePagoNoExisteException();
        }

        PagoDummy pago = new PagoDummy();
        pago.setCodigoTransaccion("DUMMY-" + UUID.randomUUID());
        pago.setMonto(pedido.getPrecio());
        pago.setFechaPago(LocalDate.now());
        pago.setEstadoPago("APROBADO");
        pago.setPedido(pedido);

        pagoDummyRepository.save(pago);

        pedido.setEstaPago(true);
        pedidoRepository.save(pedido);

        PagoDummyResponseDTO dto = new PagoDummyResponseDTO();
        dto.setCodigoTransaccion(pago.getCodigoTransaccion());
        dto.setEstadoPago(pago.getEstadoPago());
        dto.setMonto(pago.getMonto());
        dto.setIdPedido(pedido.getId());
        dto.setFechaPago(pago.getFechaPago());

        return dto;
    }
}
