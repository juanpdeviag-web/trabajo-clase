package Uml;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Cicla miBici = new Uml.Cicla("C-01", "todoterreno", true, "negro", 26 );

        JOptionPane.showMessageDialog(null, miBici);
        miBici.setNombre("UnaRueda");
        System.out.println(miBici.getNombre);



        miBici.acelerar(); //llamado de metodo NO static
        //Cicla.acelerar(); //llamado de metodo static
        System.out.println(miBici.getId);
        miBici.setId("C-02");
        System.out.println(miBici.getId);


    }
}
