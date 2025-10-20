package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.*;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaMapper;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaProductoMapper;
import uy.um.edu.pizzumandburgum.repository.HamburguesaProductoRepository;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.HamburguesaProductoService;
import uy.um.edu.pizzumandburgum.service.HamburguesaService;

@Service
public class HamburguesaServiceImpl implements HamburguesaService {
    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    @Autowired
    private HamburguesaMapper hamburguesaMapper;

    @Autowired
    private HamburguesaProductoService hamburguesaProductoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private HamburguesaProductoMapper hamburguesaProductoMapper;

    @Autowired
    private HamburguesaProductoRepository hamburguesaProductoRepository;


    @Override
    public HamburguesaResponseDTO crearHamburguesa(Long idHamburguesa) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa).orElseThrow(()-> new HamburguesaNoEncontradaException());

        if (hamburguesa.getCantCarnes()>3){
            throw new CantidadDeCarnesException();
        } else if (hamburguesa.getCantCarnes()==0) {
           throw new SinCarneException();
        }
        boolean tienePan = false;

        for (HamburguesaProducto hp: hamburguesa.getIngredientes()){
            Producto p =productoRepository.findById(hp.getProducto().getIdProducto()).orElseThrow(() -> new ProductoNoExisteException()) ;
            if ("Pan".equalsIgnoreCase(p.getTipo())){
                tienePan = true;
            }
        }
        if (!tienePan){
            throw new SinPanException();
        }
        float precio = this.fijarPrecio(hamburguesa.getIdCreacion());
        hamburguesa.setPrecio(precio);
        Hamburguesa guardado = hamburguesaRepository.save(hamburguesa);

        // Delegamos la creación de ingredientes al service especializado
        for (HamburguesaProducto hp : hamburguesa.getIngredientes()) {
            Producto producto = productoRepository.findById(hp.getProducto().getIdProducto()).orElseThrow(()->new ProductoNoExisteException());
            hamburguesaProductoService.agregarIngrediente(
                    guardado.getIdCreacion(),
                    hp.getProducto().getIdProducto(),
                    hp.getCantidad()
            );
        }
        return hamburguesaMapper.toResponseDTO(guardado);
    }

    @Override
    public float fijarPrecio(Long idHamburguesa) {
        Hamburguesa hamburguesa = hamburguesaRepository.findById(idHamburguesa)
                .orElseThrow(() -> new HamburguesaNoEncontradaException());

        float precioTotal = hamburguesaProductoService.calcularPrecio(idHamburguesa);
        hamburguesa.setPrecio(precioTotal);
        hamburguesaRepository.save(hamburguesa);
        return precioTotal;
    }


}
