package uy.um.edu.pizzumandburgum.Excepciones.Pedido;

public class FechaInvalidaException extends RuntimeException {
    public FechaInvalidaException() {

        super("La fecha de inicio no puede ser posterior a la fecha de fin");
    }
}
