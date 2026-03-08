package UML.TallerBIcicletas.model;

import java.util.Arrays;

public class Cliente {
    //Atributos
    private String id;
    private String nombre;
    private String telefono;

    // Arrays públicos o accesibles para que Taller los llene
    private Bicicleta[] listBicicletas;
    private int cantBicicletas; // Contador simple

    //Constructor
    public Cliente(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;

        this.listBicicletas = new Bicicleta[5]; // Capacidad para 5 bicis
        this.cantBicicletas = 0;
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

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Bicicleta[] getListBicicletas() { return listBicicletas; }
    public void setListBicicletas(Bicicleta[] listBicicletas) {
        this.listBicicletas = listBicicletas;
    }



    // Métodos para que el Taller gestione el array interno del cliente

    public int getCantBicicletas() { return cantBicicletas; }
    public void setCantBicicletas(int c) { this.cantBicicletas = c; }



}
