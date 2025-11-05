package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Producto.CampoObligatorioException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.PrecioNegativoException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoYaExisteException;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoProductoModificacionService;
import uy.um.edu.pizzumandburgum.service.Interfaces.ProductoService;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

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
        if (producto.getPrecio() == 0){
            throw new CampoObligatorioException("El precio es un campo obligatorio");
        }
        if (producto.getPrecio() < 0){
            throw new PrecioNegativoException();
        }
        if (productoRepository.findByNombre(producto.getNombre()).isPresent()) {
            throw new ProductoYaExisteException();
        }
        producto.setEstaActivo(true);
        productoRepository.save(producto);
        historicoService.RegistrarAgregar(producto);
        return productoMapper.toResponseDTO(producto);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto).orElseThrow(ProductoNoExisteException::new);
        producto.setEstaActivo(false);
        historicoService.RegistrarEliminar(producto);

    }

    @Override
    public void modificarProducto(ProductoRequestDTO productoviejoDTO, ProductoRequestDTO productonuevoDTO) {
        Producto productoViejo = productoMapper.toEntity(productoviejoDTO);
        Producto productoNuevo = new Producto();
        productoNuevo.setTipo(productonuevoDTO.getTipo());
        productoNuevo.setPrecio(productonuevoDTO.getPrecio());
        productoNuevo.setSinTacc(productonuevoDTO.isSinTacc());
        productoNuevo.setNombre(productonuevoDTO.getNombre());
        productoNuevo.setHistorico(productoViejo.getHistorico());
        productoRepository.save(productoViejo);
        historicoService.registrarActualizacion(productoViejo,productoNuevo);

    }

    @Override
    public List<ProductoResponseDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoResponseDTO> resultado = new ArrayList<>();

        for (Producto producto : productos) {
            if (producto.isEstaActivo()){
            ProductoResponseDTO dto = productoMapper.toResponseDTO(producto);
            resultado.add(dto);}
            else {
                throw new ProductoNoExisteException();
            }
        }

        return resultado;
        }

    @Override
    public List<ProductoResponseDTO> listarBebidas() {
        List<Producto> productos = productoRepository.findAll();
        List<Producto> bebidas = new ArrayList<>();
        List<ProductoResponseDTO> retornar = new ArrayList<>();
        for (Producto producto : productos){
            if (producto.isEstaActivo()){
            if (producto.getTipo().equals("Bebida")){
                bebidas.add(producto);
            }
            }
            else{
                throw new ProductoNoExisteException();
            }
        }
        for (Producto bebida : bebidas){
            ProductoResponseDTO b = productoMapper.toResponseDTO(bebida);
            retornar.add(b);
        }
        return retornar;
    }
}
