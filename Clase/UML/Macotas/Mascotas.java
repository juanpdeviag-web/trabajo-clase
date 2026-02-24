package UML.Macotas;

public class Mascotas {
    //Atributos
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private String nombrePropietario;
    private String numeroContacto;

    //Metodo Constructor
    public Mascotas(String id, String nombre, String especie, String raza, int edad, String nombrePropietario, String numeroContacto){
        this.id=id;
        this.nombre=nombre;
        this.especie=especie;
        this.raza=raza;
        this.edad=edad;
        this.nombrePropietario=nombrePropietario;
        this.numeroContacto=numeroContacto;

    }

    //Gets

    public String getId() {
        return id;
    }
    public String getNombre(){
        return nombre;
    }
    public String getEspecie() {
        return especie;
    }
    public String getRaza() {
        return raza;
    }
    public int getEdad() {
        return edad;
    }
    public String getNombrePropietario() {
        return nombrePropietario;
    }
    public String getNumeroContacto() {
        return numeroContacto;
    }


    //Sets
    public void setId(String id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }
    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    //toString
    @Override
    public String toString(){
        return "Id:"+ id+ "\nNombre: "+nombre+ "\nEspecie: "+especie+ "\nRaza: "+raza+ "\nEdad: "+edad+"\nNombre Propietario: "+nombrePropietario+"\nNúmero Contacto: "+numeroContacto;
    }
}
