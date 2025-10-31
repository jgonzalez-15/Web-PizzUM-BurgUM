package uy.um.edu.pizzumandburgum.exceptions.Calificacion;

public class CalificacionNoExisteException extends RuntimeException {
    public CalificacionNoExisteException() {

        super("No existe calificacion previa.");
    }
}
