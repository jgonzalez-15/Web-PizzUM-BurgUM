package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.mapper.AdministradorMapper;
import uy.um.edu.pizzumandburgum.mapper.ClienteMapper;
import uy.um.edu.pizzumandburgum.mapper.MedioDePagoMapper;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.AdministradorRepository;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.MedioDePagoRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ReporteService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public List<PedidoResponseDTO> obtenerTicketsDeVenta(LocalDate fecha) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoResponseDTO dto = pedidoMapper.toResponseDTO(pedido);
            if ((dto.getFecha().isEqual(fecha)) && !dto.getEstado().equals("En Cola") && !dto.getEstado().equals("Cancelado")) {
                resultado.add(dto);
            }
        }
        return resultado;
    }
}