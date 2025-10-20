package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.Producto;

public interface HamburguesaProductoService {
    public void agregarIngrediente(Long idHamburguesa, Long idProducto, int cantidad);
    public float calcularPrecio(Long idhamburguesa);
}
