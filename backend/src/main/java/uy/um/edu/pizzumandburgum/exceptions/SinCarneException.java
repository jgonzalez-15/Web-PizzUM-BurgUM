package uy.um.edu.pizzumandburgum.exceptions;

public class SinCarneException extends RuntimeException {
    public SinCarneException() {
        super("Debe elegir por lo menos una hamburguesa.");
    }
}
