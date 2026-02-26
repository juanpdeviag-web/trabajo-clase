package UML.Cicla;

public class Cicla {

    // Atributos de la clase
        // Private: no puede ser accedido o modificado directamente desde fuera de esa clase.
        // Public: puede ser accedido o modificado directamente desde fuera de esa clase.

    private String id;
    private String nombre;

    //private boolean suspencion;
    private boolean freno;
    //private boolean transmision;

    //private String tipoPlato;
    private String color;

    private int tallaRin;

    // Metodo constructor
    public Cicla(String id,
                 String nombre,
                 boolean freno,
                 String color,
                 int tallaRin){


        this.id          = id;
        this.nombre      = nombre;
        this.freno       = freno;
        this.color       = color;
        this.tallaRin    = tallaRin;
    }




    // Métodos
        //Private: solo puede ser usado dentro de la clase en que fue creado.
        //Public: puede ser usado por fuera de la clase en que fue creado.

    public void acelerar(){
        System.out.println("Acelerando");

    }
       // código de acelerar
    public void frenar(){
        System.out.println("Frenando");
    }
       // código de frenar

    @Override
    public String toString(){
        return "Id:"+ id+ "\nNombre: "+nombre+ "\nFreno: "+freno+ "\nColor: "+color+ "\nTallaRin: "+tallaRin;
    }


    // Metodo Get - Para obtener datos
    public String getNombre(){
        return nombre;
    }
    public String getId(){
        return id;
    }
    public boolean getFreno(){
        return freno;
    }
    public String getColor(){
        return color;
    }
    public int getTallaRin(){
        return tallaRin;
    }

    // Metodo Set - Para asignar datos
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setFreno(boolean freno){
        this.freno = freno;
    }
    public void setColor(String color){
        this.color = color;
    }
    public void setTallaRin(int tallaRin){
        this.tallaRin = tallaRin;
    }



}







