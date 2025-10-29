package uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza;

public class SinSalsaException extends RuntimeException {
    public SinSalsaException() {

      super("El tipo de salsa es obligatorio");
    }
}
