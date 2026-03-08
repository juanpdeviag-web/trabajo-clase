package UML.TallerBIcicletas.model;

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
        this.listRepuestos = new Repuesto[10];
        this.cantRepuestos = 0;

        this.listTareas = new Tarea[10];
        this.cantTareas = 0;

        this.listMecanicos = new Mecanico[3];
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


}
