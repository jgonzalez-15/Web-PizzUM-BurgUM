package uy.um.edu.pizzumandburgum.exceptions.Domicilio;

public class PorLoMenosUnDomicilioException extends RuntimeException {
  public PorLoMenosUnDomicilioException() {
    super("El cliente debe tener al menos un domicilio registrado.");
  }
}
