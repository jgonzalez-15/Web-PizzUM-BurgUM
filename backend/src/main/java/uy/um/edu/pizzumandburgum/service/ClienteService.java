package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRequestDTO dto);
    ClienteResponseDTO login(String email, String password);
    HamburguesaResponseDTO diseñarHamburguesa (HamburguesaResponseDTO hamburguesaResponseDTO);
    PedidoResponseDTO realizarPedido (PedidoRequestDTO pedidoRequestDTO);

}
