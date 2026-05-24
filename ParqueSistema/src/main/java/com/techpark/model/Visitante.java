package com.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Visitante extends Persona implements Notificable {
    private double saldoVirtual;
    private String rutaFotografia;
    private Ticket ticket;
    private List<RegistroVisita> historialVisitas;
    private List<Notificacion> buzonNotificaciones;
    private List<Atraccion> atraccionesFavoritas;

    public Visitante(String idDocumento, String nombre, int edad, double estatura,
                     double saldoVirtual, String rutaFotografia) {
        super(idDocumento, nombre, edad, estatura);
        this.saldoVirtual = saldoVirtual;
        this.rutaFotografia = rutaFotografia;
        this.historialVisitas = new ArrayList<>();
        this.buzonNotificaciones = new ArrayList<>();
        this.atraccionesFavoritas = new ArrayList<>();
    }

    @Override
    public void recibirNotificacion(Notificacion notificacion) {
        buzonNotificaciones.add(notificacion);
    }

    public void agregarFavorita(Atraccion atraccion) {
        if (atraccion != null && !atraccionesFavoritas.contains(atraccion)) {
            atraccionesFavoritas.add(atraccion);
        }
    }

    public void removerFavorita(Atraccion atraccion) {
        atraccionesFavoritas.remove(atraccion);
    }

    public List<Atraccion> getAtraccionesFavoritas() {
        return atraccionesFavoritas;
    }

    public boolean esFavorita(Atraccion atraccion) {
        return atraccionesFavoritas.contains(atraccion);
    }

    public void agregarVisita(RegistroVisita registro) {
        historialVisitas.add(registro);
    }

    public void recargarSaldo(double monto) {
        if (monto > 0) {
            this.saldoVirtual += monto;
        }
    }

    public boolean deducirSaldo(double monto) {
        if (saldoVirtual >= monto) {
            saldoVirtual -= monto;
            return true;
        }
        return false;
    }

    public double getSaldoVirtual() {
        return saldoVirtual;
    }

    public void setSaldoVirtual(double saldoVirtual) {
        this.saldoVirtual = saldoVirtual;
    }

    public String getRutaFotografia() {
        return rutaFotografia;
    }

    public void setRutaFotografia(String rutaFotografia) {
        this.rutaFotografia = rutaFotografia;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<RegistroVisita> getHistorialVisitas() {
        return historialVisitas;
    }

    public List<Notificacion> getBuzonNotificaciones() {
        return buzonNotificaciones;
    }

    @Override
    public String toString() {
        return String.format("%s - Saldo: $%.2f - Ticket: %s",
                super.toString(), saldoVirtual, ticket != null ? ticket.getTipo() : "Sin ticket");
    }
}