package co.edu.uniquindio.model;

import java.time.LocalDate;

public abstract class Participante extends co.edu.uniquindio.model.Persona {
    protected int edad;
    protected String paisRepresentacion;
    // El tipo de participación (rol) se puede inferir del tipo de la clase hija (Atleta, Arbitro, etc.)
    // por lo que el campo tipoParticipacion es redundante pero lo mantenemos si el diseño lo exige.

    public Participante(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, int edad, String paisRepresentacion) {
        super(nombre, apellido, fechaNacimiento, nacionalidad);
        this.edad = edad;
        this.paisRepresentacion = paisRepresentacion;
    }

    // Método abstracto que podría ser usado para obtener el rol específico
    public abstract String getRol();

    // Getters y Setters
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPaisRepresentacion() {
        return paisRepresentacion;
    }

    public void setPaisRepresentacion(String paisRepresentacion) {
        this.paisRepresentacion = paisRepresentacion;
    }
}