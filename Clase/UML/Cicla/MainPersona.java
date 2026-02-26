package UML.Cicla;

public class MainPersona {
    public static void main(String[] args) {
        Persona juan = new Persona("Juan", 20, 1.65);
        System.out.println(juan);
        juan.vivo(); //Metodo no static
    }
}
