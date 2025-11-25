package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Hamburguesa;

public class SinCarneException extends RuntimeException {
    public SinCarneException() {
        super("Debe elegir por lo menos una hamburguesa.");
    }
}
