package com.techpark.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AtraccionTest {

    @Test
    void testBloqueoAutomaticoA500Visitantes() {
        Atraccion atraccion = new Atraccion("ATR001", "Montaña Rusa Extrema",
                TipoAtraccion.MECANICA_ALTURA, 20, 1.40, 12, 5000.0);

        assertEquals(EstadoAtraccion.ACTIVA, atraccion.getEstado());

        for (int i = 0; i < 499; i++) {
            assertTrue(atraccion.registrarIngresoVisitante());
            assertEquals(EstadoAtraccion.ACTIVA, atraccion.getEstado());
        }

        assertEquals(499, atraccion.getVisitantesAcumulados());

        assertTrue(atraccion.registrarIngresoVisitante());

        assertEquals(500, atraccion.getVisitantesAcumulados());
        assertEquals(EstadoAtraccion.EN_MANTENIMIENTO, atraccion.getEstado());
        assertTrue(atraccion.getMotivoEstado().contains("500 visitantes"));

        assertFalse(atraccion.registrarIngresoVisitante());
        assertEquals(500, atraccion.getVisitantesAcumulados());
    }

    @Test
    void testReinicioDespuesDeMantenimiento() {
        Atraccion atraccion = new Atraccion("ATR002", "Splash Mountain",
                TipoAtraccion.ACUATICA, 30, 1.20, 8, 3000.0);

        for (int i = 0; i < 500; i++) {
            atraccion.registrarIngresoVisitante();
        }

        assertEquals(EstadoAtraccion.EN_MANTENIMIENTO, atraccion.getEstado());

        Operador operador = new Operador("9876", "María García", 30, 1.65,
                "maria.op", "pass456", null);

        operador.realizarMantenimiento(atraccion);

        assertEquals(0, atraccion.getVisitantesAcumulados());
        assertEquals(EstadoAtraccion.ACTIVA, atraccion.getEstado());
    }
}