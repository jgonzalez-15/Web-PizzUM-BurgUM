package uy.um.edu.pizzumandburgum.exceptions;

public class ProductoYaExisteException extends RuntimeException {
    public ProductoYaExisteException() {

        super("Producto ya existe");
    }
}
