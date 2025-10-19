package uy.um.edu.pizzumandburgum.exceptions;

public class PizzaNoExisteException extends RuntimeException {
    public PizzaNoExisteException() {

        super("Pizza no encontrada");
    }
}
