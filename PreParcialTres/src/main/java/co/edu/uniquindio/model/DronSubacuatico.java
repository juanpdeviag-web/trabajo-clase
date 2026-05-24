package co.edu.uniquindio.model;

import co.edu.uniquindio.enums.TipoSensor;

public class DronSubacuatico extends Dron {
    private int profundidadMaximaInmersion;
    private int profundidadActual;
    private TipoSensor tipoSensor;

    public DronSubacuatico(String id, int bateriaMaxima, int profundidadMaximaInmersion, TipoSensor tipoSensor) {
        super(id, bateriaMaxima);
        this.profundidadMaximaInmersion = profundidadMaximaInmersion;
        this.profundidadActual = 0; // Superficie al inicio
        this.tipoSensor = tipoSensor;
    }

    public void ajustarProfundidad(int profundidadDeseada) {
        if (profundidadDeseada > this.profundidadMaximaInmersion) {
            this.profundidadActual = this.profundidadMaximaInmersion;
        } else {
            this.profundidadActual = Math.max(profundidadDeseada, 0);
        }
    }

    public int getProfundidadActual() { return profundidadActual; }
    public int getProfundidadMaximaInmersion() { return profundidadMaximaInmersion; }

    @Override
    public void realizarMision() {
        System.out.println("Dron subacuático " + getId() + " sumergido monitoreando raíces.");
    }
}

