package uy.um.edu.pizzumandburgum.Excepciones.Creacion.Pizza;

public class SinTamanioException extends RuntimeException {
    public SinTamanioException() {

        super("El tamanio de pizza es obligatorio");
    }
}
