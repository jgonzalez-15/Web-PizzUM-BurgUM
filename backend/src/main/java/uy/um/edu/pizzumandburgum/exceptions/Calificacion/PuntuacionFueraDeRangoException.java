package uy.um.edu.pizzumandburgum.exceptions.Calificacion;

public class PuntuacionFueraDeRangoException extends RuntimeException {
    public PuntuacionFueraDeRangoException() {

        super("Puntuacion fuera de rango.");
    }
}
