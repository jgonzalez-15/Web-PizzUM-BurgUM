package uy.um.edu.pizzumandburgum.exceptions.Usuario;

public class ContraseniaInvalidaException extends RuntimeException {
    public ContraseniaInvalidaException() {

        super("Contraseña Incorrecta");
    }
}
