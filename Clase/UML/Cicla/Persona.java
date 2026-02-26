package UML.Cicla;

public class Persona {
    //Atributos de la Clase (public / private)
    private String nombre;
    private int edad;
    private double estatura;

    //Constructor
    public Persona( String nombre,
                    int edad,
                    double estatura){

        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;

    }

    //Métodos
    public void vivo(){
        System.out.println("VIVO");
    }

    @Override
    public String toString(){
        return "Nombre: "+nombre+"\nEdad: "+edad+"\nEstatura: "+estatura;
    }
}
