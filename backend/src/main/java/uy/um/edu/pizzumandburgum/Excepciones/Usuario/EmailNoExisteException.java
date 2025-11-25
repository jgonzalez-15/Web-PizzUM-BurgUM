package uy.um.edu.pizzumandburgum.Excepciones.Usuario;

public class EmailNoExisteException extends RuntimeException {
    public EmailNoExisteException() {
        super("Email incorrecto.");
    }
}
