package uy.um.edu.pizzumandburgum.exceptions.Favorito;

public class FavoritoYaExisteException extends RuntimeException {
    public FavoritoYaExisteException() {
        super("Este favorito ya existe");
    }
}
