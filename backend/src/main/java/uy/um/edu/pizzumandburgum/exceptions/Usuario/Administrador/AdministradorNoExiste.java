package uy.um.edu.pizzumandburgum.exceptions.Usuario.Administrador;

public class AdministradorNoExiste extends RuntimeException {
    public AdministradorNoExiste() {

        super("El administrador no existe.");
    }
}
