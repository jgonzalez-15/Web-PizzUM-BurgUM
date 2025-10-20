package uy.um.edu.pizzumandburgum.exceptions;

public class MedioDePagoNoExisteException extends RuntimeException {
    public MedioDePagoNoExisteException() {
        super("El medio de pago no esta registrado");
    }
}
