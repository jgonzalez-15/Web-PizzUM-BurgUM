package uy.um.edu.pizzumandburgum.exceptions.Producto;

public class ProductoNoExisteException extends RuntimeException {
    public ProductoNoExisteException() {
        super("Producto no existe");
    }
}
