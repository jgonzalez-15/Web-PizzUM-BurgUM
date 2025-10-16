package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaProductoMapper;
import uy.um.edu.pizzumandburgum.repository.HamburguesaProductoRepository;
import uy.um.edu.pizzumandburgum.service.HamburguesaProductoService;

@Service
public class HamburguesaProductoServiceImpl implements HamburguesaProductoService {

    @Autowired
    private HamburguesaProductoRepository hamburguesaProductoRepository;

    @Autowired
    private HamburguesaProductoMapper hamburguesaProductoMapper;
    @Override
    public void agregarIngrediente(Hamburguesa hamburguesa, Producto producto, int cantidad) {
        HamburguesaProducto hp = new HamburguesaProducto();
        hp.setHamburguesa(hamburguesa);
        hp.setProducto(producto);
        hp.setCantidad(cantidad);
        hamburguesaProductoRepository.save(hp);
    }
}
