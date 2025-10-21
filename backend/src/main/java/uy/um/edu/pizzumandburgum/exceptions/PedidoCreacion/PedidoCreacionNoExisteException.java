package uy.um.edu.pizzumandburgum.exceptions.PedidoCreacion;

public class PedidoCreacionNoExisteException extends RuntimeException {
    public PedidoCreacionNoExisteException() {
        super("PedidoCreacion no existe");
    }
}
