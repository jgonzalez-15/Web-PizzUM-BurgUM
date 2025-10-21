package uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente;

public class ClienteNoExisteException extends RuntimeException {
    public ClienteNoExisteException() {

        super("Cliente no Existe");
    }
}
