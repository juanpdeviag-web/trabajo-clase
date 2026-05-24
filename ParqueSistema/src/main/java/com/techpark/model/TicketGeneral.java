package com.techpark.model;

public class TicketGeneral extends Ticket {

    public TicketGeneral(String idTicket, double precioBase) {
        super(idTicket, precioBase, TipoTicket.GENERAL);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase();
    }
}