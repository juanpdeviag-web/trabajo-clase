package com.techpark.model;

public class TicketFastPass extends Ticket {
    private double recargoPrioridad;

    public TicketFastPass(String idTicket, double precioBase, double recargoPrioridad) {
        super(idTicket, precioBase, TipoTicket.FAST_PASS);
        this.recargoPrioridad = recargoPrioridad;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() + recargoPrioridad;
    }

    public double getRecargoPrioridad() {
        return recargoPrioridad;
    }

    public void setRecargoPrioridad(double recargoPrioridad) {
        this.recargoPrioridad = recargoPrioridad;
    }
}