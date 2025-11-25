package uy.um.edu.pizzumandburgum.Excepciones.Favorito;

public class FavoritoYaExisteException extends RuntimeException {
    public FavoritoYaExisteException() {
        super("Este favorito ya existe");
    }
}
