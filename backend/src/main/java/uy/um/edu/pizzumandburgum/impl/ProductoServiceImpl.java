package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.ProductoYaExisteException;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.ProductoService;


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
}
