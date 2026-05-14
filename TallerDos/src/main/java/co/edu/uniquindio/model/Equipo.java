package co.edu.uniquindio.model;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private String nombre;
    private String paisOrigen;
    private List<co.edu.uniquindio.model.Atleta> atletas;

    public Equipo(String nombre, String paisOrigen) {
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
        this.atletas = new ArrayList<>();
    }

    public void agregarAtleta(co.edu.uniquindio.model.Atleta atleta) {
        this.atletas.add(atleta);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }



    public List<co.edu.uniquindio.model.Atleta> getAtletas() {
        return atletas;
    }
}
