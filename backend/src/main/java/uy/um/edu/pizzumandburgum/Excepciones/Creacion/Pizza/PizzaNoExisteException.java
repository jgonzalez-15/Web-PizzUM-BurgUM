package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Pizza;

public class PizzaNoExisteException extends RuntimeException {
    public PizzaNoExisteException() {

        super("Pizza no encontrada");
    }
}
