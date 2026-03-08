package UML.TallerBIcicletas.model;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

public class Main {

    // Instancia única del Taller (El Cerebro)
    static Taller miTaller = new Taller();

    public static void main(String[] args) {

        // --- PRECARGA DE DATOS ---
        // (Para que no tengas que digitar todo cada vez que pruebas)
        miTaller.registrarRepuesto("Frenos", 50.0, 10);
        miTaller.registrarRepuesto("Aceite", 20.0, 5);
        miTaller.registrarCliente("123", "Juan Perez");
        miTaller.registrarMecanico("M01", "Pedro El Mecanico", "555-001", 1500.0);

        int opcion = 0;

        do {
            try {
                String menu = "=== TALLER DE BICICLETAS ===\n"
                        + "--- REGISTROS BÁSICOS ---\n"
                        + "1. Registrar Cliente\n"
                        + "2. Registrar Bicicleta (A un Cliente)\n"
                        + "3. Registrar Mecánico\n"
                        + "4. Registrar Repuesto (Inventario)\n"
                        + "--- ÓRDENES DE SERVICIO ---\n"
                        + "5. Crear Nueva Orden\n"
                        + "6. Gestionar Orden (Tareas/Repuestos/Estado)\n"
                        + "7. Ver Factura de Orden\n"
                        + "--- GESTIÓN DE DATOS (CRUD) ---\n"
                        + "8. CONSULTAR (Listados)\n"
                        + "9. MODIFICAR (Actualizar datos)\n"
                        + "10. ELIMINAR (Borrar registros)\n"
                        + "11. Salir\n\n"
                        + "Seleccione una opción:";

                String entrada = JOptionPane.showInputDialog(menu);

                // Si presiona Cancelar, salimos
                if (entrada == null) break;

                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1: registrarCliente(); break;
                    case 2: registrarBici(); break;
                    case 3: registrarMecanico(); break;
                    case 4: registrarRepuesto(); break;
                    case 5: crearOrden(); break;
                    case 6: gestionOrdenes(); break; // Submenú Ordenes
                    case 7: verFactura(); break;
                    case 8: menuConsultas(); break;  // Submenú Listados
                    case 9: menuModificar(); break;  // Submenú Updates
                    case 10: menuEliminar(); break;  // Submenú Deletes
                    case 11: JOptionPane.showMessageDialog(null, "¡Hasta luego!"); break;
                    default: JOptionPane.showMessageDialog(null, "Opción no válida.");
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

    public static void registrarCliente() {
        String id = JOptionPane.showInputDialog("Cédula del Cliente:");
        String nombre = JOptionPane.showInputDialog("Nombre del Cliente:");
        miTaller.registrarCliente(id, nombre);
        JOptionPane.showMessageDialog(null, "Cliente registrado.");
    }

    public static void registrarBici() {
        String idCliente = JOptionPane.showInputDialog("Cédula del Dueño:");
        String serial = JOptionPane.showInputDialog("Serial de la Bicicleta:");
        String marca = JOptionPane.showInputDialog("Marca de la Bicicleta:");
        miTaller.agregarBicicletaACliente(idCliente, serial, marca);
        JOptionPane.showMessageDialog(null, "Bicicleta intentada registrar (Verifique si el cliente existe).");
    }

    public static void registrarMecanico() {
        String id = JOptionPane.showInputDialog("ID Mecánico:");
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String tel = JOptionPane.showInputDialog("Teléfono:");
        double sal = Double.parseDouble(JOptionPane.showInputDialog("Salario:"));
        miTaller.registrarMecanico(id, nombre, tel, sal);
        JOptionPane.showMessageDialog(null, "Mecánico registrado.");
    }

    public static void registrarRepuesto() {
        String nombre = JOptionPane.showInputDialog("Nombre del Repuesto:");
        double costo = Double.parseDouble(JOptionPane.showInputDialog("Costo Unitario:"));
        int cant = Integer.parseInt(JOptionPane.showInputDialog("Cantidad Inicial:"));
        miTaller.registrarRepuesto(nombre, costo, cant);
        JOptionPane.showMessageDialog(null, "Repuesto registrado.");
    }

    // ========================================================
    // MÉTODOS DE ÓRDENES
    // ========================================================

    public static void crearOrden() {
        String codigo = JOptionPane.showInputDialog("Código ÚNICO de Orden:");
        String idCliente = JOptionPane.showInputDialog("Cédula del Cliente:");
        String serial = JOptionPane.showInputDialog("Serial de la Bici:");

        miTaller.crearOrden(codigo, idCliente, serial);
        JOptionPane.showMessageDialog(null, "Orden creada (Si cliente y bici existen).");
    }

    public static void gestionOrdenes() {
        String[] opciones = {"Agregar Tarea", "Agregar Repuesto", "Cambiar Estado"};
        int seleccion = JOptionPane.showOptionDialog(null, "¿Qué desea hacer con la orden?", "Gestión Orden",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 0) { // Tarea
            String cod = JOptionPane.showInputDialog("Código de Orden:");
            String desc = JOptionPane.showInputDialog("Descripción Tarea:");
            double costo = Double.parseDouble(JOptionPane.showInputDialog("Costo Mano Obra:"));
            miTaller.agregarTareaAOrden(cod, desc, costo);
            JOptionPane.showMessageDialog(null, "Tarea agregada.");
        }
        else if (seleccion == 1) { // Repuesto
            String cod = JOptionPane.showInputDialog("Código de Orden:");
            String nom = JOptionPane.showInputDialog("Nombre exacto del Repuesto:");
            miTaller.agregarRepuestoAOrden(cod, nom);
            JOptionPane.showMessageDialog(null, "Repuesto agregado (si hay stock).");
        }
        else if (seleccion == 2) { // Estado
            String cod = JOptionPane.showInputDialog("Código de Orden:");
            EstadoOrden[] estados = EstadoOrden.values();
            EstadoOrden nuevoEstado = (EstadoOrden) JOptionPane.showInputDialog(null,
                    "Seleccione nuevo estado:", "Estado",
                    JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);

            if(nuevoEstado != null) {
                miTaller.cambiarEstadoOrden(cod, nuevoEstado);
                JOptionPane.showMessageDialog(null, "Estado actualizado.");
            }
        }
    }

    public static void verFactura() {
        String cod = JOptionPane.showInputDialog("Código de Orden:");
        String factura = miTaller.generarFactura(cod);
        JOptionPane.showMessageDialog(null, factura);
    }

    // ========================================================
    // SUBMENÚS DE GESTIÓN (CRUD)
    // ========================================================

    public static void menuConsultas() {
        String[] ops = {"Listar Clientes", "Listar Mecánicos", "Listar Inventario", "Ver Bicis de un Cliente"};
        int x = JOptionPane.showOptionDialog(null, "¿Qué desea consultar?", "Consultas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ops, ops[0]);

        if (x == 0) JOptionPane.showMessageDialog(null, miTaller.listarClientes());
        else if (x == 1) JOptionPane.showMessageDialog(null, miTaller.listarMecanicos());
        else if (x == 2) JOptionPane.showMessageDialog(null, miTaller.listarInventario());
        else if (x == 3) {
            String id = JOptionPane.showInputDialog("ID del Cliente:");
            JOptionPane.showMessageDialog(null, miTaller.listarBicicletasDeCliente(id));
        }
    }

    public static void menuModificar() {
        String[] ops = {"Teléfono Cliente", "Salario Mecánico", "Precio Repuesto", "Color Bicicleta"};
        int x = JOptionPane.showOptionDialog(null, "¿Qué desea modificar?", "Actualizar Datos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ops, ops[0]);

        if (x == 0) { // Cliente
            String id = JOptionPane.showInputDialog("ID Cliente:");
            String tel = JOptionPane.showInputDialog("Nuevo Teléfono:");
            boolean ok = miTaller.actualizarCliente(id, tel);
            JOptionPane.showMessageDialog(null, ok ? "Actualizado" : "No encontrado");
        }
        else if (x == 1) { // Mecánico
            String id = JOptionPane.showInputDialog("ID Mecánico:");
            String tel = JOptionPane.showInputDialog("Nuevo Teléfono:");
            double sal = Double.parseDouble(JOptionPane.showInputDialog("Nuevo Salario:"));
            boolean ok = miTaller.actualizarMecanico(id, tel, sal);
            JOptionPane.showMessageDialog(null, ok ? "Actualizado" : "No encontrado");
        }
        else if (x == 2) { // Repuesto
            String nom = JOptionPane.showInputDialog("Nombre Repuesto:");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo Precio:"));
            boolean ok = miTaller.actualizarPrecioRepuesto(nom, precio);
            JOptionPane.showMessageDialog(null, ok ? "Actualizado" : "No encontrado");
        }
        else if (x == 3) { // Bici
            String id = JOptionPane.showInputDialog("ID Dueño:");
            String serial = JOptionPane.showInputDialog("Serial Bici:");
            String color = JOptionPane.showInputDialog("Nuevo Color:");
            boolean ok = miTaller.actualizarBicicleta(id, serial, color);
            JOptionPane.showMessageDialog(null, ok ? "Actualizado" : "No encontrado");
        }
    }

    public static void menuEliminar() {
        String[] ops = {"Eliminar Cliente", "Eliminar Mecánico", "Eliminar Repuesto", "Eliminar Bicicleta"};
        int x = JOptionPane.showOptionDialog(null, "ADVERTENCIA: ¿Qué desea eliminar?", "Eliminar Datos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, ops, ops[0]);

        if (x == 0) { // Cliente
            String id = JOptionPane.showInputDialog("ID Cliente:");
            boolean ok = miTaller.eliminarCliente(id);
            JOptionPane.showMessageDialog(null, ok ? "Eliminado" : "No encontrado");
        }
        else if (x == 1) { // Mecánico
            String id = JOptionPane.showInputDialog("ID Mecánico:");
            boolean ok = miTaller.eliminarMecanico(id);
            JOptionPane.showMessageDialog(null, ok ? "Eliminado" : "No encontrado");
        }
        else if (x == 2) { // Repuesto
            String nom = JOptionPane.showInputDialog("Nombre Repuesto:");
            boolean ok = miTaller.eliminarRepuesto(nom);
            JOptionPane.showMessageDialog(null, ok ? "Eliminado" : "No encontrado");
        }
        else if (x == 3) { // Bici
            String id = JOptionPane.showInputDialog("ID Dueño:");
            String serial = JOptionPane.showInputDialog("Serial Bici:");
            boolean ok = miTaller.eliminarBicicleta(id, serial);
            JOptionPane.showMessageDialog(null, ok ? "Eliminada" : "No encontrada");
        }
    }
}