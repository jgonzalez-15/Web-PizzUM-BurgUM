package uy.um.edu.pizzumandburgum.exceptions.Domicilio;

public class SinAccesoAlDomicilioException extends RuntimeException {
  public SinAccesoAlDomicilioException() {

    super("No tienes acceso a este domicilio");
  }
}
