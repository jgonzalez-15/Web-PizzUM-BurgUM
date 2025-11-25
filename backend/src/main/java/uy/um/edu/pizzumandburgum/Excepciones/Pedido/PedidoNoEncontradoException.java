package uy.um.edu.pizzumandburgum.Excepciones.Pedido;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException() {
      super("No se ha encontrado el pedido.");
    }
}
