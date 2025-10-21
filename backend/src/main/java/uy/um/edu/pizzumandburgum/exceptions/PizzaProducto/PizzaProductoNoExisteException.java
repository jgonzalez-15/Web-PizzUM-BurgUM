package uy.um.edu.pizzumandburgum.exceptions.PizzaProducto;

public class PizzaProductoNoExisteException extends RuntimeException {
    public PizzaProductoNoExisteException() {

        super("Pizza Producto no existe");
    }
}
