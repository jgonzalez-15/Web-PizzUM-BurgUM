package uy.um.edu.pizzumandburgum.Excepciones.Domicilio;

public class DomicilioNoExisteException extends RuntimeException {
  public DomicilioNoExisteException() {

    super("El domicilio no ha sido encontrado");
  }
}
