package UML.NewMascota;
import javax.swing.JOptionPane;

public class Main {

        // Crear la clínica (objeto global para el menú)
        private static Clinica clinica = new Clinica("VIDA ANIMAL", 50000);

        public static void main(String[] args) {

            int opcion;

            do {
                opcion = mostrarMenu();

                if (opcion == 1) {
                    registrarPropietario();
                } else if (opcion == 2) {
                    registrarMascota();
                } else if (opcion == 3) {
                    consultarMascota();
                } else if (opcion == 4) {
                    actualizarMascota();
                } else if (opcion == 5) {
                    eliminarMascota();
                } else if (opcion == 6) {
                    calcularCosto();
                } else if (opcion == 7) {
                    listarTodo();
                } else if (opcion == 8) {
                    JOptionPane.showMessageDialog(null,
                            "¡Gracias por usar el sistema!\nClínica " + clinica.getNombre());
                } else {
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                }

            } while (opcion != 8);
        }

        //
        // Mostrar Menú Principal
        //
        private static int mostrarMenu() {
            String menu = "══════════════════════════\n";
            menu = menu + "  CLÍNICA VIDA ANIMAL\n";
            menu = menu + "══════════════════════════\n";
            menu = menu + "1. Registrar Propietario\n";
            menu = menu + "2. Registrar Mascota\n";
            menu = menu + "3. Consultar Mascota\n";
            menu = menu + "4. Actualizar Mascota\n";
            menu = menu + "5. Eliminar Mascota\n";
            menu = menu + "6. Calcular Costo Consulta\n";
            menu = menu + "7. Listar Registros\n";
            menu = menu + "8. Salir\n";
            menu = menu + "══════════════════════════\n";
            menu = menu + "Seleccione una opción:";

            String input = JOptionPane.showInputDialog(null, menu);

            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                return -1;
            }
        }

        //
        // Opción 1: Registrar Propietario
        //
        private static void registrarPropietario() {
            String id = JOptionPane.showInputDialog("ID del propietario:");
            String nombre = JOptionPane.showInputDialog("Nombre completo:");
            String telefono = JOptionPane.showInputDialog("Teléfono:");
            String direccion = JOptionPane.showInputDialog("Dirección:");

            Propietario propietario = new Propietario(id, nombre, telefono, direccion);

            boolean registrado = clinica.registrarPropietario(propietario);

            if (registrado) {
                JOptionPane.showMessageDialog(null,
                        "✅ Propietario registrado!\n\n" + propietario);
            } else {
                JOptionPane.showMessageDialog(null, "❌ No hay espacio disponible");
            }
        }

        //
        // Opción 2: Registrar Mascota
        //
        private static void registrarMascota() {
            // Primero mostrar propietarios disponibles
            JOptionPane.showMessageDialog(null, clinica.listarPropietarios());

            String idPropietario = JOptionPane.showInputDialog("ID del propietario:");
            Propietario propietario = clinica.consultarPropietario(idPropietario);

            if (propietario == null) {
                JOptionPane.showMessageDialog(null,
                        "❌ Propietario no encontrado.\nRegistre primero al propietario.");
                return;
            }

            String codigo = JOptionPane.showInputDialog("Código de la mascota:");
            String nombre = JOptionPane.showInputDialog("Nombre de la mascota:");
            String tipo = JOptionPane.showInputDialog("Tipo (Perro, Gato, Ave, etc.):");
            String raza = JOptionPane.showInputDialog("Raza:");
            String edadStr = JOptionPane.showInputDialog("Edad (años):");
            int edad = Integer.parseInt(edadStr);

            Mascota mascota = new Mascota(codigo, nombre, tipo, raza, edad, propietario);

            boolean registrado = clinica.registrarMascota(mascota);

            if (registrado) {
                JOptionPane.showMessageDialog(null,
                        "✅ Mascota registrada!\n\n" + mascota);
            } else {
                JOptionPane.showMessageDialog(null, "❌ No hay espacio disponible");
            }
        }

        //
        // Opción 3: Consultar Mascota
        //
        private static void consultarMascota() {
            String codigo = JOptionPane.showInputDialog("Código de la mascota:");

            Mascota mascota = clinica.consultarMascota(codigo);

            if (mascota != null) {
                JOptionPane.showMessageDialog(null, mascota);
            } else {
                JOptionPane.showMessageDialog(null,
                        "❌ Mascota no encontrada con código: " + codigo);
            }
        }

        //
        // Opción 4: Actualizar Mascota
        //
        private static void actualizarMascota() {
            String codigo = JOptionPane.showInputDialog("Código de la mascota a actualizar:");

            Mascota mascota = clinica.consultarMascota(codigo);

            if (mascota == null) {
                JOptionPane.showMessageDialog(null, "❌ Mascota no encontrada");
                return;
            }

            // Mostrar datos actuales
            JOptionPane.showMessageDialog(null, "Datos actuales:\n\n" + mascota);

            // Pedir nuevos datos
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", mascota.getNombre());
            String nuevoTipo = JOptionPane.showInputDialog("Nuevo tipo:", mascota.getEspecie());
            String nuevaRaza = JOptionPane.showInputDialog("Nueva raza:", mascota.getRaza());
            String nuevaEdadStr = JOptionPane.showInputDialog("Nueva edad:", mascota.getEdad());
            int nuevaEdad = Integer.parseInt(nuevaEdadStr);

            boolean actualizado = clinica.actualizarMascota(codigo, nuevoNombre,
                    nuevoTipo, nuevaRaza, nuevaEdad);

            if (actualizado) {
                JOptionPane.showMessageDialog(null,
                        "✅ Mascota actualizada!\n\n" + clinica.consultarMascota(codigo));
            }
        }

        //
        // Opción 5: Eliminar Mascota
        //
        private static void eliminarMascota() {
            String codigo = JOptionPane.showInputDialog("Código de la mascota a eliminar:");

            Mascota mascota = clinica.consultarMascota(codigo);

            if (mascota == null) {
                JOptionPane.showMessageDialog(null, "❌ Mascota no encontrada");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Eliminar a " + mascota.getNombre() + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = clinica.eliminarMascota(codigo);

                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "✅ Mascota eliminada");
                }
            }
        }

        //
        // Opción 6: Calcular Costo
        //
        private static void calcularCosto() {
            String codigo = JOptionPane.showInputDialog("Código de la mascota:");

            Mascota mascota = clinica.consultarMascota(codigo);

            if (mascota != null) {
                String factura = clinica.generarFactura(mascota);
                JOptionPane.showMessageDialog(null, factura);
            } else {
                JOptionPane.showMessageDialog(null, "❌ Mascota no encontrada");
            }
        }

        //
        // Opción 7: Listar Todo
        //
        private static void listarTodo() {
            String listaPropietarios = clinica.listarPropietarios();
            String listaMascotas = clinica.listarMascotas();

            JOptionPane.showMessageDialog(null, listaPropietarios);
            JOptionPane.showMessageDialog(null, listaMascotas);
        }

}
