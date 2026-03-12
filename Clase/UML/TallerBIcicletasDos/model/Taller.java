package UML.TallerBIcicletasDos.model;

import UML.TallerBIcicletasDos.model.Bicicleta;
import UML.TallerBIcicletasDos.model.Cliente;
import UML.TallerBIcicletasDos.model.Mecanico;
import UML.TallerBIcicletasDos.model.OrdenServicio;
import UML.TallerBIcicletasDos.model.Repuesto;

import javax.swing.*;
import java.util.ArrayList;

public class Taller {
    // Listas Globales del Taller
    private Cliente[] listClientes;
    private int contClientes;

    private Bicicleta[] listBicicletas;

    private Repuesto[] listRepuestos;
    private int contRepuestos;

    private Mecanico[] listMecanicos;
    private int contMecanicos;

    private OrdenServicio[] listOrdenes;
    private int contOrdenes;

    public Taller() {
        this.listClientes = new Cliente[100];
        this.contClientes = 0;
        this.listBicicletas = new Bicicleta[100];
        this.listRepuestos = new Repuesto[50];
        this.contRepuestos = 0;
        this.listMecanicos = new Mecanico[10];
        this.contMecanicos = 0;
        this.listOrdenes = new OrdenServicio[200];
        this.contOrdenes = 0;
    }

    //Metodo Buscar Clientes
    public int buscarClienteById(String idBuscar) {
        for (int i = 0; i < listClientes.length; i++) {
            if (listClientes[i] != null && listClientes[i].getId().equals(idBuscar)) {
                return i;
            }
        }
        return -1;
    }

    //Métodos CRUD Cliente
    //Create - Registrar Cliente
    public boolean registrarCliente(String id, String nombre, String telefono, String direccion) {
        Cliente newCliente = new Cliente(id, nombre, telefono, direccion);

        if (buscarClienteById(id) == -1) {
            for (int i = 0; i < listClientes.length; i++) {
                if (listClientes[i] == null) {
                    listClientes[i] = newCliente;
                    return true;
                }
            }
        }
        return false;
    }
    public String listarClientes() {
        String listado = "";

        for (int i = 0; i < listClientes.length; i++) {
            // VALIDACIÓN: Solo entra si la posición tiene un objeto Cliente real
            if (listClientes[i] != null) {
                listado += listClientes[i].toString() + "\n--------------------\n";
            }
        }

        // Si al final el String sigue vacío, es que no hay nadie
        if (listado.equals("")) {
            return "No hay clientes registrados actualmente.";
        }

        return listado;
    }

    // Read - Consultar Cliente
    public Cliente consultarCliente(String id) {
        int posicion = buscarClienteById(id);
        if (posicion != -1) {
            return listClientes[posicion];
        }
        return null;
    }

   // Read - Ver Listado
    public String mostrarListaClientes() {
        String lista = "";
        for (int i = 0; i < listClientes.length; i++) {
            if (listClientes[i] != null) {
                lista += "Cedula: " + listClientes[i].getId()
                        + " | Nombre: " + listClientes[i].getNombre()
                        + " | Direccion: " + listClientes[i].getDireccion()
                        + " | Contacto: " + listClientes[i].getTelefono() + "\n";
            }
        }
        return lista.isEmpty() ? "No hay clientes registrados." : lista;
    }


    // Update - Actualizar Clientes
    public boolean actualizarClientes(String id, String nombre, String telefono, String direccion) {
        int posicion = buscarClienteById(id);
        if (posicion != -1) {
            listClientes[posicion].setNombre(nombre);
            listClientes[posicion].setTelefono(telefono);
            listClientes[posicion].setDireccion(direccion);
            return true;
        }
        return false;
    }

    //Delete - Eliminar Clientes
    public boolean eliminarClientes(String id) {
        int posicion = buscarClienteById(id);
        if (posicion != -1) {
            listClientes[posicion] = null;
            return true;
        }
        return false;
    }

    // ————————————————————————————————————————
    // Metodo Buscar Bicicletas
    public int buscarBicicletaBySerial(String serialBuscar) {
        for (int i = 0; i < listBicicletas.length; i++) {
            if (listBicicletas[i] != null && listBicicletas[i].getSerial().equals(serialBuscar)) {
                return i;
            }
        }
        return -1;
    }

    // Métodos CRUD Bicicletas
    // Create - Registrar Bicicleta (requiere cliente existente)
    public boolean registrarBicicleta(String idCliente, String serial, String marca, String color ) {
        int posCliente = buscarClienteById(idCliente);

        if (posCliente == -1){
            JOptionPane.showMessageDialog(null, "Error: El cliente con ID " + idCliente + " no existe.");
            return false;
        }

        Cliente cliente = listClientes[posCliente];
        Bicicleta newBicicleta = new Bicicleta(serial, marca, color);
        newBicicleta.setTheCLiente(cliente);
        cliente.setBicicleta(newBicicleta);

        if (buscarBicicletaBySerial(serial) == -1) {
            for (int i = 0; i < listBicicletas.length; i++) {
                if (listBicicletas[i] == null) {
                    listBicicletas[i] = newBicicleta;
                    return true;
                }
            }
        }
        return false;
    }

    // Read - Consultar Bicicleta
    public Bicicleta consultarBicicletas(String serial) {
        int posicion = buscarBicicletaBySerial(serial);
        if (posicion != -1) {
            return listBicicletas[posicion];
        }
        return null;
    }

    // Read - Ver Listado Bicicletas
    public String mostrarListaBicicletas() {
        String listaB = "";
        for (int i = 0; i < listBicicletas.length; i++) {
            if (listBicicletas[i] != null) {
                listaB += "Serial: " + listBicicletas[i].getSerial()
                        + " | Cliente: " + listBicicletas[i].getNombreCliente()
                        + " | Marca: " + listBicicletas[i].getMarca()
                        + " | Color: " + listBicicletas[i].getColor() + "\n";
            }
        }
        return listaB.isEmpty() ? "No hay bicicletas registradas." : listaB;
    }

    // Update - Actualizar BIcicletas
    public boolean actualizarBicicleta(String serial, String marca, String color) {
        int posicion = buscarBicicletaBySerial(serial);
        if (posicion != -1) {
            listBicicletas[posicion].setMarca(marca);
            listBicicletas[posicion].setColor(color);
            return true;
        }
        return false;
    }

    // Delete - Eliminar Bicicletas
    public boolean eliminarBicicletas(String serial) {
        int posicion = buscarBicicletaBySerial(serial);
        if (posicion != -1) {
            listBicicletas[posicion] = null;
            return true;
        }
        return false;
    }

    public Cliente[] getListClientes() {
        return listClientes;
    }
    public void setListClientes(Cliente[] listClientes) {
        this.listClientes = listClientes;
    }

    public int getContClientes() {
        return contClientes;
    }
    public void setContClientes(int contClientes) {
        this.contClientes = contClientes;
    }

    public Bicicleta[] getListBicicletas() {
        return listBicicletas;
    }
    public void setListBicicletas(Bicicleta[] listBicicletas) {
        this.listBicicletas = listBicicletas;
    }

    public Repuesto[] getListRepuestos() {
        return listRepuestos;
    }
    public void setListRepuestos(Repuesto[] listRepuestos) {
        this.listRepuestos = listRepuestos;
    }

    public int getContRepuestos() {
        return contRepuestos;
    }
    public void setContRepuestos(int contRepuestos) {
        this.contRepuestos = contRepuestos;
    }

    public Mecanico[] getListMecanicos() {
        return listMecanicos;
    }
    public void setListMecanicos(Mecanico[] listMecanicos) {
        this.listMecanicos = listMecanicos;
    }

    public int getContMecanicos() {
        return contMecanicos;
    }
    public void setContMecanicos(int contMecanicos) {
        this.contMecanicos = contMecanicos;
    }

    public OrdenServicio[] getListOrdenes() {
        return listOrdenes;
    }
    public void setListOrdenes(OrdenServicio[] listOrdenes) {
        this.listOrdenes = listOrdenes;
    }

    public int getContOrdenes() {
        return contOrdenes;
    }
    public void setContOrdenes(int contOrdenes) {
        this.contOrdenes = contOrdenes;
    }
}
