package uy.um.edu.pizzumandburgum.Excepciones.Usuario.Administrador;

public class AdministradorNoExiste extends RuntimeException {
    public AdministradorNoExiste() {

        super("El administrador no existe.");
    }
}
