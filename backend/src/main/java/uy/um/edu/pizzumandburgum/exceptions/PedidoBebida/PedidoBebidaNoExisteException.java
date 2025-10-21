package uy.um.edu.pizzumandburgum.exceptions.PedidoBebida;

public class PedidoBebidaNoExisteException extends RuntimeException {
    public PedidoBebidaNoExisteException() {
        super("Pedido Bebida no encontrado");
    }
}
