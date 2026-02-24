package UML.Macotas;

import java.util.ArrayList;

public class SistemaClinica {
    private ArrayList<Mascotas> mascotas;

    //Constructor
    public SistemaClinica(){
        mascotas = new ArrayList<>();
    }

    //Registrar Mascota
    public void registrarMascota(Mascotas mascota){
        mascotas.add(mascota);
    }

    // Buscar mascota por ID
    public Mascotas buscarPorId(String id) {
        for (Mascotas m : mascotas) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    // Listar todas las mascotas
    public String listarTodas() {
        if (mascotas.isEmpty()) {
            return "No hay mascotas registradas.";
        }

        StringBuilder lista = new StringBuilder();
        lista.append("═══════════════════════════════════════\n");
        lista.append("    MASCOTAS REGISTRADAS\n");
        lista.append("═══════════════════════════════════════\n\n");

        for (int i = 0; i < mascotas.size(); i++) {
            Mascotas m = mascotas.get(i);
            lista.append((i + 1)).append(". ");
            lista.append(m.getNombre()).append(" - ");
            lista.append(m.getEspecie()).append(" (ID: ");
            lista.append(m.getId()).append(")\n");
        }

        return lista.toString();
    }

    // Obtener cantidad de mascotas
    public int getCantidadMascotas() {
        return mascotas.size();
    }

}
