package uy.um.edu.pizzumandburgum.Excepciones.Usuario;

public class EmailYaRegistradoException extends RuntimeException{
    public EmailYaRegistradoException() {
        super("El email ingresado ya se encuentra registrado en el sistema.");
    }
}
