package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRequestDTO dto);
    ClienteResponseDTO login(String email, String password);
    ClienteResponseDTO editarPerfil (String email, ClienteUpdateDTO dto);
    List<Pedido> historialPedido(String email);
    List<ClienteResponseDTO> listarClientes();
    HamburguesaResponseDTO asociarHamburguesa(String emailCliente, Long idHamburguesa);
    List<CreacionResponseDTO> mostrarCreaciones(String email);
    List<CreacionResponseDTO> mostrarCreacionesFavoritas(String email);
    List<PedidoResponseDTO> obtenerPedidosPorCliente(String email);
}
