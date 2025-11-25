package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Hamburguesa;

public class HamburguesaNoEncontradaException extends RuntimeException {
    public HamburguesaNoEncontradaException() {

        super("Hamburguesa no encontrada.");
    }
}
