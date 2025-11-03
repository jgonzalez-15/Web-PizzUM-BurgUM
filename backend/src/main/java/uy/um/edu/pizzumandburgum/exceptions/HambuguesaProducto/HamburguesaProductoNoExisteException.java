package uy.um.edu.pizzumandburgum.exceptions.HambuguesaProducto;

public class HamburguesaProductoNoExisteException extends RuntimeException {
    public HamburguesaProductoNoExisteException() {

        super("HamburguesaProducto no existe");
    }
}
