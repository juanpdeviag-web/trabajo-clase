package UML.NewMascota;

public class Clinica {

        // Atributos
        private String nombre;
        private Mascota[] mascotas;
        private Propietario[] propietarios;
        private int cantidadMascotas;
        private int cantidadPropietarios;
        private double valorBaseConsulta;

        // Constructor
        public Clinica(String nombre, double valorBaseConsulta) {
            this.nombre              = nombre;
            this.valorBaseConsulta   = valorBaseConsulta;
            this.mascotas            = new Mascota[100];
            this.propietarios        = new Propietario[100];
            this.cantidadMascotas    = 0;
            this.cantidadPropietarios = 0;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Registrar Propietario
        // ══════════════════════════════════════════════════════════
        public boolean registrarPropietario(Propietario propietario) {
            if (cantidadPropietarios < 100) {
                propietarios[cantidadPropietarios] = propietario;
                cantidadPropietarios++;
                return true;
            }
            return false;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Registrar Mascota
        // ══════════════════════════════════════════════════════════
        public boolean registrarMascota(Mascota mascota) {
            if (cantidadMascotas < 100) {
                mascotas[cantidadMascotas] = mascota;
                cantidadMascotas++;
                return true;
            }
            return false;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Consultar Mascota por código
        // ══════════════════════════════════════════════════════════
        public Mascota consultarMascota(String codigo) {
            for (int i = 0; i < cantidadMascotas; i++) {
                if (mascotas[i].getId().equalsIgnoreCase(codigo)) {
                    return mascotas[i];
                }
            }
            return null;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Consultar Propietario por ID
        // ══════════════════════════════════════════════════════════
        public Propietario consultarPropietario(String id) {
            for (int i = 0; i < cantidadPropietarios; i++) {
                if (propietarios[i].getCedula().equalsIgnoreCase(id)) {
                    return propietarios[i];
                }
            }
            return null;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Actualizar Mascota
        // ══════════════════════════════════════════════════════════
        public boolean actualizarMascota(String codigo, String nuevoNombre,
                                         String nuevoTipo, String nuevaRaza,
                                         int nuevaEdad) {

            Mascota mascota = consultarMascota(codigo);

            if (mascota != null) {
                mascota.setNombre(nuevoNombre);
                mascota.setEspecie(nuevoTipo);
                mascota.setRaza(nuevaRaza);
                mascota.setEdad(nuevaEdad);
                return true;
            }
            return false;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Eliminar Mascota
        // ══════════════════════════════════════════════════════════
        public boolean eliminarMascota(String codigo) {
            for (int i = 0; i < cantidadMascotas; i++) {
                if (mascotas[i].getId().equalsIgnoreCase(codigo)) {
                    // Mover todos los elementos una posición hacia atrás
                    for (int j = i; j < cantidadMascotas - 1; j++) {
                        mascotas[j] = mascotas[j + 1];
                    }
                    mascotas[cantidadMascotas - 1] = null;
                    cantidadMascotas--;
                    return true;
                }
            }
            return false;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Calcular Costo de Consulta (DEPENDENCIA con Mascota)
        // ══════════════════════════════════════════════════════════
        public double calcularCostoConsulta(Mascota mascota) {

            double costo = valorBaseConsulta;

            // Incremento por tipo de animal
            String tipo = mascota.getEspecie().toLowerCase();

            if (tipo.equals("perro")) {
                costo = costo + 10000;
            } else if (tipo.equals("gato")) {
                costo = costo + 8000;
            } else if (tipo.equals("ave")) {
                costo = costo + 5000;
            } else {
                costo = costo + 15000;  // Exóticos
            }

            // Incremento por edad
            int edad = mascota.getEdad();

            if (edad >= 10) {
                costo = costo + 20000;
            } else if (edad >= 5) {
                costo = costo + 10000;
            }

            return costo;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Mostrar Factura
        // ══════════════════════════════════════════════════════════
        public String generarFactura(Mascota mascota) {

            String factura = "══════════════════════════\n";
            factura = factura + "  CLINICA " + nombre + "\n";
            factura = factura + "══════════════════════════\n";
            factura = factura + "Paciente: " + mascota.getNombre() + "\n";
            factura = factura + "Tipo: " + mascota.getEspecie() + "\n";
            factura = factura + "Edad: " + mascota.getEdad() + " años\n";
            factura = factura + "══════════════════════════\n";
            factura = factura + "Valor base: $" + valorBaseConsulta + "\n";

            // Mostrar cargo por especie
            String tipo = mascota.getEspecie().toLowerCase();
            if (tipo.equals("perro")) {
                factura = factura + "Cargo especie: $10000\n";
            } else if (tipo.equals("gato")) {
                factura = factura + "Cargo especie: $8000\n";
            } else if (tipo.equals("ave")) {
                factura = factura + "Cargo especie: $5000\n";
            } else {
                factura = factura + "Cargo especie: $15000\n";
            }

            // Mostrar cargo por edad
            int edad = mascota.getEdad();
            if (edad >= 10) {
                factura = factura + "Cargo edad: $20000\n";
            } else if (edad >= 5) {
                factura = factura + "Cargo edad: $10000\n";
            } else {
                factura = factura + "Cargo edad: $0\n";
            }

            factura = factura + "══════════════════════════\n";
            factura = factura + "TOTAL: $" + calcularCostoConsulta(mascota) + "\n";
            factura = factura + "══════════════════════════";

            return factura;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Listar todas las mascotas
        // ══════════════════════════════════════════════════════════
        public String listarMascotas() {
            if (cantidadMascotas == 0) {
                return "No hay mascotas registradas.";
            }

            String lista = "══════════════════════════\n";
            lista = lista + "  MASCOTAS REGISTRADAS\n";
            lista = lista + "══════════════════════════\n";

            for (int i = 0; i < cantidadMascotas; i++) {
                lista = lista + (i + 1) + ". ";
                lista = lista + mascotas[i].getNombre();
                lista = lista + " (" + mascotas[i].getId() + ")";
                lista = lista + " - " + mascotas[i].getEspecie() + "\n";
            }

            return lista;
        }

        // ══════════════════════════════════════════════════════════
        // MÉTODO: Listar todos los propietarios
        // ══════════════════════════════════════════════════════════
        public String listarPropietarios() {
            if (cantidadPropietarios == 0) {
                return "No hay propietarios registrados.";
            }

            String lista = "══════════════════════════\n";
            lista = lista + "  PROPIETARIOS REGISTRADOS\n";
            lista = lista + "══════════════════════════\n";

            for (int i = 0; i < cantidadPropietarios; i++) {
                lista = lista + (i + 1) + ". ";
                lista = lista + propietarios[i].getNombre();
                lista = lista + " (ID: " + propietarios[i].getCedula() + ")\n";
            }

            return lista;
        }

        // Getters
        public String getNombre() {
            return nombre;
        }

        public double getValorBaseConsulta() {
            return valorBaseConsulta;
        }

        public int getCantidadMascotas() {
            return cantidadMascotas;
        }

        public int getCantidadPropietarios() {
            return cantidadPropietarios;
        }

        // Setters
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setValorBaseConsulta(double valorBaseConsulta) {
            this.valorBaseConsulta = valorBaseConsulta;
        }

}
