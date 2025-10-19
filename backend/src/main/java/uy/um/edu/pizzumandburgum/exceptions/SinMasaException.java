package uy.um.edu.pizzumandburgum.exceptions;

public class SinMasaException extends RuntimeException {
    public SinMasaException() {

        super("El tipo de masa es obligatorio");
    }
}
