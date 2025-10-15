package uy.um.edu.pizzumandburgum.exceptions;

public class EmailYaRegistradoException extends RuntimeException{
    public EmailYaRegistradoException() {
        super("El email ingresado ya se encuentra registrado en el sistema.");
    }
}
