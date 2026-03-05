package UML.NewMascota;

public class Mascota {

    //  Atributos de la clase Mascota
    private String id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;

    private Propietario thePropietario;

    //  Constructor
    public Mascota( String id,
                    String nombre,
                    String especie,
                    String raza,
                    int edad){
        this.id=id;
        this.nombre=nombre;
        this.especie=especie;
        this.raza=raza;
        this.edad=edad;
    }

    //  Gets


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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String taza) {
        this.raza = taza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Propietario getThePropietario() {
        return thePropietario ;
    }
    public void setThePropietario(Propietario thePropietario) {
        this.thePropietario = thePropietario;
    }

    //  toString
    @Override
    public String toString(){
        return "DATOS DE LA MASCOTA "+id+"\n"+
                "Nombre: " + nombre + "\n" +
                "Tipo: " + especie + "\n" +
                "Raza: " + raza + "\n" +
                "Edad: " + edad + " años\n"+
                thePropietario.toString();
    }

}
