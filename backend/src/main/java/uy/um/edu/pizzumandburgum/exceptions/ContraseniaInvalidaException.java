package uy.um.edu.pizzumandburgum.exceptions;

public class ContraseniaInvalidaException extends RuntimeException {
    public ContraseniaInvalidaException() {

        super("Contraseña Incorrecta");
    }
}
