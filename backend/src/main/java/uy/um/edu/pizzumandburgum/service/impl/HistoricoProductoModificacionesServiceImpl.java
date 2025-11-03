package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.entities.HistoricoProductoModificaciones;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.repository.HistoricoProductoModificacionesRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.HistoricoProductoModificacionesService;

import java.time.LocalDateTime;

@Service
public class HistoricoProductoModificacionesServiceImpl implements HistoricoProductoModificacionesService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private HistoricoProductoModificacionesRepository historicoProductoModificacionesRepository;
    @Override
    public void registrarActualizacion(Producto pAnterior, Producto pNuevo) {
        Producto nuevo = productoRepository.findById(pNuevo.getIdProducto()).orElseThrow(ProductoNoExisteException::new);
        Producto anterior = productoRepository.findById(pAnterior.getIdProducto()).orElseThrow(ProductoNoExisteException::new);

        HistoricoProductoModificaciones historico = new HistoricoProductoModificaciones();

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
    }

    @Override
    public void RegistrarAgregar(Producto pNuevo) {
        Producto nuevo = productoRepository.findById(pNuevo.getIdProducto()).orElseThrow(ProductoNoExisteException::new);
        HistoricoProductoModificaciones historico = new HistoricoProductoModificaciones();
        historico.setNombreNuevo(nuevo.getNombre());
        historico.setPrecioNuevo(nuevo.getPrecio());
        historico.setTipoNuevo(nuevo.getTipo());
        historico.setSinTaccNuevo(nuevo.isSinTacc());
        historico.setFechaModificacion(LocalDateTime.now());
        historico.setTipoModificiacion("Agrego");

        historicoProductoModificacionesRepository.save(historico);
    }

    @Override
    public void RegistrarEliminar(Producto pAnterior) {
        Producto anterior = productoRepository.findById(pAnterior.getIdProducto()).orElseThrow(ProductoNoExisteException::new);

        HistoricoProductoModificaciones historico = new HistoricoProductoModificaciones();

        historico.setProducto(anterior);
        historico.setPrecioAnterior(anterior.getPrecio());
        historico.setNombreAnterior(anterior.getNombre());
        historico.setSinTaccAnterior(anterior.isSinTacc());
        historico.setTipoAnterior(anterior.getTipo());
        historico.setFechaModificacion(LocalDateTime.now());
        historico.setTipoModificiacion("Eliminado");

        historicoProductoModificacionesRepository.save(historico);

    }
}
