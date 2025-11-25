package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.ClienteRegistrarRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.*;
import uy.um.edu.pizzumandburgum.DTOs.Update.ClienteUpdateDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRegistrarRequestDTO dto);
    ClienteResponseDTO login(String email, String password);
    ClienteResponseDTO editarPerfil (String email, ClienteUpdateDTO dto);
    List<PedidoResponseDTO> historialPedido(String email);
    List<ClienteResponseDTO> listarClientes();
    HamburguesaResponseDTO asociarHamburguesa(String emailCliente, Long idHamburguesa);
    List<CreacionResponseDTO> mostrarCreaciones(String email);
    List<PedidoResponseDTO> obtenerPedidosPorCliente(String email);
    PizzaResponseDTO asociarPizza(String email, Long idPizza);
    List<DomicilioResponseDTO> mostrarDomicilios(String idCliente);
    ClienteResponseDTO obtenerCliente(String email);
}
