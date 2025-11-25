package uy.um.edu.pizzumandburgum.Excepciones.Usuario;

public class ContraseniaInvalidaException extends RuntimeException {
    public ContraseniaInvalidaException() {

        super("Contraseña Incorrecta");
    }
}
