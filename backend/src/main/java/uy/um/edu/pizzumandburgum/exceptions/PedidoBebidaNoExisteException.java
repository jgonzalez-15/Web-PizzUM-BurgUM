package uy.um.edu.pizzumandburgum.exceptions;

public class PedidoBebidaNoExisteException extends RuntimeException {
    public PedidoBebidaNoExisteException() {
        super("Pedido Bebida no encontrado");
    }
}
