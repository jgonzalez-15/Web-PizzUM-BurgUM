package uy.um.edu.pizzumandburgum.Excepciones.Pedido;

public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException() {

      super("El estado es invalido");
    }
}
