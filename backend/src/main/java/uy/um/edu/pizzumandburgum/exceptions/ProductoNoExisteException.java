package uy.um.edu.pizzumandburgum.exceptions;

public class ProductoNoExisteException extends RuntimeException {
    public ProductoNoExisteException() {
        super("Producto no existe");
    }
}
