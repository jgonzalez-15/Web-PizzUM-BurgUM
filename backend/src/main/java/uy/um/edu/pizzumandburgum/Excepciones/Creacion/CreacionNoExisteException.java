package uy.um.edu.pizzumandburgum.Excepciones.Creacion;

public class CreacionNoExisteException extends RuntimeException {
    public CreacionNoExisteException() {
        super("La creacion no existe");
    }
}
