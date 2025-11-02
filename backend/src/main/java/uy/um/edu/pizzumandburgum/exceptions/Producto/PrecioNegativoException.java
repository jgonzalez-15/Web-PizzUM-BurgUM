package uy.um.edu.pizzumandburgum.exceptions.Producto;

public class PrecioNegativoException extends RuntimeException {
    public PrecioNegativoException() {
        super("El precio debe ser mayor que 0");
    }
}
