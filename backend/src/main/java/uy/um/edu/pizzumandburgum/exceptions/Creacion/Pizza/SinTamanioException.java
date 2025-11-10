package uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza;

public class SinTamanioException extends RuntimeException {
    public SinTamanioException() {

        super("El tamanio de pizza es obligatorio");
    }
}
