package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Response.*;
import uy.um.edu.pizzumandburgum.Entidades.Administrador;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;
import uy.um.edu.pizzumandburgum.Entidades.MedioDePago;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;
import uy.um.edu.pizzumandburgum.Excepciones.MedioDePago.MedioDePagoNoExisteException;
import uy.um.edu.pizzumandburgum.Mappers.AdministradorMapper;
import uy.um.edu.pizzumandburgum.Mappers.ClienteMapper;
import uy.um.edu.pizzumandburgum.Mappers.PedidoMapper;
import uy.um.edu.pizzumandburgum.Repositorios.AdministradorRepository;
import uy.um.edu.pizzumandburgum.Repositorios.ClienteRepository;
import uy.um.edu.pizzumandburgum.Repositorios.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.Repositorios.PedidoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.ReporteService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MedioDePagoRepository medioDePagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private AdministradorMapper administradorMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    @Override
    public List<AdministradorResponseDTO> obtenerCantidadUsuarios() {
        List<Administrador> administradores = administradorRepository.findAll();
        List<AdministradorResponseDTO> administradoresResponseDTO = new ArrayList<>();
        for (Administrador administrador : administradores) {
            administradoresResponseDTO.add(administradorMapper.toResponseDTO(administrador));
        }
        return administradoresResponseDTO;
    }

    @Override
    public ClienteResponseDTO obtenerClientePorTarjeta(Long numeroTarjeta) {
        MedioDePago tarjeta = medioDePagoRepository.findByNumeroTarjeta(numeroTarjeta).orElseThrow(MedioDePagoNoExisteException::new);
        Cliente cliente = tarjeta.getCliente();

        return clienteMapper.toResponseDTO(cliente);
    }


    @Override
    public List<TicketResponseDTO> obtenerTicketsDeVenta(LocalDate fecha) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<TicketResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoResponseDTO dto = pedidoMapper.toResponseDTO(pedido);
            if ((dto.getFecha().isEqual(fecha)) && !dto.getEstado().equals("En Cola") && !dto.getEstado().equals("Cancelado")) {
                Optional<Cliente> cliente = clienteRepository.findByEmail(dto.getIdClienteAsignado());
                Long idPedido = pedido.getId();
                String email =  cliente.get().getEmail();
                Long cedula = cliente.get().getCedula();
                float precio = pedido.getPrecio();
                resultado.add(new TicketResponseDTO(idPedido, email, cedula, precio));
            }
        }
        return resultado;
    }
}