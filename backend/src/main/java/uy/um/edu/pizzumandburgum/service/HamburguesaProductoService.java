package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.Producto;

public interface HamburguesaProductoService {
    public void agregarIngrediente(Hamburguesa hamburguesa, Producto producto, int cantidad);
    public float calcularPrecio(HamburguesaResponseDTO hamburguesaResponseDTO);
}
