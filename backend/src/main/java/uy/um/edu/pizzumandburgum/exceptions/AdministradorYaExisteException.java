package uy.um.edu.pizzumandburgum.exceptions;

public class AdministradorYaExisteException extends RuntimeException {
    public AdministradorYaExisteException() {
        super("Administrador ya existe");
    }
}
