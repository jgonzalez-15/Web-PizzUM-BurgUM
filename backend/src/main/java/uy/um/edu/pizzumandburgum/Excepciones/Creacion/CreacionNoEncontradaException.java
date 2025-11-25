package uy.um.edu.pizzumandburgum.Excepciones.Creacion;

public class CreacionNoEncontradaException extends RuntimeException {
    public CreacionNoEncontradaException() {

      super("Su creacion no ha sido encontrada");
    }
}
