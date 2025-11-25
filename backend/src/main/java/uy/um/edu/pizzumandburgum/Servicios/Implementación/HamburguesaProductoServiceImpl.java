package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Hamburguesa;
import uy.um.edu.pizzumandburgum.Entidades.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.Entidades.Producto;
import uy.um.edu.pizzumandburgum.Excepciones.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.Excepciones.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.Mappers.HamburguesaProductoMapper;
import uy.um.edu.pizzumandburgum.Repositorios.HamburguesaProductoRepository;
import uy.um.edu.pizzumandburgum.Repositorios.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.Repositorios.ProductoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.HamburguesaProductoService;

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
