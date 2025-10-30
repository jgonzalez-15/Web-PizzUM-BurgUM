package uy.um.edu.pizzumandburgum.exceptions.Usuario;

public class EmailNoExisteException extends RuntimeException {
    public EmailNoExisteException() {
        super("Email incorrecto.");
    }
}
