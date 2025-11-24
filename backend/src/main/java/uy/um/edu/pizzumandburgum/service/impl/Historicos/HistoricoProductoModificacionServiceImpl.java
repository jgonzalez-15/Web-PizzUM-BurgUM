package uy.um.edu.pizzumandburgum.service.impl.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoProductoModificacion;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.Historicos.HistoricoProductoMapper;
import uy.um.edu.pizzumandburgum.repository.Historicos.HistoricoProductoModificacionRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoProductoModificacionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricoProductoModificacionServiceImpl implements HistoricoProductoModificacionService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private HistoricoProductoModificacionRepository historicoProductoModificacionesRepository;

    @Autowired
    private HistoricoProductoMapper historicoProductoMapper;

    @Override
    public void registrarActualizacion(Producto pAnterior, Producto pNuevo) {
        Producto nuevo = productoRepository.findById(pNuevo.getIdProducto()).orElseThrow(ProductoNoExisteException::new);
        Producto anterior = productoRepository.findById(pAnterior.getIdProducto()).orElseThrow(ProductoNoExisteException::new);

        HistoricoProductoModificacion historico = new HistoricoProductoModificacion();

        historico.setProducto(nuevo);
        historico.setPrecioAnterior(anterior.getPrecio());
        historico.setNombreAnterior(anterior.getNombre());
        historico.setSinTaccAnterior(anterior.isSinTacc());
        historico.setTipoAnterior(anterior.getTipo());
        historico.setNombreNuevo(nuevo.getNombre());
        historico.setPrecioNuevo(nuevo.getPrecio());
        historico.setTipoNuevo(nuevo.getTipo());
        historico.setSinTaccNuevo(nuevo.isSinTacc());
        historico.setFechaModificacion(LocalDateTime.now());
        historico.setTipoModificiacion("Actualizacion");

        historicoProductoModificacionesRepository.save(historico);
        nuevo.getHistorico().add(historico);
    }

    @Override
    public void RegistrarAgregar(Producto pNuevo) {
        Producto nuevo = productoRepository.findById(pNuevo.getIdProducto()).orElseThrow(ProductoNoExisteException::new);
        HistoricoProductoModificacion historico = new HistoricoProductoModificacion();
        historico.setNombreNuevo(nuevo.getNombre());
        historico.setPrecioNuevo(nuevo.getPrecio());
        historico.setTipoNuevo(nuevo.getTipo());
        historico.setSinTaccNuevo(nuevo.isSinTacc());
        historico.setFechaModificacion(LocalDateTime.now());
        historico.setTipoModificiacion("Agrego");
        historico.setProducto(nuevo);
        historicoProductoModificacionesRepository.save(historico);
        nuevo.getHistorico().add(historico);
    }

    @Override
    public void RegistrarEliminar(Producto pAnterior) {
        Producto anterior = productoRepository.findById(pAnterior.getIdProducto()).orElseThrow(ProductoNoExisteException::new);

        HistoricoProductoModificacion historico = new HistoricoProductoModificacion();

        historico.setProducto(anterior);
        historico.setPrecioAnterior(anterior.getPrecio());
        historico.setNombreAnterior(anterior.getNombre());
        historico.setSinTaccAnterior(anterior.isSinTacc());
        historico.setTipoAnterior(anterior.getTipo());
        historico.setFechaModificacion(LocalDateTime.now());
        historico.setTipoModificiacion("Eliminado");

        historicoProductoModificacionesRepository.save(historico);
        anterior.getHistorico().add(historico);
    }

    @Override
    public void RegistrarOculto(Producto pAnterior) {
        Producto anterior = productoRepository.findById(pAnterior.getIdProducto()).orElseThrow(ProductoNoExisteException::new);

        HistoricoProductoModificacion historico = new HistoricoProductoModificacion();

        historico.setProducto(anterior);
        historico.setPrecioAnterior(anterior.getPrecio());
        historico.setNombreAnterior(anterior.getNombre());
        historico.setSinTaccAnterior(anterior.isSinTacc());
        historico.setTipoAnterior(anterior.getTipo());
        historico.setFechaModificacion(LocalDateTime.now());
        historico.setTipoModificiacion("Oculto");

        historicoProductoModificacionesRepository.save(historico);
        anterior.getHistorico().add(historico);
    }

    @Override
    public List<HistoricoProductoResponseDTO> listarHistoricosPorProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId).orElseThrow(ProductoNoExisteException::new);

        List<HistoricoProductoModificacion> lista = historicoProductoModificacionesRepository.findByProductoOrderByFechaModificacionDesc(producto);

        List<HistoricoProductoResponseDTO> retornar = new ArrayList<>();

        for (HistoricoProductoModificacion hp : lista) {
            retornar.add(historicoProductoMapper.toResponseDTO(hp));
        }

        return retornar;
    }

    @Override
    public List<HistoricoProductoResponseDTO> listarTodosLosHistoricos() {
        List<HistoricoProductoModificacion> lista = historicoProductoModificacionesRepository.findAllByOrderByFechaModificacionDesc();

        List<HistoricoProductoResponseDTO> retornar = new ArrayList<>();

        for (HistoricoProductoModificacion hp : lista) {retornar.add(historicoProductoMapper.toResponseDTO(hp));
        }

        return retornar;
    }

    @Override
    public List<HistoricoProductoResponseDTO> listarHistoricosPorTipo(String tipoModificacion) {
        List<HistoricoProductoModificacion> lista = historicoProductoModificacionesRepository.findByTipoModificiacionOrderByFechaModificacionDesc(tipoModificacion);

        List<HistoricoProductoResponseDTO> retornar = new ArrayList<>();

        for (HistoricoProductoModificacion hp : lista) {
            retornar.add(historicoProductoMapper.toResponseDTO(hp));
        }

        return retornar;
    }
}
