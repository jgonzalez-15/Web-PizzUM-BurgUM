package uy.um.edu.pizzumandburgum.Excepciones.Producto;

public class PrecioNegativoException extends RuntimeException {
    public PrecioNegativoException() {
        super("El precio debe ser mayor que 0");
    }
}
