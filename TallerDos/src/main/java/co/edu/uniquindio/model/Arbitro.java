package co.edu.uniquindio.model;

import java.time.LocalDate;

public class Arbitro extends co.edu.uniquindio.model.Participante {
    private String categoria; // Ej: "Internacional", "Nacional"

    public Arbitro(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, int edad, String paisRepresentacion, String categoria) {
        super(nombre, apellido, fechaNacimiento, nacionalidad, edad, paisRepresentacion);
        this.categoria = categoria;
    }

    @Override
    public String getRol() {
        return "Arbitro";
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}