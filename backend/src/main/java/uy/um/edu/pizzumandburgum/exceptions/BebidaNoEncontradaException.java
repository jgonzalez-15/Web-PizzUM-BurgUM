package uy.um.edu.pizzumandburgum.exceptions;

public class BebidaNoEncontradaException extends RuntimeException {
    public BebidaNoEncontradaException() {

        super("No se ha podido encontrar la bebida");
    }
}
