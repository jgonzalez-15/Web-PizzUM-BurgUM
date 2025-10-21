package uy.um.edu.pizzumandburgum.exceptions.Creacion;

public class CreacionNoEncontradaException extends RuntimeException {
    public CreacionNoEncontradaException() {

      super("Su creacion no ha sido encontrada");
    }
}
