package uy.um.edu.pizzumandburgum.exceptions;

public class CreacionNoEncontradaException extends RuntimeException {
    public CreacionNoEncontradaException() {

      super("Su creacion no ha sido encontrada");
    }
}
