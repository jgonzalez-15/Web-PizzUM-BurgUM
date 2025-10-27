package uy.um.edu.pizzumandburgum.exceptions.Pedido;

public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException() {

      super("El estado es invalido");
    }
}
