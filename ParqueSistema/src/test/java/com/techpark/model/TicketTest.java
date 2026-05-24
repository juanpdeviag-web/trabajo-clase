package com.techpark.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void testTicketGeneralCalculaPrecioBase() {
        TicketGeneral ticket = new TicketGeneral("TG001", 50000.0);
        assertEquals(50000.0, ticket.calcularPrecioFinal(), 0.01);
    }

    @Test
    void testTicketFamiliarAplicaDescuento() {
        TicketFamiliar ticket = new TicketFamiliar("TF001", 50000.0, 15.0);
        assertEquals(42500.0, ticket.calcularPrecioFinal(), 0.01);
    }

    @Test
    void testTicketFastPassAplicaRecargo() {
        TicketFastPass ticket = new TicketFastPass("TFP001", 50000.0, 20000.0);
        assertEquals(70000.0, ticket.calcularPrecioFinal(), 0.01);
    }

    @Test
    void testTicketFamiliarDescuentoCero() {
        TicketFamiliar ticket = new TicketFamiliar("TF002", 50000.0, 0.0);
        assertEquals(50000.0, ticket.calcularPrecioFinal(), 0.01);
    }

    @Test
    void testTicketFamiliarDescuentoCompleto() {
        TicketFamiliar ticket = new TicketFamiliar("TF003", 50000.0, 100.0);
        assertEquals(0.0, ticket.calcularPrecioFinal(), 0.01);
    }

    @Test
    void testTicketFastPassRecargoAlto() {
        TicketFastPass ticket = new TicketFastPass("TFP002", 50000.0, 50000.0);
        assertEquals(100000.0, ticket.calcularPrecioFinal(), 0.01);
    }
}