package uy.um.edu.pizzumandburgum.Excepciones.Producto;

public class ProductoYaExisteException extends RuntimeException {
    public ProductoYaExisteException() {

        super("Producto ya existe");
    }
}
