package uy.um.edu.pizzumandburgum.Excepciones.HambuguesaProducto;

public class HamburguesaProductoNoExisteException extends RuntimeException {
    public HamburguesaProductoNoExisteException() {

        super("HamburguesaProducto no existe");
    }
}
