package uy.um.edu.pizzumandburgum.exceptions;

public class PizzaProductoNoExisteException extends RuntimeException {
    public PizzaProductoNoExisteException() {

        super("Pizza Producto no existe");
    }
}
