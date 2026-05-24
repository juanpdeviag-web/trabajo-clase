package co.edu.uniquindio;

// Pruebas.java
import co.edu.uniquindio.dto.ResultadoAnalisisVelocidad;
import co.edu.uniquindio.enums.TipoMonitoreo;
import co.edu.uniquindio.model.*;
import co.edu.uniquindio.services.AquaFarm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Pruebas {

    public void ejecutarPruebasVelocidades() {
        AquaFarm empresa = new AquaFarm();

        // Creamos la lista y los objetos de manera directa y fluida
        List<DronDeSuperficie> listaDrones = List.of(
                new DronDeSuperficie("DS-01", 100, 40.0, 15.0, TipoMonitoreo.VISUAL),
                new DronDeSuperficie("DS-02", 100, 50.0, 30.0, TipoMonitoreo.TERMICO),
                new DronDeSuperficie("DS-03", 100, 30.0, 12.0, TipoMonitoreo.VISUAL)
        );

        // Ejecución
        ResultadoAnalisisVelocidad resultado = empresa.procesarVelocidades(listaDrones);

        // Asserts (Se mantienen igual de estrictos)
        assert resultado.velocidadesOrdenadas().size() == 3 : "Error: El tamaño debería ser 3";
        assert resultado.velocidadesOrdenadas().get(0) == 12.0 : "Error: El menor debe ser 12.0";
        assert resultado.velocidadesOrdenadas().get(2) == 30.0 : "Error: El mayor debe ser 30.0";
        assert resultado.promedio() == 19.0 : "Error: El promedio esperado es 19.0";

        System.out.println("¡Todas las pruebas optimizadas pasaron con éxito!");
    }

    @Test
    @DisplayName("Consumo calculado correctamente")
    public void testConsumoMonitoreoVisual() {
        DronDeSuperficie dron1 = new DronDeSuperficie("DRON-01", 100, 50, 20, TipoMonitoreo.VISUAL);

        int consumoEsperado = 20;
        int consumoReal= dron1.calcularConsumoPorMonitoreo(2.0);

        assertEquals(consumoEsperado, consumoReal, "El monitoreo VISUAL debería consumir 10 por hora");
    }

    @Test
    @DisplayName("Consumo Horas Negativas es 0")
    public void testConsumoConHorasNegativasEsCero(){
        DronDeSuperficie dron1 = new DronDeSuperficie("DRON-01", 100, 50, 20, TipoMonitoreo.VISUAL);

        int consumoReal = dron1.calcularConsumoPorMonitoreo(-3.0);

        assertEquals(0, consumoReal);

    }

    @Test
    @DisplayName("Verificación Boolean")
    public void testVerificacionesConBooleanos(){
        DronDeSuperficie dron1 = new DronDeSuperficie("DRON-01", 100, 50, 20, TipoMonitoreo.VISUAL);

        dron1.cargarBateria(100);
        boolean puedeRegresar = dron1.puedeRegresarABase(10.0);


        assertTrue(puedeRegresar, "Debería regresar si la base está cerca");
    }

    public static void main(String[] args) {
        new Pruebas().ejecutarPruebasVelocidades();
    }
}

