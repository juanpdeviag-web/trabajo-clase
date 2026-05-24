package com.techpark.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VisitanteTest {

    @Test
    void testRecibirNotificacion() {
        Visitante visitante = new Visitante("123456", "Juan Pérez", 25, 1.75, 100000.0, "");
        Notificacion notificacion = new Notificacion("Atracción cerrada por lluvia");

        visitante.recibirNotificacion(notificacion);

        assertEquals(1, visitante.getBuzonNotificaciones().size());
        assertEquals(notificacion, visitante.getBuzonNotificaciones().get(0));
    }

    @Test
    void testRecargarSaldo() {
        Visitante visitante = new Visitante("123456", "Juan Pérez", 25, 1.75, 100000.0, "");
        visitante.recargarSaldo(50000.0);

        assertEquals(150000.0, visitante.getSaldoVirtual(), 0.01);
    }

    @Test
    void testDeducirSaldoExitoso() {
        Visitante visitante = new Visitante("123456", "Juan Pérez", 25, 1.75, 100000.0, "");
        boolean resultado = visitante.deducirSaldo(30000.0);

        assertTrue(resultado);
        assertEquals(70000.0, visitante.getSaldoVirtual(), 0.01);
    }

    @Test
    void testDeducirSaldoInsuficiente() {
        Visitante visitante = new Visitante("123456", "Juan Pérez", 25, 1.75, 100000.0, "");
        boolean resultado = visitante.deducirSaldo(150000.0);

        assertFalse(resultado);
        assertEquals(100000.0, visitante.getSaldoVirtual(), 0.01);
    }

    @Test
    void testAgregarVisita() {
        Atraccion atraccion1 = new Atraccion("ATR001", "SUBE Y BAJA", TipoAtraccion.MECANICA_ALTURA, 20, 1.60,15, 5000);
        Visitante visitante = new Visitante("123456", "Juan Pérez", 25, 1.75, 100000.0, "");
        RegistroVisita registro = new RegistroVisita(atraccion1, 5000.0, 15);

        visitante.agregarVisita(registro);

        assertEquals(1, visitante.getHistorialVisitas().size());
        assertEquals(registro, visitante.getHistorialVisitas().get(0));
    }
}