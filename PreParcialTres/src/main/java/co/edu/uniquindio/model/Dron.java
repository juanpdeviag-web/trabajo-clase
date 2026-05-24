package co.edu.uniquindio.model;

import co.edu.uniquindio.interfaces.Operable;


public abstract class Dron implements Operable {
    protected String id;
    protected int bateriaActual;
    protected int bateriaMaxima;

    public Dron(String id, int bateriaMaxima) {
        this.id = id;
        this.bateriaMaxima = Math.min(bateriaMaxima, 100); // No excede el 100%
        this.bateriaActual = this.bateriaMaxima; // Inicia con carga completa
    }

    public void cargarBateria(int porcentaje) {
        if (porcentaje > 0) {
            this.bateriaActual = Math.min(this.bateriaActual + porcentaje, 100);
        }
    }

    public void reducirBateria(int porcentaje) {
        if (porcentaje > 0) {
            this.bateriaActual = Math.max(this.bateriaActual - porcentaje, 0);
        }
    }

    public int getBateriaActual() { return bateriaActual; }
    public String getId() { return id; }


    public void setBateriaActual(int bateriaActual) {
        this.bateriaActual = bateriaActual;
    }
}

