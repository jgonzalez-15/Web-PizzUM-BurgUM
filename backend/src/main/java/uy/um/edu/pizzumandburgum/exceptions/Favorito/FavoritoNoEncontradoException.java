package uy.um.edu.pizzumandburgum.exceptions.Favorito;

public class FavoritoNoEncontradoException extends RuntimeException {
    public FavoritoNoEncontradoException() {
        super("No se encontró el favorito");
    }
}
