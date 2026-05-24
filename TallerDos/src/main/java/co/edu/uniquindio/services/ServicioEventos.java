package co.edu.uniquindio.services;

import co.edu.uniquindio.enums.TipoEvento;
import co.edu.uniquindio.model.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//METODOS

    public class ServicioEventos {

        /**
         * 1. Obtiene una colección de atletas de un evento de natación de tipo competición.
         * @param evento El evento deportivo a analizar.
         * @return Una lista de atletas que cumplen con los criterios.
         */
        public List<Atleta> getAtletasNatacionCompeticion(EventoDeportivo evento) {
            if (!evento.getTipoEvento().equals(TipoEvento.COMPETICION) || !"Natación".equalsIgnoreCase(evento.getDisciplina())) {
                return List.of(); // Devuelve lista vacía si el evento no cumple los criterios
            }

            return evento.getParticipantes().stream()
                    .filter(p -> p instanceof Atleta)
                    .map(p -> (Atleta) p)
                    .collect(Collectors.toList());
        }

        /**
         * 2. Obtiene una lista de participantes por un rol determinado.
         * @param evento El evento del cual obtener los participantes.
         * @param rol La clase del rol deseado (ej. Atleta.class, Arbitro.class).
         * @return Una lista de participantes que coinciden con el rol.
         */
        public List<Participante> getParticipantesPorRol(EventoDeportivo evento, Class<? extends Participante> rol) {
            return evento.getParticipantes().stream()
                    .filter(rol::isInstance)
                    .collect(Collectors.toList());
        }

        /**
         * 3. Obtiene eventos deportivos por una ubicación específica.
         * @param todosLosEventos Lista de todos los eventos disponibles.
         * @param ubicacion La ubicación (país y ciudad) a buscar.
         * @return Una lista de eventos que se realizan en esa ubicación.
         */
        public List<EventoDeportivo> getEventosPorUbicacion(List<EventoDeportivo> todosLosEventos, co.edu.uniquindio.model.Ubicacion ubicacion) {
            return todosLosEventos.stream()
                    .filter(evento -> evento.getUbicacion().equals(ubicacion))
                    .collect(Collectors.toList());
        }

        /**
         * 4. Calcula el promedio de años de experiencia de los atletas en un evento.
         * @param evento El evento deportivo.
         * @return El promedio de experiencia, o 0.0 si no hay atletas.
         */
        public double calcularPromedioExperienciaAtletas(EventoDeportivo evento) {
            return evento.getParticipantes().stream()
                    .filter(p -> p instanceof Atleta)
                    .map(p -> (Atleta) p)
                    .mapToInt(Atleta::getExperiencia)
                    .average()
                    .orElse(0.0);
        }

        /**
         * 5. Genera una descripción textual completa de un evento deportivo.
         * @param evento El evento a describir.
         * @return Un String con la descripción detallada.
         */
        public String generarDescripcionCompleta(EventoDeportivo evento) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return String.format("Evento: %s (%s) - Disciplina: %s. Se llevará a cabo en %s, %s el %s.",
                    evento.getNombre(),
                    evento.getTipoEvento().toString().toLowerCase(),
                    evento.getDisciplina(),
                    evento.getUbicacion().ciudad(),
                    evento.getUbicacion().pais(),
                    evento.getFechaInicio().format(formatter));
        }

        /**
         * 6. Devuelve una lista de los equipos participantes en un evento deportivo.
         * @param evento El evento deportivo.
         * @return Una colección de los equipos registrados en el evento.
         */
        public List<Equipo> getEquiposParticipantes(EventoDeportivo evento) {
            return evento.getEquipos();
        }

        /**
         * 7. Verifica si un participante puede acceder a un evento.
         * La lógica se basa en:
         * - El evento debe estar disponible.
         * - El participante debe estar registrado en la lista de participantes del evento.
         * @param participante La persona que intenta acceder.
         * @param evento El evento al que se quiere acceder.
         * @return true si el participante puede acceder, false en caso contrario.
         */
        public boolean puedeAcceder(Participante participante, EventoDeportivo evento) {
            if (!evento.isDisponible()) {
                return false;
            }

            // Verifica si el participante está en la lista de participantes del evento.
            // Se usa equals(), por lo que las clases Participante deberían tener un equals/hashCode bien definido.
            return evento.getParticipantes().stream()
                    .anyMatch(p -> p.equals(participante));
        }

        /**
         * 8. Obtiene el listado completo de personas (participantes) involucradas en un evento.
         * @param evento El evento de interés.
         * @return Una lista con todos los participantes registrados en el evento.
         */
        public List<Participante> getListadoCompletoPersonas(EventoDeportivo evento) {
            return evento.getParticipantes();
        }
    }

