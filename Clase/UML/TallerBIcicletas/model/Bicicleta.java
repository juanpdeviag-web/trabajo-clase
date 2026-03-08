package UML.TallerBIcicletas.model;

public class Bicicleta {

   //Atributos
    private String serial;
    private String marca;
    private String color;


   //Constructor
    public Bicicleta(String serial, String marca) {
        this.serial = serial;
        this.marca = marca;
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

    //toString
    @Override
    public String toString() {
        return "Bicicleta{" +
                "serial='" + serial + '\'' +
                ", marca='" + marca + '\'' +
                '}';
    }
}
