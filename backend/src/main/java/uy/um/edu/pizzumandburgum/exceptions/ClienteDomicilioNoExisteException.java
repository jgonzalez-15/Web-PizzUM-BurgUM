package uy.um.edu.pizzumandburgum.exceptions;

public class ClienteDomicilioNoExisteException extends RuntimeException {
    public ClienteDomicilioNoExisteException() {

        super("ClienteDomicilio no existe");
    }
}
