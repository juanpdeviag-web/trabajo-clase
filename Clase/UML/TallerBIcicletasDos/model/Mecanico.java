package UML.TallerBIcicletasDos.model;

public class Mecanico {
    private String id;
    private String nombre;
    private String telefono;
    private double salario;



    public Mecanico(String id, String nombre,String telefono, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.telefono=telefono;
        this.salario=salario;
    }


   //Getter and Setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() { return telefono; } // Si faltaba
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public void setSalario(double salario) { this.salario = salario; }
    public double getSalario() { return salario; }


    //toString

    @Override
    public String toString() {
        return "Mecanico{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", salario=" + salario +
                '}';
    }
}
