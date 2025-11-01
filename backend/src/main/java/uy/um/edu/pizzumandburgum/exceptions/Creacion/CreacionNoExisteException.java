package uy.um.edu.pizzumandburgum.exceptions.Creacion;

public class CreacionNoExisteException extends RuntimeException {
    public CreacionNoExisteException() {
        super("La creacion no existe");
    }
}
