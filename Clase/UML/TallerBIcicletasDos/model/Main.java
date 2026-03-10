package UML.TallerBIcicletasDos.model;

import UML.TallerBIcicletas.model.Bicicleta;
import UML.TallerBIcicletas.model.Cliente;
import UML.TallerBIcicletas.model.Taller;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

public class Main {

    // Instancia única del Taller (El Cerebro)
    static UML.TallerBIcicletas.model.Taller miTaller = new Taller();

    public static void main(String[] args) {



        // --- PRECARGA DE DATOS ---
        // (Para que no tengas que digitar todo cada vez que pruebas)
//        miTaller.registrarRepuesto("Frenos", 50.0, 10);
//        miTaller.registrarRepuesto("Aceite", 20.0, 5);
        miTaller.registrarCliente("123", "Juan Perez", "321000", "Calle 2");
//        miTaller.registrarMecanico("M01", "Pedro El Mecanico", "555-001", 1500.0);

        int opcion = 0;

        do {
            try {
                String menu = "=== TALLER DE BICICLETAS ===\n"
                        + "--- REGISTROS BÁSICOS ---\n"
                        + "1. Registrar Cliente\n"
                        + "2. Registrar Bicicleta (A un Cliente)\n"
                        + "11. Salir\n\n"
                        + "Seleccione una opción:";

                String entrada = JOptionPane.showInputDialog(menu);

                // Si presiona Cancelar, salimos
                if (entrada == null) break;

                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        Clientes();
                        break;
                    case 2:
                        Bicicleta();
                        break;
                      // Submenú Deletes
                    case 11:
                        JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Debe ingresar un número.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage());
            }

        } while (opcion != 11);
    }

    // ========================================================
    // MÉTODOS DE REGISTRO
    // ========================================================

    public static void Clientes(){
        int opcion = 0;

        while (opcion <= 5) {
            String menu = "===== TALLER BICICLETAS =====\n"
                    + "1. Registrar cliente\n"
                    + "2. Mostrar cliente\n"
                    + "3. Actualizar cliente\n"
                    + "4. Eliminar cliente\n"
                    + "5. Salir";

            String entrada = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);

            if (entrada == null) {
                break;
            }

            opcion = Integer.parseInt(entrada);
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    mostrarCliente();
                    break;
                case 3:
                    actualizarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
            }
        }
    }

    public static void registrarCliente() {
        String id = JOptionPane.showInputDialog("Cédula del Cliente:");
        if (id == null || id.trim().isEmpty()) return;

        String nombre = JOptionPane.showInputDialog("Nombre del Cliente:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String direccion = JOptionPane.showInputDialog("Dirección del Cliente:");
        if (direccion == null || direccion.trim().isEmpty()) return;

        String telefono = JOptionPane.showInputDialog("Teléfono del Cliente:");
        if (telefono == null || telefono.trim().isEmpty()) return;

        boolean registrado = miTaller.registrarCliente(id, nombre, telefono, direccion);
        JOptionPane.showMessageDialog(null, registrado ? "Cliente registrado.": "No se pudo registrar el Cliente");
    }

    public static String mostrarCliente() {
        JOptionPane.showMessageDialog(null, miTaller.mostrarListaClientes());
        String id = JOptionPane.showInputDialog("Ingrese el ID del Cliente:");
        Cliente cliente = miTaller.consultarCliente(id);
        String infoCliente = "";
        if (cliente != null){
            infoCliente = "ID: " + cliente.getId()
                    + "\nNombre: "+ cliente.getNombre()
                    + "\nDireccion: "+ cliente.getDireccion()
                    + "\nContacto: "+ cliente.getTelefono();
            JOptionPane.showMessageDialog(null, infoCliente, "Información del Propietario", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el cliente.","Error", JOptionPane.ERROR_MESSAGE);
        }
        return infoCliente;
    }


    public static void actualizarCliente(){

        JOptionPane.showMessageDialog(null, miTaller.mostrarListaClientes());

        String id = JOptionPane.showInputDialog("Ingrese el ID del Cliente:");


        String nombreClienteAct = JOptionPane.showInputDialog("Ingrese el nuevo nombre");
        String direccionClienteAct = JOptionPane.showInputDialog("Ingrese nueva dirección");
        String contactoClienteAct = JOptionPane.showInputDialog("Ingrese nuevo teléfono");

        boolean actualizadoCliente = miTaller.actualizarClientes(id, nombreClienteAct, direccionClienteAct, contactoClienteAct);
        JOptionPane.showMessageDialog(null, miTaller.mostrarListaClientes());
        JOptionPane.showMessageDialog(null, actualizadoCliente ? "Cliente actualizado exitosamente" : "No se encontró el cliente");
    }
    public static void eliminarCliente(){
        JOptionPane.showMessageDialog(null, miTaller.mostrarListaClientes());

        String id = JOptionPane.showInputDialog("Ingrese el ID del cliente a Eliminar: ");

        boolean eliminadoCLiente = miTaller.eliminarClientes(id);
        JOptionPane.showMessageDialog(null, eliminadoCLiente ? "Cliente ha sido eliminado exitosamente." : "No se encontró el cliente");
    }


    //----------------
    public static void Bicicleta(){
        int opcion = 0;

        while (opcion <= 5) {
            String menu = "===== TALLER BICICLETAS =====\n"
                    + "1. Registrar bicicleta\n"
                    + "2. Mostrar bicicleta\n"
                    + "3. Actualizar bicicleta\n"
                    + "4. Eliminar bicicleta\n"
                    + "5. Salir";

            String entrada = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);

            if (entrada == null) {
                break;
            }

            opcion = Integer.parseInt(entrada);
            switch (opcion) {
                case 1:
                    registrarBici();
                    break;
                case 2:
                    mostrarBici();
                    break;
            }
        }
    }



    public static void registrarBici() {
        JOptionPane.showMessageDialog(null, miTaller.mostrarListaClientes());

        String idCliente = JOptionPane.showInputDialog("Ingrese ID del dueño:");
        if (idCliente == null || idCliente.trim().isEmpty()) return;

        String serial = JOptionPane.showInputDialog("Serial de la Bicicleta:");
        if (serial == null || serial.trim().isEmpty()) return;

        String marca = JOptionPane.showInputDialog("Marca de la Bicicleta:");
        if (marca == null || marca.trim().isEmpty()) return;

        String color = JOptionPane.showInputDialog("Color de la Bicicleta:");
        if (color == null || color.trim().isEmpty()) return;


        boolean registradoBici = miTaller.registrarBicicleta(idCliente, serial, marca, color);
        JOptionPane.showMessageDialog(null, registradoBici ? "Intento de registrar bicicleta completado.": "Intento de registro fallido");
    }

    public static String mostrarBici() {
        JOptionPane.showMessageDialog(null, miTaller.mostrarListaBicicletas());

        String serial = JOptionPane.showInputDialog("Ingrese serial de la Bici:");
        Bicicleta bicicleta = miTaller.consultarBicicletas(serial);

        String infoBici = "";
        if (bicicleta != null){
            infoBici = "Serial: " + bicicleta.getSerial()
                    + "\nMarca: "+ bicicleta.getMarca()
                    + "\nColor: "+ bicicleta.getColor();
            JOptionPane.showMessageDialog(null, infoBici, "Información de la Bici", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el cliente.","Error", JOptionPane.ERROR_MESSAGE);
        }
        return infoBici;
    }

}