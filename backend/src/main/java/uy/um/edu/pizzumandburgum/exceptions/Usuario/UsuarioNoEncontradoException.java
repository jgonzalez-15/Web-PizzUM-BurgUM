package uy.um.edu.pizzumandburgum.exceptions.Usuario;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException() {
        super("No se ha encontrado el usuario.");
    }
}
