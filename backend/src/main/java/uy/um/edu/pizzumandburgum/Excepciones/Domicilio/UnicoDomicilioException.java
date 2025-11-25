package uy.um.edu.pizzumandburgum.Excepciones.Domicilio;

public class UnicoDomicilioException extends RuntimeException {
    public UnicoDomicilioException() {

      super("No puedes eliminar tu único domicilio");
    }
}
