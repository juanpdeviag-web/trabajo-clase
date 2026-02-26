package UML.Cicla;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Cicla miBici = new Cicla("C-01", "todoterreno", true, "negro", 26 );

        JOptionPane.showMessageDialog(null, miBici);
        miBici.setNombre("UnaRueda");
        miBici.setId("C-02");
        miBici.setFreno(false);
        miBici.setColor("Azul");
        miBici.setTallaRin(25);
        JOptionPane.showMessageDialog(null, miBici);



        miBici.acelerar(); //llamado de metodo NO static
        //Cicla.acelerar(); //llamado de metodo static

    }
}
