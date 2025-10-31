package uy.um.edu.pizzumandburgum.exceptions.Calificacion;

public class CalificacionYaExisteException extends RuntimeException {
    public CalificacionYaExisteException() {
        super("El pedido ya ha sido calificado");
    }
}
