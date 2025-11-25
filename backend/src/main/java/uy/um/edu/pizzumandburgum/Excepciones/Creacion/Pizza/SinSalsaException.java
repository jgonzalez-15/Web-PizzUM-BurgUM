package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Pizza;

public class SinSalsaException extends RuntimeException {
    public SinSalsaException() {

      super("El tipo de salsa es obligatorio");
    }
}
