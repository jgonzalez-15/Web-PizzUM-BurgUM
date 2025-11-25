package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Producto;
import uy.um.edu.pizzumandburgum.Excepciones.Producto.CampoObligatorioException;
import uy.um.edu.pizzumandburgum.Excepciones.Producto.PrecioNegativoException;
import uy.um.edu.pizzumandburgum.Excepciones.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Producto.ProductoYaExisteException;
import uy.um.edu.pizzumandburgum.Mappers.ProductoMapper;
import uy.um.edu.pizzumandburgum.Repositorios.ProductoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos.HistoricoProductoModificacionService;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private ProductoCreacionUpdaterService productoCreacionUpdater;

    @Autowired
    private HistoricoProductoModificacionService historicoService;

    @Override
    public ProductoResponseDTO agregarProducto(ProductoRequestDTO productoDTO) {

        Producto producto = productoMapper.toEntity(productoDTO);
        if (producto.getNombre() == null){
            throw new CampoObligatorioException("El nombre es un campo obligatorio");
        }
        if (producto.getTipo() == null){
            throw new CampoObligatorioException("El tipo es un campo obligatorio");
        }
        if (producto.isVisible() && (producto.getPrecio() == null || producto.getPrecio() <= 0)) {
            throw new CampoObligatorioException("El precio es un campo obligatorio para productos visibles");
        }
        if (producto.getPrecio() < 0){
            throw new PrecioNegativoException();
        }
        if (productoRepository.findByNombre(producto.getNombre())
                .filter(p -> p.isEstaActivo())
                .isPresent()) {
            throw new ProductoYaExisteException();
        }

        producto.setEstaActivo(true);
        producto.setVisible(productoDTO.isVisible());
        productoRepository.save(producto);
        historicoService.RegistrarAgregar(producto);
        return productoMapper.toResponseDTO(producto);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        Producto producto = productoRepository.findByIdProducto(idProducto).orElseThrow(ProductoNoExisteException::new);
        producto.setEstaActivo(false);
        productoRepository.save(producto);
        historicoService.RegistrarEliminar(producto);
    }

    @Override
    public void ocultarProducto(Long idProducto) {
        Producto producto = productoRepository.findByIdProducto(idProducto).orElseThrow(ProductoNoExisteException::new);
        producto.setVisible(false);
        productoRepository.save(producto);
        historicoService.RegistrarOculto(producto);
    }

    @Override
    public void mostrarProducto(Long idProducto) {
        Producto producto = productoRepository.findByIdProducto(idProducto).orElseThrow(ProductoNoExisteException::new);
        producto.setVisible(true);
        productoRepository.save(producto);
    }

    @Override
    public ProductoResponseDTO editarProducto(Long idProducto, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findByIdProducto(idProducto).orElseThrow(ProductoNoExisteException::new);

        Float precioViejo = producto.getPrecio();

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setSinTacc(dto.isSinTacc());
        producto.setVisible(dto.isVisible());
        producto.setTipo(dto.getTipo());

        productoRepository.save(producto);

        if (!Objects.equals(precioViejo, producto.getPrecio())) {
            productoCreacionUpdater.actualizarPreciosPorProducto(producto);
        }

        historicoService.registrarActualizacion(producto, producto);

        return productoMapper.toResponseDTO(producto);
    }

        @Override
    public List<ProductoResponseDTO> listarProductos() {
        List<ProductoResponseDTO> resultado = new ArrayList<>();
        for (Producto producto : productoRepository.findAll()) {
            if (producto.isEstaActivo() && producto.isVisible()) {
                resultado.add(productoMapper.toResponseDTO(producto));
            }
        }
        return resultado;
    }

    @Override
    public List<ProductoResponseDTO> listarProductosAdmin() {
        List<ProductoResponseDTO> resultado = new ArrayList<>();
        for (Producto producto : productoRepository.findAll()) {
            if (producto.isEstaActivo()) {
                resultado.add(productoMapper.toResponseDTO(producto));
            }
        }
        return resultado;
    }


    @Override
    public List<ProductoResponseDTO> listarBebidas() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoResponseDTO> retornar = new ArrayList<>();

        for (Producto producto : productos) {
            if (producto.isEstaActivo() && producto.isVisible() && "Bebida".equalsIgnoreCase(producto.getTipo())) {
                ProductoResponseDTO dto = productoMapper.toResponseDTO(producto);
                retornar.add(dto);
            }
        }

        return retornar;
    }
}
