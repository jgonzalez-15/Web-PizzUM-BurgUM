package uy.um.edu.pizzumandburgum.Excepciones.Pedido;

public class PedidoPagoException extends RuntimeException {
    public PedidoPagoException() {

      super("El pedido ya esta pago");
    }
}
