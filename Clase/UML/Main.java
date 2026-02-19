package Uml;

public class Main {
    public static void main(String[] args) {
        Cicla miBici = new Cicla("C-01", "todoterreno", true, "negro", 26 );
        System.out.println(miBici);
        miBici.acelerar(); //llamado de metodo NO static
        //Cicla.acelerar(); //llamado de metodo static
    }
}
