package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
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
    public void agregarIngrediente(Long idHamburguesa, Long idProducto, int cantidad) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa).orElseThrow(()-> new HamburguesaNoEncontradaException());
        Producto producto = productoRepository.findById(idProducto).orElseThrow(()->new ProductoNoExisteException());
        HamburguesaProducto hp = new HamburguesaProducto();
        hp.setHamburguesa(hamburguesa);
        hp.setProducto(producto);
        hp.setCantidad(cantidad);
        hamburguesaProductoRepository.save(hp);
    }

    @Override
    public float calcularPrecio(Long idhamburguesa) {
        float precio = 0;
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idhamburguesa).orElseThrow(() -> new HamburguesaNoEncontradaException());

        for (HamburguesaProducto hp : hamburguesa.getIngredientes()){
            Producto producto = productoRepository.findById(hp.getProducto().getIdProducto()).orElseThrow(()->new  ProductoNoExisteException());
            precio += producto.getPrecio() * hp.getCantidad();
        }

        return precio;
    }


}
