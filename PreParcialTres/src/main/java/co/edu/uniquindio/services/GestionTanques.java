package co.edu.uniquindio.services;

import co.edu.uniquindio.model.TanqueCultivo;

import java.util.Comparator;
import java.util.List;

public class GestionTanques {

    List<TanqueCultivo> tanque;

    public List<Double> obtenerTemperaturasCriticas (List<TanqueCultivo> tanque){

        List<Double> tanquesCalientes =

        tanque.stream()
                .filter( caliente -> caliente.getTemperaturaCelsius() > 24)
                .map(TanqueCultivo::getCapacidadMaxima)
                .sorted(Comparator.reverseOrder())
                .toList();

        return tanquesCalientes;
    }

    public double calcularNivelPromedio (List<TanqueCultivo> tanque){
        double promedio = tanque.stream()
                .mapToDouble(TanqueCultivo::getNivelAguaActual)
                .average()
                .orElse(0.0);
        return promedio;
    }

}
