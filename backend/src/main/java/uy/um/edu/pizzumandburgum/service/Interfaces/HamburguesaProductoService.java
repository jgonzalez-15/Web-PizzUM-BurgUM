package uy.um.edu.pizzumandburgum.service.Interfaces;

public interface HamburguesaProductoService {
    public void agregarIngrediente(Long idHamburguesa, Long idProducto, int cantidad);
    public float calcularPrecio(Long idhamburguesa);
}
