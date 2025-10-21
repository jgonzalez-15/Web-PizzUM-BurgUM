package uy.um.edu.pizzumandburgum.exceptions.Producto;

public class ProductoYaExisteException extends RuntimeException {
    public ProductoYaExisteException() {

        super("Producto ya existe");
    }
}
