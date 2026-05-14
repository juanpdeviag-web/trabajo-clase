package co.edu.uniquindio.model;

import java.time.LocalDate;

public class PersonalApoyo extends co.edu.uniquindio.model.Participante {
    private int experienciaLaboral;

    public PersonalApoyo(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, int edad, String paisRepresentacion, int experienciaLaboral) {
        super(nombre, apellido, fechaNacimiento, nacionalidad, edad, paisRepresentacion);
        this.experienciaLaboral = experienciaLaboral;
    }

    @Override
    public String getRol() {
        return "Personal de Apoyo";
    }

    public int getExperienciaLaboral() {
        return experienciaLaboral;
    }

    public void setExperienciaLaboral(int experienciaLaboral) {
        this.experienciaLaboral = experienciaLaboral;
    }
}
