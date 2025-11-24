package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaProductoMapper;
import uy.um.edu.pizzumandburgum.repository.HamburguesaProductoRepository;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.HamburguesaProductoService;

@Service
public class HamburguesaProductoServiceImpl implements HamburguesaProductoService {

    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    @Autowired
    private HamburguesaProductoRepository hamburguesaProductoRepository;

    @Autowired
    private HamburguesaProductoMapper hamburguesaProductoMapper;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public HamburguesaProductoResponseDTO agregarIngrediente(Long idHamburguesa,HamburguesaProductoRequestDTO dto) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa).orElseThrow(HamburguesaNoEncontradaException::new);
        Producto producto = productoRepository.findById(dto.getIdProducto()).orElseThrow(ProductoNoExisteException::new);
        HamburguesaProducto hp = new HamburguesaProducto();
        hp.setHamburguesa(hamburguesa);
        hp.setProducto(producto);
        hp.setCantidad(dto.getCantidad());
        HamburguesaProducto guardado = hamburguesaProductoRepository.save(hp);
        return hamburguesaProductoMapper.toResponseDTO(guardado);
    }

    @Override
    public float calcularPrecio(Long idhamburguesa) {
        float precio = 0;
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idhamburguesa).orElseThrow(HamburguesaNoEncontradaException::new);

        for (HamburguesaProducto hp : hamburguesa.getIngredientes()){
            Producto producto = productoRepository.findById(hp.getProducto().getIdProducto()).orElseThrow(ProductoNoExisteException::new);
            precio += producto.getPrecio() * hp.getCantidad();
        }

        return precio;
    }


}
