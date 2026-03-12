package UML.TallerBIcicletasDos.model;

import UML.TallerBIcicletas.model.Bicicleta;
import UML.TallerBIcicletas.model.Cliente;
import UML.TallerBIcicletas.model.EstadoOrden;
import UML.TallerBIcicletas.model.Mecanico;
import UML.TallerBIcicletas.model.Repuesto;
import UML.TallerBIcicletas.model.Tarea;

public class OrdenServicio {
    //Atributos
    private String codigo;
    private double costoTotal;
    private EstadoOrden estado;

    // Relaciones
    private Cliente cliente;
    private Bicicleta bicicleta;

    // Arrays internos (Solo almacenamiento)
    private Repuesto[] listRepuestos;
    private int cantRepuestos;

    private Tarea[] listTareas;
    private int cantTareas;

    private Mecanico[] listMecanicos;
    private int cantMecanicos;

    public OrdenServicio(String codigo, Cliente cliente, Bicicleta bicicleta) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.bicicleta = bicicleta;
        this.estado = EstadoOrden.RECIBIDA;
        this.costoTotal = 0;

        // Inicializar cajones vacíos
        listRepuestos = new Repuesto[10];
        this.cantRepuestos = 0;

        listTareas = new Tarea[10];
        this.cantTareas = 0;

        listMecanicos = new Mecanico[3];
        this.cantMecanicos = 0;
    }

    // Solo Getters y Setters necesarios para Taller
    public String getCodigo() { return codigo; }
    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costo) { this.costoTotal = costo; }

    public Repuesto[] getListRepuestos() { return listRepuestos; }
    public int getCantRepuestos() { return cantRepuestos; }
    public void setCantRepuestos(int c) { this.cantRepuestos = c; }

    public Tarea[] getListTareas() { return listTareas; }
    public int getCantTareas() { return cantTareas; }
    public void setCantTareas(int c) { this.cantTareas = c; }

    public EstadoOrden getEstado() {
        return this.estado;
    }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }

    //otros


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantMecanicos() {
        return cantMecanicos;
    }
    public void setCantMecanicos(int cantMecanicos) {
        this.cantMecanicos = cantMecanicos;
    }

    public Mecanico[] getListMecanicos() {
        return listMecanicos;
    }
    public void setListMecanicos(Mecanico[] listMecanicos) {
        this.listMecanicos = listMecanicos;
    }

    public void setListTareas(Tarea[] listTareas) {
        this.listTareas = listTareas;
    }

    public void setListRepuestos(Repuesto[] listRepuestos) {
        this.listRepuestos = listRepuestos;
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }
    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


}
