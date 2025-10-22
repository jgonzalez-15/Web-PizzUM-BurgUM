package uy.um.edu.pizzumandburgum.exceptions.Pedido;

public class PedidoPagoException extends RuntimeException {
    public PedidoPagoException() {

      super("El pedido ya esta pago");
    }
}
