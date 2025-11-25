package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Pizza;

public class SinMasaException extends RuntimeException {
    public SinMasaException() {

        super("El tipo de masa es obligatorio");
    }
}
