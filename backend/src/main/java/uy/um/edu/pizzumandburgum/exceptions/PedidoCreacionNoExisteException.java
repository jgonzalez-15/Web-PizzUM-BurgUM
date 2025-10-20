package uy.um.edu.pizzumandburgum.exceptions;

public class PedidoCreacionNoExisteException extends RuntimeException {
    public PedidoCreacionNoExisteException() {
        super("PedidoCreacion no existe");
    }
}
