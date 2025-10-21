package uy.um.edu.pizzumandburgum.exceptions.ClienteDomicilio;

public class ClienteDomicilioNoExisteException extends RuntimeException {
    public ClienteDomicilioNoExisteException() {

        super("ClienteDomicilio no existe");
    }
}
