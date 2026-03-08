package UML.TallerBIcicletas.model;

import java.util.ArrayList;

public class Taller {
    // Listas Globales del Taller
    private Cliente[] clientes;
    private int contClientes;

    private Repuesto[] inventario;
    private int contRepuestos;

    private Mecanico[] nomina;
    private int contMecanicos;

    private OrdenServicio[] ordenes;
    private int contOrdenes;

    public Taller() {
        this.clientes = new Cliente[100];
        this.contClientes = 0;
        this.inventario = new Repuesto[50];
        this.contRepuestos = 0;
        this.nomina = new Mecanico[10];
        this.contMecanicos = 0;
        this.ordenes = new OrdenServicio[200];
        this.contOrdenes = 0;
    }

    // --------------------------------------------------------
    // GESTIÓN DE REGISTROS BÁSICOS
    // --------------------------------------------------------

    public void registrarCliente(String id, String nombre) {
        if (contClientes < 100) {
            clientes[contClientes] = new Cliente(id, nombre);
            contClientes++;
        }
    }

    public void registrarRepuesto(String nombre, double costo, int cantidad) {
        if (contRepuestos < 50) {
            inventario[contRepuestos] = new Repuesto(nombre, costo, cantidad);
            contRepuestos++;
        }
    }

    // --------------------------------------------------------
    // FUNCIÓN: TALLER ASIGNA BICI A CLIENTE
    // --------------------------------------------------------
    public void agregarBicicletaACliente(String idCliente, String serial, String marca) {
        // 1. Buscamos al cliente
        Cliente elCliente = null;
        for(int i = 0; i < contClientes; i++) {
            if(clientes[i].getId().equals(idCliente)) {
                elCliente = clientes[i];
                break;
            }
        }

        // 2. Si existe, el Taller mete la bici en el array del cliente
        if (elCliente != null) {
            // Obtenemos el array del cliente y su contador actual
            Bicicleta[] susBicis = elCliente.getListBicicletas();
            int cuantosTiene = elCliente.getCantBicicletas();

            if (cuantosTiene < 5) {
                susBicis[cuantosTiene] = new Bicicleta(serial, marca);
                // Actualizamos el contador del cliente
                elCliente.setCantBicicletas(cuantosTiene + 1);
            }
        }
    }

    // --------------------------------------------------------
    // FUNCIÓN: CREAR ORDEN
    // --------------------------------------------------------
    public void crearOrden(String codigoOrden, String idCliente, String serialBici) {
        // Buscamos Cliente
        Cliente elCliente = null;
        for(int i = 0; i < contClientes; i++) {
            if(clientes[i].getId().equals(idCliente)) elCliente = clientes[i];
        }

        // Buscamos la Bici DENTRO del cliente
        Bicicleta laBici = null;
        if (elCliente != null) {
            Bicicleta[] bicisCliente = elCliente.getListBicicletas();
            for(int i = 0; i < elCliente.getCantBicicletas(); i++) {
                if(bicisCliente[i].getSerial().equals(serialBici)) {
                    laBici = bicisCliente[i];
                }
            }
        }

        // Si todo existe, creamos la orden
        if (elCliente != null && laBici != null && contOrdenes < 200) {
            ordenes[contOrdenes] = new OrdenServicio(codigoOrden, elCliente, laBici);
            contOrdenes++;
        }
    }

    // --------------------------------------------------------
    // FUNCIÓN: AGREGAR TAREA A UNA ORDEN
    // --------------------------------------------------------
    public void agregarTareaAOrden(String codigoOrden, String nombreTarea, double costo) {
        // 1. Buscamos la orden
        OrdenServicio laOrden = buscarOrden(codigoOrden);

        if (laOrden != null) {
            // 2. Accedemos a los arrays internos de esa orden
            Tarea[] tareasOrden = laOrden.getListTareas();
            int cant = laOrden.getCantTareas();

            // 3. Agregamos la tarea
            if (cant < 10) {
                tareasOrden[cant] = new Tarea(nombreTarea, costo);
                laOrden.setCantTareas(cant + 1); // Actualizamos contador

                // 4. EL TALLER RECALCULA EL COSTO INMEDIATAMENTE
                actualizarCostoOrden(laOrden);
            }
        }
    }

    // --------------------------------------------------------
    // FUNCIÓN: AGREGAR REPUESTO A UNA ORDEN
    // --------------------------------------------------------
    public void agregarRepuestoAOrden(String codigoOrden, String nombreRepuesto) {
        OrdenServicio laOrden = buscarOrden(codigoOrden);

        // Buscamos el repuesto en el inventario del TALLER
        Repuesto elRepuesto = null;
        for(int i=0; i < contRepuestos; i++) {
            if(inventario[i].getNombre().equals(nombreRepuesto)) {
                elRepuesto = inventario[i];
            }
        }

        if (laOrden != null && elRepuesto != null && elRepuesto.getCantidad() > 0) {
            // 1. Restamos del inventario del taller
            elRepuesto.setCantidad(elRepuesto.getCantidad() - 1);

            // 2. Agregamos al array de la orden
            Repuesto[] repuestosOrden = laOrden.getListRepuestos();
            int cant = laOrden.getCantRepuestos();

            if (cant < 10) {
                repuestosOrden[cant] = elRepuesto;
                laOrden.setCantRepuestos(cant + 1);

                // 3. Recalculamos costo total
                actualizarCostoOrden(laOrden);
            }
        }
    }

    // --------------------------------------------------------
    // LÓGICA DE CALCULO (Privada, usada internamente)
    // --------------------------------------------------------
    private void actualizarCostoOrden(OrdenServicio orden) {
        double total = 0;

        // Sumar tareas de la orden
        Tarea[] t = orden.getListTareas();
        for(int i=0; i < orden.getCantTareas(); i++) {
            total = total + t[i].getCosto();
        }

        // Sumar repuestos de la orden
        Repuesto[] r = orden.getListRepuestos();
        for(int i=0; i < orden.getCantRepuestos(); i++) {
            total = total + r[i].getCosto();
        }

        // El taller escribe el precio final en la etiqueta de la orden
        orden.setCostoTotal(total);
    }

    // Método auxiliar para no repetir código de búsqueda
    private OrdenServicio buscarOrden(String codigo) {
        for(int i=0; i < contOrdenes; i++) {
            if(ordenes[i].getCodigo().equals(codigo)) {
                return ordenes[i];
            }
        }
        return null;
    }

    public String generarFactura(String codigoOrden) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) return "Orden no encontrada.";

        String factura = "=== FACTURA ORDEN " + orden.getCodigo() + " ===\n";
        factura += "Estado: " + orden.getEstado() + "\n";
        factura += "--------------------------------\n";
        factura += "Mano de Obra (Tareas): " + orden.getCantTareas() + "\n";
        factura += "Repuestos Utilizados: " + orden.getCantRepuestos() + "\n";
        factura += "--------------------------------\n";
        factura += "TOTAL A PAGAR: $" + orden.getCostoTotal() + "\n";

        return factura;
    }

    //CRUD
    // --- CREATE: Ya tenías registrarMecanico, asegúrate que reciba todos los datos ---
    public void registrarMecanico(String id, String nombre, String telefono, double salario) {
        if (contMecanicos < 10) {
            nomina[contMecanicos] = new Mecanico(id, nombre, telefono, salario);
            contMecanicos++;
        }
    }


    // --- READ: Listar Clientes ---
    public String listarClientes() {
        String lista = "=== LISTA DE CLIENTES ===\n";
        for(int i = 0; i < contClientes; i++) {
            lista += (i+1) + ". " + clientes[i].getNombre() + " (ID: " + clientes[i].getId() + ")\n";
        }
        return lista;
    }

    // --- READ: Ver bicis de un cliente específico ---
    public String listarBicicletasDeCliente(String idCliente) {
        Cliente elCliente = buscarCliente(idCliente); // Reusamos el método auxiliar
        if (elCliente == null) return "Cliente no encontrado.";

        String lista = "Bicicletas de " + elCliente.getNombre() + ":\n";
        Bicicleta[] susBicis = elCliente.getListBicicletas();
        int cant = elCliente.getCantBicicletas();

        if (cant == 0) return "Este cliente no tiene bicicletas registradas.";

        for(int i = 0; i < cant; i++) {
            lista += "- Serial: " + susBicis[i].getSerial() +
                    " | Marca: " + susBicis[i].getMarca() + "\n";
        }
        return lista;
    }

    // --- READ: Listar Inventario ---
    public String listarInventario() {
        String lista = "=== INVENTARIO ===\n";
        for(int i = 0; i < contRepuestos; i++) {
            lista += "- " + inventario[i].getNombre() +
                    " | Precio: $" + inventario[i].getCosto() +
                    " | Stock: " + inventario[i].getCantidad() + "\n";
        }
        return lista;
    }

    // --- READ: Listar Mecánicos ---
    public String listarMecanicos() {
        String lista = "=== NÓMINA DE MECÁNICOS ===\n";
        for(int i = 0; i < contMecanicos; i++) {
            lista += (i+1) + ". " + nomina[i].getNombre() +
                    " (ID: " + nomina[i].getId() + ") - Tel: " + nomina[i].getTelefono() + "\n";
        }
        return lista;
    }

    // --- UPDATE: Modificar teléfono de cliente ---
    public boolean actualizarCliente(String id, String nuevoTelefono) {
        for(int i = 0; i < contClientes; i++) {
            if(clientes[i].getId().equals(id)) {
                clientes[i].setTelefono(nuevoTelefono); // Usamos el setter
                return true; // ¡Éxito!
            }
        }
        return false; // No lo encontramos
    }

    // --- UPDATE: Modificar precio de repuesto ---
    public boolean actualizarPrecioRepuesto(String nombre, double nuevoPrecio) {
        for(int i = 0; i < contRepuestos; i++) {
            if(inventario[i].getNombre().equals(nombre)) {
                inventario[i].setCosto(nuevoPrecio);
                return true;
            }
        }
        return false;
    }

    // --- UPDATE: Cambiar Estado de Orden ---
    public boolean cambiarEstadoOrden(String codigo, EstadoOrden nuevoEstado) {
        OrdenServicio orden = buscarOrden(codigo);
        if(orden != null) {
            orden.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    // --- UPDATE: Modificar teléfono y salario (Mecanico) ---
    public boolean actualizarMecanico(String id, String nuevoTel, double nuevoSalario) {
        for(int i = 0; i < contMecanicos; i++) {
            if(nomina[i].getId().equals(id)) {
                nomina[i].setTelefono(nuevoTel);
                nomina[i].setSalario(nuevoSalario);
                return true;
            }
        }
        return false;
    }

    // --- UPDATE: Pintar bicicleta (Cambiar color) ---
    public boolean actualizarBicicleta(String idCliente, String serialBici, String nuevoColor) {
        Cliente elCliente = buscarCliente(idCliente);
        if (elCliente != null) {
            Bicicleta[] susBicis = elCliente.getListBicicletas();
            for(int i = 0; i < elCliente.getCantBicicletas(); i++) {
                if (susBicis[i].getSerial().equals(serialBici)) {
                    susBicis[i].setColor(nuevoColor); // ¡Encontrada y modificada!
                    return true;
                }
            }
        }
        return false;
    }

    // --- DELETE: Eliminar Cliente ---
    public boolean eliminarCliente(String id) {
        int posicionEncontrada = -1;

        // 1. Buscamos dónde está
        for(int i = 0; i < contClientes; i++) {
            if(clientes[i].getId().equals(id)) {
                posicionEncontrada = i;
                break;
            }
        }

        // 2. Si lo encontramos, movemos la fila
        if (posicionEncontrada != -1) {
            // Desde la posición encontrada hasta el final, movemos el de la derecha a la izquierda
            for(int i = posicionEncontrada; i < contClientes - 1; i++) {
                clientes[i] = clientes[i + 1];
            }

            // El último lugar queda duplicado, así que lo limpiamos
            clientes[contClientes - 1] = null;

            // Bajamos el contador
            contClientes--;
            return true;
        }
        return false;
    }

    // --- DELETE: Eliminar Repuesto ---
    public boolean eliminarRepuesto(String nombre) {
        int pos = -1;
        for(int i = 0; i < contRepuestos; i++) {
            if(inventario[i].getNombre().equals(nombre)) {
                pos = i;
                break;
            }
        }

        if (pos != -1) {
            for(int i = pos; i < contRepuestos - 1; i++) {
                inventario[i] = inventario[i + 1];
            }
            inventario[contRepuestos - 1] = null;
            contRepuestos--;
            return true;
        }
        return false;
    }


    // --- DELETE: Despedir Mecánico ---
    public boolean eliminarMecanico(String id) {
        int pos = -1;
        for(int i = 0; i < contMecanicos; i++) {
            if(nomina[i].getId().equals(id)) {
                pos = i;
                break;
            }
        }

        if (pos != -1) {
            // Mover fila
            for(int i = pos; i < contMecanicos - 1; i++) {
                nomina[i] = nomina[i + 1];
            }
            nomina[contMecanicos - 1] = null;
            contMecanicos--;
            return true;
        }
        return false;
    }

    // --- DELETE: Eliminar una bicicleta de un cliente ---
    public boolean eliminarBicicleta(String idCliente, String serialBici) {
        Cliente elCliente = buscarCliente(idCliente);

        if (elCliente != null) {
            Bicicleta[] susBicis = elCliente.getListBicicletas();
            int cant = elCliente.getCantBicicletas();
            int posBici = -1;

            // 1. Buscamos la bici DENTRO del cliente
            for(int i = 0; i < cant; i++) {
                if (susBicis[i].getSerial().equals(serialBici)) {
                    posBici = i;
                    break;
                }
            }

            // 2. Si la encontramos, movemos el array DEL CLIENTE
            if (posBici != -1) {
                for(int i = posBici; i < cant - 1; i++) {
                    susBicis[i] = susBicis[i + 1];
                }
                susBicis[cant - 1] = null; // Borrar la última copia

                // Importante: Actualizar el contador DEL CLIENTE
                elCliente.setCantBicicletas(cant - 1);
                return true;
            }
        }
        return false;
    }

    private Cliente buscarCliente(String id) {
        // Recorremos la lista de clientes registrados
        for (int i = 0; i < contClientes; i++) {
            // Si el ID coincide...
            if (clientes[i].getId().equals(id)) {
                return clientes[i]; // ...Devolvemos el objeto Cliente completo
            }
        }
        return null; // Si termina el ciclo y no lo encuentra, devuelve null
    }
}