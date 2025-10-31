package uy.um.edu.pizzumandburgum.exceptions.Calificacion;

public class PedidoNoEntregadoException extends RuntimeException {
    public PedidoNoEntregadoException() {

      super("El pedido aun no fue entregado. No se puede calificar aun");
    }
}
