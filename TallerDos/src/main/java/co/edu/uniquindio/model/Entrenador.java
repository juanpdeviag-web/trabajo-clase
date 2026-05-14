package co.edu.uniquindio.model;

import java.time.LocalDate;

public class Entrenador extends co.edu.uniquindio.model.Participante {
    private String especializacion;

    public Entrenador(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, int edad, String paisRepresentacion, String especializacion) {
        super(nombre, apellido, fechaNacimiento, nacionalidad, edad, paisRepresentacion);
        this.especializacion = especializacion;
    }

    @Override
    public String getRol() {
        return "Entrenador";
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }
}
