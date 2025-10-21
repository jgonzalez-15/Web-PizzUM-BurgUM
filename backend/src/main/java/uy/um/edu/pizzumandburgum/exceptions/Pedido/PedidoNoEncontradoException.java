package uy.um.edu.pizzumandburgum.exceptions.Pedido;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException() {
      super("No se ha encontrado el pedido.");
    }
}
