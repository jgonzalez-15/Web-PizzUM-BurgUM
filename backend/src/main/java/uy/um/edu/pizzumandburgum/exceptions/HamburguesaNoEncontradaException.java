package uy.um.edu.pizzumandburgum.exceptions;

public class HamburguesaNoEncontradaException extends RuntimeException {
    public HamburguesaNoEncontradaException() {

        super("Hamburguesa no encontrada.");
    }
}
