package com.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String idZona;
    private String nombre;
    private int aforoMaximo;
    private List<Atraccion> atracciones;
    private List<Operador> operadores;

    public Zona(String idZona, String nombre, int aforoMaximo) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.aforoMaximo = aforoMaximo;
        this.atracciones = new ArrayList<>();
        this.operadores = new ArrayList<>();
    }

    public void agregarAtraccion(Atraccion atraccion) {
        if (atraccion != null && !atracciones.contains(atraccion)) {
            atracciones.add(atraccion);
        }
    }

    public void agregarOperador(Operador operador) {
        if (operador != null && !operadores.contains(operador)) {
            operadores.add(operador);
            operador.setZonaAsignada(this);
        }
    }

    public void removerOperador(Operador operador) {
        operadores.remove(operador);
    }

    public boolean tieneOperadores() {
        return !operadores.isEmpty();
    }

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(int aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public List<Atraccion> getAtracciones() {
        return atracciones;
    }

    public List<Operador> getOperadores() {
        return operadores;
    }

    @Override
    public String toString() {
        return String.format("Zona %s - %s (Aforo: %d) - Atracciones: %d - Operadores: %d",
                idZona, nombre, aforoMaximo, atracciones.size(), operadores.size());
    }
}