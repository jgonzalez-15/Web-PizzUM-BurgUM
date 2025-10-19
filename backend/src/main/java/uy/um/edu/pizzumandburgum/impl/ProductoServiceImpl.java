package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.ProductoYaExisteException;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.ProductoService;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoDTO agregarProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        if (productoRepository.findByNombre(producto.getNombre()).isPresent()) {
            throw new ProductoYaExisteException();
        }
        productoRepository.save(producto);
        return productoMapper.toResponseDTO(producto);
    }

    @Override
    public void eliminarProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        if (!productoRepository.existsById(producto.getIdProducto())){
            throw new ProductoNoExisteException();
        }
        productoRepository.deleteById(producto.getIdProducto());
    }

    @Override
    public void modificarProducto(ProductoDTO productoviejoDTO, ProductoDTO productonuevoDTO) {
        Producto producto = productoMapper.toEntity(productoviejoDTO);
        producto.setTipo(productonuevoDTO.getTipo());
        producto.setPrecio(productonuevoDTO.getPrecio());
        producto.setSinTacc(productonuevoDTO.isSinTacc());
        producto.setNombre(productonuevoDTO.getNombre());
        productoRepository.save(producto);
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> resultado = new ArrayList<>();

        for (Producto producto : productos) {
            ProductoDTO dto = productoMapper.toResponseDTO(producto);
            resultado.add(dto);
        }

        return resultado;
        }
    }
