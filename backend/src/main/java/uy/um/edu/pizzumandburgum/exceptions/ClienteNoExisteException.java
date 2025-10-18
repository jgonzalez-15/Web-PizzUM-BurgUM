package uy.um.edu.pizzumandburgum.exceptions;

public class ClienteNoExisteException extends RuntimeException {
    public ClienteNoExisteException() {

        super("Cliente no Existe");
    }
}
