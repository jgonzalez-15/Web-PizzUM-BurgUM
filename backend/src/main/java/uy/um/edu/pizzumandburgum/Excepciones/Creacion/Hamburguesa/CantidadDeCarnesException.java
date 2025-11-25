package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Hamburguesa;

public class CantidadDeCarnesException extends RuntimeException {
    public CantidadDeCarnesException() {
        super("Limite de cantidad de carnes superado.");
    }
}
