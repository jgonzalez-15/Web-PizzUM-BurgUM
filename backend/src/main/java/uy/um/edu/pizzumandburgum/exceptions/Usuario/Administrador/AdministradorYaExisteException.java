package uy.um.edu.pizzumandburgum.exceptions.Usuario.Administrador;

public class AdministradorYaExisteException extends RuntimeException {
    public AdministradorYaExisteException() {
        super("Administrador ya existe");
    }
}
