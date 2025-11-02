package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoYaExisteException;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ProductoService;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoResponseDTO agregarProducto(ProductoRequestDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        if (productoRepository.findByNombre(producto.getNombre()).isPresent()) {
            throw new ProductoYaExisteException();
        }
        productoRepository.save(producto);
        return productoMapper.toResponseDTO(producto);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        if (!productoRepository.existsById(idProducto)){
            throw new ProductoNoExisteException();
        }
        productoRepository.deleteById(idProducto);
    }

    @Override
    public void modificarProducto(ProductoRequestDTO productoviejoDTO, ProductoRequestDTO productonuevoDTO) {
        Producto producto = productoMapper.toEntity(productoviejoDTO);
        producto.setTipo(productonuevoDTO.getTipo());
        producto.setPrecio(productonuevoDTO.getPrecio());
        producto.setSinTacc(productonuevoDTO.isSinTacc());
        producto.setNombre(productonuevoDTO.getNombre());
        productoRepository.save(producto);
    }

    @Override
    public List<ProductoResponseDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoResponseDTO> resultado = new ArrayList<>();

        for (Producto producto : productos) {
            ProductoResponseDTO dto = productoMapper.toResponseDTO(producto);
            resultado.add(dto);
        }

        return resultado;
        }

    @Override
    public List<ProductoResponseDTO> listarBebidas() {
        List<Producto> productos = productoRepository.findAll();
        List<Producto> bebidas = new ArrayList<>();
        List<ProductoResponseDTO> retornar = new ArrayList<>();
        for (Producto producto : productos){
            if (producto.getTipo().equals("Bebida")){
                bebidas.add(producto);
            }
        }
        for (Producto bebida : bebidas){
            ProductoResponseDTO b = productoMapper.toResponseDTO(bebida);
            retornar.add(b);
        }
        return retornar;
    }
}
