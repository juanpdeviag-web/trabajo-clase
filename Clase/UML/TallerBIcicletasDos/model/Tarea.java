package UML.TallerBIcicletasDos.model;

    public class Tarea {
        private String codigo;
        private String nombre;
        private double costo;

        public Tarea(String codigo, String nombre, double costo) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.costo = costo;
        }

        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public double getCosto() {
            return costo;
        }
        public void setCosto(double costo) {
            this.costo = costo;
        }

        public String getCodigo() {
            return codigo;
        }
        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }
    }
