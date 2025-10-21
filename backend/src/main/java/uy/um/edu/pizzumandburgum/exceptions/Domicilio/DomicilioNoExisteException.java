package uy.um.edu.pizzumandburgum.exceptions.Domicilio;

public class DomicilioNoExisteException extends RuntimeException {
  public DomicilioNoExisteException() {

    super("El domicilio no ha sido encontrado");
  }
}
