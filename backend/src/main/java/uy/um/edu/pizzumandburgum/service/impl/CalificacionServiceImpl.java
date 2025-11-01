package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.CalificacionRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.CalificacionResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Calificacion;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.exceptions.Calificacion.CalificacionNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Calificacion.CalificacionYaExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Calificacion.PedidoNoEntregadoException;
import uy.um.edu.pizzumandburgum.exceptions.Calificacion.PuntuacionFueraDeRangoException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.CalificacionMapper;
import uy.um.edu.pizzumandburgum.repository.CalificacionRepository;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.CalificacionService;

import java.time.LocalDateTime;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private CalificacionMapper calificacionMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public CalificacionResponseDTO crearCalificacion(CalificacionRequestDTO dto) {

        Cliente cliente = clienteRepository.findByEmail(dto.getIdCliente()).orElseThrow(()-> new ClienteNoExisteException());
        Pedido pedido = pedidoRepository.findById(dto.getIdPedido()).orElseThrow(()-> new PedidoNoEncontradoException());
        if (dto.getPuntuacion()<0 || dto.getPuntuacion()>5){
            throw new PuntuacionFueraDeRangoException();
        }
        if (!"Entregado".equalsIgnoreCase(pedido.getEstado())) {
            throw new PedidoNoEntregadoException();
        }

        if (pedido.getCalificacion() != null) {
            throw new CalificacionYaExisteException();
        }
        Calificacion calificacion = calificacionMapper.toEntity(dto);
        calificacion.setFecha(LocalDateTime.now());
        calificacionRepository.save(calificacion);
        cliente.getCalificaciones().add(calificacion);
        pedido.setCalificacion(calificacion);

        return calificacionMapper.toResponseDTO(calificacion);
    }

    @Override
    public CalificacionResponseDTO calificacionPorPedido(Long idPedido) {
        return calificacionMapper.toResponseDTO(calificacionRepository.findByPedidoId(idPedido).orElseThrow(()-> new CalificacionNoExisteException()));
    }


}