package uy.um.edu.pizzumandburgum.exceptions.Bebida;

public class BebidaNoEncontradaException extends RuntimeException {
    public BebidaNoEncontradaException() {

        super("No se ha podido encontrar la bebida");
    }
}
