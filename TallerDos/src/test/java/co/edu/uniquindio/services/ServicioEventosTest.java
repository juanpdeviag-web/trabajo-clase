package co.edu.uniquindio.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ServicioEventosTest {

    private co.edu.uniquindio.services.ServicioEventos servicio;
    private co.edu.uniquindio.model.Atleta atleta1, atleta2, atleta3;
    private co.edu.uniquindio.model.Entrenador entrenador1;
    private co.edu.uniquindio.model.Arbitro arbitro1;
    private co.edu.uniquindio.model.EventoDeportivo eventoNatacion, eventoFutbol;
    private co.edu.uniquindio.model.Ubicacion ubicacionTokio, ubicacionParis;

    @BeforeEach
    void setUp() {
        servicio = new co.edu.uniquindio.services.ServicioEventos();

        // Ubicaciones
        ubicacionTokio = new co.edu.uniquindio.model.Ubicacion("Tokio", "Japón");
        ubicacionParis = new co.edu.uniquindio.model.Ubicacion("París", "Francia");

        // Participantes
        atleta1 = new co.edu.uniquindio.model.Atleta("Michael", "Phelps", LocalDate.of(1985, 6, 30), "USA", 38, "USA", 20);
        atleta2 = new co.edu.uniquindio.model.Atleta("Katie", "Ledecky", LocalDate.of(1997, 3, 17), "USA", 26, "USA", 10);
        atleta3 = new co.edu.uniquindio.model.Atleta("Lionel", "Messi", LocalDate.of(1987, 6, 24), "Argentina", 36, "Argentina", 25);
        entrenador1 = new co.edu.uniquindio.model.Entrenador("Bob", "Bowman", LocalDate.of(1964, 1, 1), "USA", 59, "USA", "Natación");
        arbitro1 = new co.edu.uniquindio.model.Arbitro("John", "Doe", LocalDate.of(1970, 5, 15), "Canadá", 53, "Canadá", "Internacional");

        // Eventos
        eventoNatacion = new co.edu.uniquindio.model.EventoDeportivo("Mundial de Natación", LocalDate.of(2024, 7, 20), "Natación", co.edu.uniquindio.enums.TipoEvento.COMPETICION, ubicacionTokio);
        eventoNatacion.agregarParticipante(atleta1);
        eventoNatacion.agregarParticipante(atleta2);
        eventoNatacion.agregarParticipante(entrenador1);
        eventoNatacion.agregarParticipante(arbitro1);

        eventoFutbol = new co.edu.uniquindio.model.EventoDeportivo("Copa del Mundo", LocalDate.of(2026, 6, 1), "Fútbol", co.edu.uniquindio.enums.TipoEvento.TORNEO, ubicacionParis);
        eventoFutbol.agregarParticipante(atleta3);
    }

    @Test
    void testGetAtletasNatacionCompeticion() {
        List<co.edu.uniquindio.model.Atleta> atletas = servicio.getAtletasNatacionCompeticion(eventoNatacion);
        assertEquals(2, atletas.size(), "Debería encontrar 2 atletas.");
        assertTrue(atletas.contains(atleta1) && atletas.contains(atleta2), "La lista debe contener a Phelps y Ledecky.");

        List<co.edu.uniquindio.model.Atleta> atletasFutbol = servicio.getAtletasNatacionCompeticion(eventoFutbol);
        assertTrue(atletasFutbol.isEmpty(), "No debería encontrar atletas para un evento que no es de natación/competición.");
    }

    @Test
    void testGetParticipantesPorRol() {
        List<co.edu.uniquindio.model.Participante> atletas = servicio.getParticipantesPorRol(eventoNatacion, co.edu.uniquindio.model.Atleta.class);
        assertEquals(2, atletas.size());
        assertTrue(atletas.stream().allMatch(p -> p instanceof co.edu.uniquindio.model.Atleta));

        List<co.edu.uniquindio.model.Participante> arbitros = servicio.getParticipantesPorRol(eventoNatacion, co.edu.uniquindio.model.Arbitro.class);
        assertEquals(1, arbitros.size());
        assertEquals(arbitro1, arbitros.get(0));
    }

    @Test
    void testGetEventosPorUbicacion() {
        List<co.edu.uniquindio.model.EventoDeportivo> todosLosEventos = List.of(eventoNatacion, eventoFutbol);
        List<co.edu.uniquindio.model.EventoDeportivo> eventosEnTokio = servicio.getEventosPorUbicacion(todosLosEventos, ubicacionTokio);
        assertEquals(1, eventosEnTokio.size());
        assertEquals(eventoNatacion, eventosEnTokio.get(0));

        List<co.edu.uniquindio.model.EventoDeportivo> eventosEnLondres = servicio.getEventosPorUbicacion(todosLosEventos, new co.edu.uniquindio.model.Ubicacion("Londres", "Reino Unido"));
        assertTrue(eventosEnLondres.isEmpty());
    }

    @Test
    void testCalcularPromedioExperienciaAtletas() {
        double promedio = servicio.calcularPromedioExperienciaAtletas(eventoNatacion);
        // (20 + 10) / 2 = 15
        assertEquals(15.0, promedio, "El promedio de experiencia de los atletas de natación debería ser 15.");

        double promedioFutbol = servicio.calcularPromedioExperienciaAtletas(eventoFutbol);
        assertEquals(25.0, promedioFutbol, "El promedio para el evento de fútbol debería ser 25.");
    }

    @Test
    void testGenerarDescripcionCompleta() {
        String descripcion = servicio.generarDescripcionCompleta(eventoNatacion);
        String esperado = "Evento: Mundial de Natación (competicion) - Disciplina: Natación. Se llevará a cabo en Tokio, Japón el 20/07/2024.";
        assertEquals(esperado, descripcion);
    }

    @Test
    void testGetEquiposParticipantes() {
        co.edu.uniquindio.model.Equipo equipoUSA = new co.edu.uniquindio.model.Equipo("Team USA", "USA");
        equipoUSA.agregarAtleta(atleta1);
        eventoNatacion.agregarEquipo(equipoUSA);

        List<co.edu.uniquindio.model.Equipo> equipos = servicio.getEquiposParticipantes(eventoNatacion);
        assertEquals(1, equipos.size());
        assertEquals("Team USA", equipos.get(0).getNombre());
    }

    @Test
    void testPuedeAcceder() {
        assertTrue(servicio.puedeAcceder(atleta1, eventoNatacion), "Atleta1 está registrado y debería poder acceder.");
        assertFalse(servicio.puedeAcceder(atleta3, eventoNatacion), "Atleta3 no está registrado en el evento de natación.");

        eventoNatacion.setDisponible(false);
        assertFalse(servicio.puedeAcceder(atleta1, eventoNatacion), "El evento no está disponible, nadie debería acceder.");
    }

    @Test
    void testGetListadoCompletoPersonas() {
        List<co.edu.uniquindio.model.Participante> personas = servicio.getListadoCompletoPersonas(eventoNatacion);
        assertEquals(4, personas.size(), "Debe haber 4 personas en el evento de natación.");
        assertTrue(personas.contains(atleta1));
        assertTrue(personas.contains(atleta2));
        assertTrue(personas.contains(entrenador1));
        assertTrue(personas.contains(arbitro1));
    }
}
