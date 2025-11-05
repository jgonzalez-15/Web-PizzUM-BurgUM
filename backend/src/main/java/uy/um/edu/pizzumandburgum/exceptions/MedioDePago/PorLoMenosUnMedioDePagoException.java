package uy.um.edu.pizzumandburgum.exceptions.MedioDePago;

public class PorLoMenosUnMedioDePagoException extends RuntimeException {
    public PorLoMenosUnMedioDePagoException() {
        super("El cliente debe tener por lo menos un medio de pago.");
    }
}
