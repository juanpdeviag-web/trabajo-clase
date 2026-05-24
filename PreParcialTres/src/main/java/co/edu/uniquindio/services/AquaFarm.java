package co.edu.uniquindio.services;

// AquaFarm.java
import co.edu.uniquindio.dto.ResultadoAnalisisVelocidad;
import co.edu.uniquindio.model.*;

import java.util.List;

public class AquaFarm {

    // Punto 2.1 con Streams: Extrae, ordena y calcula el promedio
    public ResultadoAnalisisVelocidad procesarVelocidades(List<DronDeSuperficie> drones) {
        if (drones == null || drones.isEmpty()) {
            return new ResultadoAnalisisVelocidad(List.of(), 0.0);
        }

        // 1. Extraer y ordenar velocidades usando la tubería Stream
        List<Double> velocidades = drones.stream()
                .map(DronDeSuperficie::getVelocidadMediaOperacion) // Extrae (Referencia a método)
                .sorted()                                         // Ordena de forma ascendente
                .toList();                                        // Guarda en lista inmutable

        // 2. Calcular promedio usando la herramienta matemática de los streams
        double promedio = drones.stream()
                .mapToDouble(DronDeSuperficie::getVelocidadMediaOperacion)
                .average()                                        // Calcula promedio automáticamente
                .orElse(0.0);                                     // Si falla, devuelve 0.0

        return new ResultadoAnalisisVelocidad(velocidades, promedio);
    }

    // Punto 2.2 con Streams: Filtra directamente por la condición
    public List<Double> filtrarVelocidadesRapidas(List<DronDeSuperficie> drones) {
        if (drones == null) return List.of();

        return drones.stream()
                .filter(d -> d.getVelocidadMediaOperacion() > (d.getAlcanceMaximoVuelo() * 0.5)) // Condición
                .map(DronDeSuperficie::getVelocidadMediaOperacion)                             // Extrae
                .toList();                                                                      // Guarda
    }
}
