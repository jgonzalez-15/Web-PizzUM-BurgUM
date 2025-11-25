package uy.um.edu.pizzumandburgum.Excepciones.MedioDePago;

public class MedioDePagoNoExisteException extends RuntimeException {
    public MedioDePagoNoExisteException() {
        super("El medio de pago no esta registrado");
    }
}
