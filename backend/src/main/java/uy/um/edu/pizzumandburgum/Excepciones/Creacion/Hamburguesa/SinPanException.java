package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Hamburguesa;

public class SinPanException extends RuntimeException {
    public SinPanException() {
        super("La hamburguesa debe tener pan");
    }
}
