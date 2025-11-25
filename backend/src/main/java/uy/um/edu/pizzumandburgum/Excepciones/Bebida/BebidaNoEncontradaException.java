package uy.um.edu.pizzumandburgum.Excepciones.Bebida;

public class BebidaNoEncontradaException extends RuntimeException {
    public BebidaNoEncontradaException() {

        super("No se ha podido encontrar la bebida");
    }
}
