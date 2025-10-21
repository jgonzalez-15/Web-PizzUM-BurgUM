package uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa;

public class HamburguesaNoEncontradaException extends RuntimeException {
    public HamburguesaNoEncontradaException() {

        super("Hamburguesa no encontrada.");
    }
}
