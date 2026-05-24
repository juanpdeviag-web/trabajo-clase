package co.edu.uniquindio.model;

public class TanqueCultivo {
    private String id;
    private double capacidadMaxima;
    private double nivelAguaActual ;
    private double temperaturaCelsius ;

    public TanqueCultivo(String id,
                        double capacidadMaxima,
                        double nivelAguaActual,
                        double temperaturaCelsius){
        this.id=id;
        this.capacidadMaxima= capacidadMaxima;
        this.nivelAguaActual=nivelAguaActual;
        this.temperaturaCelsius=temperaturaCelsius;

    }

    public double getTemperaturaCelsius(){
        return temperaturaCelsius;
    }

    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getNivelAguaActual(){
        return  nivelAguaActual;
    }
}
