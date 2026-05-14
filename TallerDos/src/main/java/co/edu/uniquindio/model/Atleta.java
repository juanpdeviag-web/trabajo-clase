package co.edu.uniquindio.model;

import java.time.LocalDate;

public class Atleta extends co.edu.uniquindio.model.Participante {
    private int experiencia; // Años de experiencia

    public Atleta(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, int edad, String paisRepresentacion, int experiencia) {
        super(nombre, apellido, fechaNacimiento, nacionalidad, edad, paisRepresentacion);
        this.experiencia = experiencia;
    }

    @Override
    public String getRol() {
        return "Atleta";
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }
}
