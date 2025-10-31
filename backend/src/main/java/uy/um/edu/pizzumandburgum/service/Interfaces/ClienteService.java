package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.*;
import uy.um.edu.pizzumandburgum.dto.update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRequestDTO dto);
    ClienteResponseDTO login(String email, String password);
    ClienteResponseDTO editarPerfil (String email, ClienteUpdateDTO dto);
    List<PedidoResponseDTO> historialPedido(String email);
    List<ClienteResponseDTO> listarClientes();
    HamburguesaResponseDTO asociarHamburguesa(String emailCliente, Long idHamburguesa);
    List<CreacionResponseDTO> mostrarCreaciones(String email);
    List<PedidoResponseDTO> obtenerPedidosPorCliente(String email);
    PizzaResponseDTO asociarPizza(String email, Long idPizza);

}
