package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.CalificacionRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.CalificacionResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Calificacion;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;

@Component
public class CalificacionMapper {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Calificacion toEntity(CalificacionRequestDTO dto) {
        Calificacion calificacion = new Calificacion();
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());
        calificacion.setCliente(clienteRepository.findByEmail(dto.getIdCliente()).orElseThrow(ClienteNoExisteException::new));
        calificacion.setPedido(pedidoRepository.findById(dto.getIdPedido()).orElseThrow(PedidoNoEncontradoException::new));
        return calificacion;
    }

    public CalificacionResponseDTO toResponseDTO(Calificacion calificacion) {
        return new CalificacionResponseDTO(calificacion.getId(), calificacion.getPuntuacion(), calificacion.getComentario(), calificacion.getFecha(),calificacion.getPedido().getId(),calificacion.getCliente().getEmail());
    }
}
