package UML.TallerBIcicletasDos.model;

import UML.TallerBIcicletasDos.model.Bicicleta;

import java.util.Arrays;

public class Cliente {
    //Atributos
    private String id;
    private String nombre;
    private String telefono;
    private String direccion;

    // Arrays públicos o accesibles para que Taller los llene
    private Bicicleta[] listBicicletas;
    private int cantBicicletas; // Contador simple

    //Constructor
    public Cliente(String id, String nombre, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;

        this.listBicicletas = new Bicicleta[5]; // Capacidad para 5 bicis
        this.cantBicicletas = 0;
    }

    //Metodo para añadir bicicletas
    public void setBicicleta(Bicicleta newBicicleta){
        for (int i = 0; i < listBicicletas.length; i++) {
            if (listBicicletas[i] == null) {
                this.listBicicletas[i] = newBicicleta;
            }
        }
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

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Métodos para que el Taller gestione el array interno del cliente
    public Bicicleta[] getListBicicletas() { return listBicicletas; }
    public void setListBicicletas(Bicicleta[] listBicicletas) {
        this.listBicicletas = listBicicletas;
    }


    public int getCantBicicletas() { return cantBicicletas; }
    public void setCantBicicletas(int c) { this.cantBicicletas = c; }


    //toString


    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", listBicicletas=" + Arrays.toString(listBicicletas) +
                ", cantBicicletas=" + cantBicicletas +
                '}';
    }
}
