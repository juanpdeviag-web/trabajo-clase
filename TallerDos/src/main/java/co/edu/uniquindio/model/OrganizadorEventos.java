package co.edu.uniquindio.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizadorEventos {
    private String nit;
    private String nombre;
    private List<EventoDeportivo> eventos;

    public OrganizadorEventos(String nit, String nombre) {
        this.nit = nit;
        this.nombre = nombre;
        this.eventos = new ArrayList<>();
    }

    public void agregarEvento(EventoDeportivo evento) {
        this.eventos.add(evento);
    }

    public List<EventoDeportivo> getEventos() {
        return eventos;
    }
    // Getters y Setters
    public String getNit() { return nit; }
    public String getNombre() { return nombre; }
}