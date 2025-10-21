package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRequestDTO dto);
    ClienteResponseDTO login(String email, String password);
    HamburguesaResponseDTO diseñarHamburguesa (HamburguesaResponseDTO hamburguesaResponseDTO);
    PedidoResponseDTO realizarPedido (PedidoRequestDTO pedidoRequestDTO);
    List<Pedido> historialPedido(String email);

}
