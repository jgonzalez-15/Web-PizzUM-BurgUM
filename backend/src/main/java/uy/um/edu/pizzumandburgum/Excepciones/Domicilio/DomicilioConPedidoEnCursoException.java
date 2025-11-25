package uy.um.edu.pizzumandburgum.Excepciones.Domicilio;

public class DomicilioConPedidoEnCursoException extends RuntimeException {
    public DomicilioConPedidoEnCursoException() {
        super("No se puede eliminar el domicilio porque tiene pedidos en curso.");
    }
}
