package uy.um.edu.pizzumandburgum.exceptions;

public class CantidadDeCarnesException extends RuntimeException {
    public CantidadDeCarnesException() {
        super("Limite de cantidad de carnes superado.");
    }
}
