package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.PagoDummyRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PagoDummyResponseDTO;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.PagoDummy;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.exceptions.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoPagoException;
import uy.um.edu.pizzumandburgum.repository.PagoDummyRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;
import uy.um.edu.pizzumandburgum.service.Interfaces.PagoDummyService;

import java.time.LocalDateTime;
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
        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new PedidoNoEncontradoException());

        if (pedido.isEstaPago()){
            throw new PedidoPagoException();
        }
        MedioDePago medioDePago = medioDePagoService.obtenerMedioDePago(pedido.getClienteAsignado().getEmail(),pedido.getMedioDePago().getNumero());

        if (medioDePago.getNumero() == null){
            throw new MedioDePagoNoExisteException();
        }

        PagoDummy pago = new PagoDummy();
        pago.setCodigoTransaccion("DUMMY-" + UUID.randomUUID());
        pago.setMonto(pedido.getPrecio());
        pago.setFechaPago(LocalDateTime.now());
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

        return dto;
    }
}
