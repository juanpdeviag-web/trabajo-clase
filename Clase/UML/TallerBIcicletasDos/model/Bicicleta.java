package UML.TallerBIcicletasDos.model;

import UML.TallerBIcicletas.model.Cliente;

public class Bicicleta {

   //Atributos
    private String serial;
    private String marca;
    private String color;

    private UML.TallerBIcicletas.model.Cliente theCLiente;
    private String nombreCliente;


   //Constructor
    public Bicicleta(String serial, String marca, String color) {
        this.serial = serial;
        this.marca = marca;
        this.color = color;
    }

    //Getter and Setter

    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public UML.TallerBIcicletas.model.Cliente getTheCLiente() {
        return theCLiente;
    }
    public void setTheCLiente(Cliente theCLiente) {
        this.theCLiente = theCLiente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    //toString


    @Override
    public String toString() {
        String duenio = (this.theCLiente != null) ? this.theCLiente.getNombre() : "Sin dueño";

        return "Bicicleta{" +
                "serial='" + serial + '\'' +
                ", marca='" + marca + '\'' +
                ", color='" + color + '\'' +
                ", Dueño=" + duenio;

    }
}
