package uy.um.edu.pizzumandburgum.Excepciones.Producto;

public class ProductoNoExisteException extends RuntimeException {
    public ProductoNoExisteException() {
        super("Producto no existe");
    }
}
