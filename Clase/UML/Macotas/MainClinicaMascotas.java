package UML.Macotas;

import javax.swing.JOptionPane;

public class MainClinicaMascotas {
    private static SistemaClinica sistema = new SistemaClinica();

    public static void main(String[] args) {
        int opcion;

        do {
            opcion = mostrarMenu();

            switch (opcion) {
                case 1:
                    registrarMascota();
                    break;
                case 2:
                    consultarMascota();
                    break;
                case 3:
                    listarMascotas();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null,
                            "¡Gracias por usar el Sistema de Clínica Veterinaria!\n" +
                                    "Dr. Martínez",
                            "Hasta pronto",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null,
                            "Opción no válida",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
            }

        } while (opcion != 4);
    }

    // Mostrar menú principal
    private static int mostrarMenu() {
        String menu = """
               
                            CLÍNICA VETERINARIA
                               Dr. Martínez
               
                  1. 📝 Registrar Mascota
                  2. 🔍 Consultar Mascota
                  3. 📋 Listar Todas las Mascotas
                  4. 🚪 Salir
                
                
                Seleccione una opción:
                """;

        String input = JOptionPane.showInputDialog(null, menu,
                "Sistema Clínica Veterinaria", JOptionPane.QUESTION_MESSAGE);

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Opción 1: Registrar mascota
    private static void registrarMascota() {
        try {
            String id = JOptionPane.showInputDialog(null,
                    "Ingrese el número de identificación (chip/código):",
                    "Registro - ID",
                    JOptionPane.QUESTION_MESSAGE);


            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Registro cancelado");
                return;
            }

            String nombre = JOptionPane.showInputDialog(null,
                    "Ingrese el nombre de la mascota:",
                    "Registro - Nombre",
                    JOptionPane.QUESTION_MESSAGE);

            String especie = JOptionPane.showInputDialog(null,
                    "Ingrese la especie (Perro, Gato, etc.):",
                    "Registro - Especie",
                    JOptionPane.QUESTION_MESSAGE);

            String raza = JOptionPane.showInputDialog(null,
                    "Ingrese la raza:",
                    "Registro - Raza",
                    JOptionPane.QUESTION_MESSAGE);

            String edadStr = JOptionPane.showInputDialog(null,
                    "Ingrese la edad (años):",
                    "Registro - Edad",
                    JOptionPane.QUESTION_MESSAGE);
            int edad = Integer.parseInt(edadStr);



            String propietario = JOptionPane.showInputDialog(null,
                    "Ingrese el nombre del propietario:",
                    "Registro - Propietario",
                    JOptionPane.QUESTION_MESSAGE);

            String contacto = JOptionPane.showInputDialog(null,
                    "Ingrese el número de contacto:",
                    "Registro - Contacto",
                    JOptionPane.QUESTION_MESSAGE);

            // Crear y registrar mascota
            Mascotas mascota = new Mascotas(id, nombre, especie, raza, edad, propietario, contacto);
            sistema.registrarMascota(mascota);

            JOptionPane.showMessageDialog(null,
                    "✅ Mascota registrada exitosamente!\n\n" + mascota,
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Error: Debe ingresar un número válido para la edad",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al registrar la mascota",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Opción 2: Consultar mascota
    private static void consultarMascota() {
        String id = JOptionPane.showInputDialog(null,
                "Ingrese el número de identificación de la mascota:",
                "Consulta de Mascota",
                JOptionPane.QUESTION_MESSAGE);

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        Mascotas mascota = sistema.buscarPorId(id);

        if (mascota != null) {
            JOptionPane.showMessageDialog(null,
                    mascota,
                    "Información de la Mascota",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "❌ No se encontró ninguna mascota con el ID: " + id,
                    "No encontrado",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Opción 3: Listar todas las mascotas
    private static void listarMascotas() {
        String lista = sistema.listarTodas();
        JOptionPane.showMessageDialog(null,
                lista,
                "Listado de Mascotas",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
