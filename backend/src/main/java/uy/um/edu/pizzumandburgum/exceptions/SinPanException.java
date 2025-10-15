package uy.um.edu.pizzumandburgum.exceptions;

public class SinPanException extends RuntimeException {
    public SinPanException() {
        super("La hamburguesa debe tener pan");
    }
}
