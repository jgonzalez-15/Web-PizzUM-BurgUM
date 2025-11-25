package uy.um.edu.pizzumandburgum.Excepciones.Favorito;

public class FavoritoNoEncontradoException extends RuntimeException {
    public FavoritoNoEncontradoException() {
        super("No se encontró el favorito");
    }
}
