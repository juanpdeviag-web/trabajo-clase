package co.edu.uniquindio.model;

import co.edu.uniquindio.enums.TipoMonitoreo;

public class DronDeSuperficie extends Dron {
    private double alcanceMaximoVuelo;
    private double velocidadMediaOperacion;
    private TipoMonitoreo tipoMonitoreo;

    public DronDeSuperficie(String id, int bateriaMaxima, double alcanceMaximoVuelo, double velocidadMediaOperacion, TipoMonitoreo tipoMonitoreo) {
        super(id, bateriaMaxima);
        this.alcanceMaximoVuelo = alcanceMaximoVuelo;
        this.velocidadMediaOperacion = velocidadMediaOperacion;
        this.tipoMonitoreo = tipoMonitoreo;
    }

    public double calcularTiempoVueloEstimado() {
        if (this.velocidadMediaOperacion <= 0) return 0.0;

        // Usamos las variables del propio dron
        double porcentajeBateria = (double) this.bateriaActual / 100;
        double distanciaRestante = porcentajeBateria * this.alcanceMaximoVuelo;

        return distanciaRestante / this.velocidadMediaOperacion;
    }

    //Reto extra
    public boolean puedeRegresarABase(double distanciaALaBase){
        if(this.bateriaActual <= 10) return false;
        double bateriaUtilizable = (double) (this.bateriaActual -10) /100;
        double distanciaMaximaPosible = bateriaUtilizable *this.alcanceMaximoVuelo;

        if (distanciaMaximaPosible >= distanciaALaBase){
            return true;
        } else{
            return false;
        }
    }

    //Reto Extra 2
    public void registrarConsumoPorVuelo(double horasVuelo) {
        // 1. Si las horas son negativas o cero, no hay consumo
        if (horasVuelo <= 0) {
            return;
        }

        // 2. Calcular cuánta batería se va a gastar (15% por hora)
        int bateriaAConsumir = (int) (horasVuelo * 15);

        // 3. Restar la batería asegurando que el mínimo sea 0 (usamos Math.max para el límite inferior)
        this.bateriaActual = Math.max(this.bateriaActual - bateriaAConsumir, 0);
    }

    public int calcularConsumoPorMonitoreo(double horasVuelo) {
        if (horasVuelo <= 0 || this.tipoMonitoreo == null) {
            return 0;
        }

        int factorConsumo;

        // Evaluamos el enum usando un switch estructurado
        switch (this.tipoMonitoreo) {
            case VISUAL:
                factorConsumo = 10;
                break;
            case TERMICO:
                factorConsumo = 20;
                break;
            default:
                factorConsumo = 10; // Un valor base por si acaso
                break;
        }

        return (int) (horasVuelo * factorConsumo);
    }


    public double getAlcanceMaximoVuelo() { return alcanceMaximoVuelo; }
    public double getVelocidadMediaOperacion() { return velocidadMediaOperacion; }

    @Override
    public void realizarMision() {
        System.out.println("Dron de superficie " + getId() + " sobrevolando cultivos.");
    }
}

