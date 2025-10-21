package uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza;

public class PizzaNoExisteException extends RuntimeException {
    public PizzaNoExisteException() {

        super("Pizza no encontrada");
    }
}
