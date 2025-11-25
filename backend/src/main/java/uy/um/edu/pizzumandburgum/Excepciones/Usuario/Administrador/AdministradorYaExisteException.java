package uy.um.edu.pizzumandburgum.Excepciones.Usuario.Administrador;

public class AdministradorYaExisteException extends RuntimeException {
    public AdministradorYaExisteException() {
        super("Administrador ya existe");
    }
}
