package uy.um.edu.pizzumandburgum.Excepciones.Usuario.Cliente;

public class ClienteNoExisteException extends RuntimeException {
    public ClienteNoExisteException() {

        super("Cliente no Existe");
    }
}
