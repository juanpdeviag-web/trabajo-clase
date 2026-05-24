package com.techpark.control;

import com.techpark.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SistemaParqueTest {

    private SistemaParque sistema;

    @BeforeEach
    void setUp() {
        sistema = SistemaParque.getInstancia();
        sistema.getAtraccionesGlobales().clear();
        sistema.getVisitantes().clear();
        sistema.getTicketsVendidos().clear();
    }

    @Test
    void testRechazoAccesoPorEdad() {
        Atraccion atraccion = new Atraccion("ATR001", "Montaña Rusa",
                TipoAtraccion.MECANICA_ALTURA, 20, 1.40, 12, 0);
        sistema.agregarAtraccion(atraccion);

        Visitante visitante = new Visitante("123", "Niño Pequeño", 8, 1.50, 50000, "");
        visitante.setTicket(new TicketGeneral("TG001", 30000));
        sistema.registrarVisitante(visitante);

        String resultado = sistema.validarYRegistrarAcceso(visitante, atraccion);

        assertTrue(resultado.contains("Edad mínima"));
        assertEquals(0, atraccion.getVisitantesAcumulados());
    }

    @Test
    void testRechazoAccesoPorAltura() {
        Atraccion atraccion = new Atraccion("ATR002", "Torre de Caída",
                TipoAtraccion.MECANICA_ALTURA, 15, 1.50, 10, 0);
        sistema.agregarAtraccion(atraccion);

        Visitante visitante = new Visitante("456", "Persona Baja", 20, 1.45, 50000, "");
        visitante.setTicket(new TicketGeneral("TG002", 30000));
        sistema.registrarVisitante(visitante);

        String resultado = sistema.validarYRegistrarAcceso(visitante, atraccion);

        assertTrue(resultado.contains("Altura mínima"));
        assertEquals(0, atraccion.getVisitantesAcumulados());
    }

    @Test
    void testRechazoAccesoPorSaldoInsuficiente() {
        Atraccion atraccion = new Atraccion("ATR003", "Simulador VR",
                TipoAtraccion.NORMAL, 10, 1.20, 8, 15000);
        sistema.agregarAtraccion(atraccion);

        Visitante visitante = new Visitante("789", "Juan Pobre", 25, 1.75, 10000, "");
        visitante.setTicket(new TicketGeneral("TG003", 30000));
        sistema.registrarVisitante(visitante);

        String resultado = sistema.validarYRegistrarAcceso(visitante, atraccion);

        assertTrue(resultado.contains("Saldo insuficiente"));
        assertEquals(10000, visitante.getSaldoVirtual(), 0.01);
        assertEquals(0, atraccion.getVisitantesAcumulados());
    }

    @Test
    void testAccesoExitosoConDeduccionDeSaldo() {
        Atraccion atraccion = new Atraccion("ATR004", "Cine 4D",
                TipoAtraccion.NORMAL, 25, 1.00, 5, 8000);
        sistema.agregarAtraccion(atraccion);

        Visitante visitante = new Visitante("111", "Ana Rica", 30, 1.70, 50000, "");
        visitante.setTicket(new TicketGeneral("TG004", 30000));
        sistema.registrarVisitante(visitante);

        String resultado = sistema.validarYRegistrarAcceso(visitante, atraccion);

        assertTrue(resultado.contains("Acceso concedido"));
        assertEquals(42000, visitante.getSaldoVirtual(), 0.01);
        assertEquals(1, atraccion.getVisitantesAcumulados());
        assertEquals(1, visitante.getHistorialVisitas().size());
    }

    @Test
    void testAccesoConTicketFastPassSinCostoAdicional() {
        Atraccion atraccion = new Atraccion("ATR005", "Casa Embrujada",
                TipoAtraccion.NORMAL, 15, 1.10, 10, 5000);
        sistema.agregarAtraccion(atraccion);

        Visitante visitante = new Visitante("222", "Carlos VIP", 28, 1.80, 30000, "");
        visitante.setTicket(new TicketFastPass("TFP001", 30000, 15000));
        sistema.registrarVisitante(visitante);

        String resultado = sistema.validarYRegistrarAcceso(visitante, atraccion);

        assertTrue(resultado.contains("Acceso concedido"));
        assertEquals(30000, visitante.getSaldoVirtual(), 0.01);
    }
}