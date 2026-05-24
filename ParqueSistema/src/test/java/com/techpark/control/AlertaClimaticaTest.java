package com.techpark.control;

import com.techpark.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlertaClimaticaTest {

    private SistemaParque sistema;

    @BeforeEach
    void setUp() {
        sistema = SistemaParque.getInstancia();
        sistema.getAtraccionesGlobales().clear();
        sistema.getVisitantes().clear();
    }

    @Test
    void testAlertaTormentaCierraAtraccionesVulnerables() {
        Atraccion acuatica = new Atraccion("ATR001", "Rápidos Salvajes",
                TipoAtraccion.ACUATICA, 20, 1.30, 10, 0);
        Atraccion altura = new Atraccion("ATR002", "Rueda de la Fortuna",
                TipoAtraccion.MECANICA_ALTURA, 40, 1.20, 8, 0);
        Atraccion normal = new Atraccion("ATR003", "Carrusel",
                TipoAtraccion.NORMAL, 30, 1.00, 5, 0);

        sistema.agregarAtraccion(acuatica);
        sistema.agregarAtraccion(altura);
        sistema.agregarAtraccion(normal);

        assertEquals(EstadoAtraccion.ACTIVA, acuatica.getEstado());
        assertEquals(EstadoAtraccion.ACTIVA, altura.getEstado());
        assertEquals(EstadoAtraccion.ACTIVA, normal.getEstado());

        sistema.activarAlertaClimatica("tormenta");

        assertEquals(EstadoAtraccion.CERRADA, acuatica.getEstado());
        assertTrue(acuatica.getMotivoEstado().contains("climáticas"));

        assertEquals(EstadoAtraccion.CERRADA, altura.getEstado());
        assertTrue(altura.getMotivoEstado().contains("climáticas"));

        assertEquals(EstadoAtraccion.ACTIVA, normal.getEstado());
    }

    @Test
    void testNotificacionesEnviadasATodosLosVisitantes() {
        Atraccion acuatica = new Atraccion("ATR004", "Splash Mountain",
                TipoAtraccion.ACUATICA, 25, 1.25, 9, 0);
        sistema.agregarAtraccion(acuatica);

        Visitante visitante1 = new Visitante("V001", "Pedro López", 22, 1.75, 50000, "");
        Visitante visitante2 = new Visitante("V002", "Laura Martínez", 27, 1.68, 60000, "");

        sistema.registrarVisitante(visitante1);
        sistema.registrarVisitante(visitante2);

        assertEquals(0, visitante1.getBuzonNotificaciones().size());
        assertEquals(0, visitante2.getBuzonNotificaciones().size());

        sistema.activarAlertaClimatica("lluvia");

        assertEquals(1, visitante1.getBuzonNotificaciones().size());
        assertEquals(1, visitante2.getBuzonNotificaciones().size());

        Notificacion notif1 = visitante1.getBuzonNotificaciones().get(0);
        assertTrue(notif1.mensaje().contains("ALERTA CLIMÁTICA"));
        assertTrue(notif1.mensaje().contains("Splash Mountain"));
    }

    @Test
    void testClimaDespejadonoGeneraNotificaciones() {
        Atraccion acuatica = new Atraccion("ATR005", "Toboganes",
                TipoAtraccion.ACUATICA, 30, 1.20, 7, 0);
        sistema.agregarAtraccion(acuatica);

        Visitante visitante = new Visitante("V003", "Carlos Ruiz", 30, 1.80, 70000, "");
        sistema.registrarVisitante(visitante);

        sistema.activarAlertaClimatica("despejado");

        assertEquals(EstadoAtraccion.ACTIVA, acuatica.getEstado());
        assertEquals(0, visitante.getBuzonNotificaciones().size());
    }
}