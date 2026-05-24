package com.techpark.model;

import java.time.LocalDateTime;

public record Notificacion(String mensaje, LocalDateTime fecha) {

    public Notificacion(String mensaje) {
        this(mensaje, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s",
                fecha.toLocalDate() + " " + fecha.toLocalTime().toString().substring(0, 8),
                mensaje);
    }
}
