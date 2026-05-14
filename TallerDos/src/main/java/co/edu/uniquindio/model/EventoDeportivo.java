package co.edu.uniquindio.model;

import co.edu.uniquindio.model.Equipo;
import co.edu.uniquindio.enums.TipoEvento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventoDeportivo {
    private String nombre;
    private LocalDate fechaInicio;
    private String disciplina;
    private TipoEvento tipoEvento;
    private co.edu.uniquindio.model.Ubicacion ubicacion;
    private List<co.edu.uniquindio.model.Participante> participantes;
    private List<Equipo> equipos;
    private boolean disponible = true; // Añadido para el método de acceso

    public EventoDeportivo(String nombre, LocalDate fechaInicio, String disciplina, TipoEvento tipoEvento, co.edu.uniquindio.model.Ubicacion ubicacion) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.disciplina = disciplina;
        this.tipoEvento = tipoEvento;
        this.ubicacion = ubicacion;
        this.participantes = new ArrayList<>();
        this.equipos = new ArrayList<>();
    }

    public void agregarParticipante(co.edu.uniquindio.model.Participante participante) {
        this.participantes.add(participante);
    }

    public void agregarEquipo(Equipo equipo) {
        this.equipos.add(equipo);
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public String getDisciplina() { return disciplina; }
    public TipoEvento getTipoEvento() { return tipoEvento; }
    public co.edu.uniquindio.model.Ubicacion getUbicacion() { return ubicacion; }
    public List<co.edu.uniquindio.model.Participante> getParticipantes() { return participantes; }
    public List<Equipo> getEquipos() { return equipos; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
