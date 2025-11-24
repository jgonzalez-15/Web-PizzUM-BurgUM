package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.*;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.mapper.AdministradorMapper;
import uy.um.edu.pizzumandburgum.mapper.MedioDePagoMapper;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.AdministradorRepository;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ReporteService;

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
    private MedioDePagoMapper medioDePagoMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private AdministradorMapper administradorMapper;

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
    public List<MedioDePagoDTO> obtenerDatosTarjetas() {
        List<MedioDePago> tarjetas = medioDePagoRepository.findAll();
        List<MedioDePagoDTO> resultado = new ArrayList<>();
        for (MedioDePago tarjeta : tarjetas) {
            MedioDePagoDTO dto = medioDePagoMapper.toResponseDTO(tarjeta);
            resultado.add(dto);
        }
        return resultado;
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