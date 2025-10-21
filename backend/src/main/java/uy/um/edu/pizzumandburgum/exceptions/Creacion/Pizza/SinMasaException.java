package uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza;

public class SinMasaException extends RuntimeException {
    public SinMasaException() {

        super("El tipo de masa es obligatorio");
    }
}
