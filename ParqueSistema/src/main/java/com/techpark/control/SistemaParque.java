package com.techpark.control;

import com.techpark.model.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaParque {
    private static SistemaParque instancia;

    private List<Zona> zonas;
    private List<Atraccion> atraccionesGlobales;
    private List<Visitante> visitantes;
    private List<Empleado> empleados;
    private List<Ticket> ticketsVendidos;
    private String climaActual;

    private SistemaParque() {
        this.zonas = new ArrayList<>();
        this.atraccionesGlobales = new ArrayList<>();
        this.visitantes = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.ticketsVendidos = new ArrayList<>();
        this.climaActual = "despejado";
    }

    public static SistemaParque getInstancia() {
        if (instancia == null) {
            instancia = new SistemaParque();
        }
        return instancia;
    }

    /**
     * Valida y registra el acceso de un visitante a una atracción
     * Implementa todas las reglas de negocio del torniquete
     */
    public String validarYRegistrarAcceso(Visitante visitante, Atraccion atraccion) {
        // Validación 1: Estado de la atracción
        if (atraccion.getEstado() != EstadoAtraccion.ACTIVA) {
            return "Acceso denegado: La atracción está " + atraccion.getEstado() +
                    ". Motivo: " + atraccion.getMotivoEstado();
        }

        // Validación 2: Edad mínima
        if (visitante.getEdad() < atraccion.getEdadMinima()) {
            return "Acceso denegado: Edad mínima requerida: " + atraccion.getEdadMinima() + " años";
        }

        // Validación 3: Altura mínima
        if (visitante.getEstatura() < atraccion.getAlturaMinima()) {
            return String.format("Acceso denegado: Altura mínima requerida: %.2fm",
                    atraccion.getAlturaMinima());
        }

        // Validación 4: Ticket válido
        Ticket ticket = visitante.getTicket();
        if (ticket == null) {
            return "Acceso denegado: El visitante no posee un ticket válido";
        }

        // Validación 5: Saldo para costos adicionales (solo Ticket General)
        if (ticket.getTipo() == TipoTicket.GENERAL && atraccion.getCostoAdicional() > 0) {
            if (visitante.getSaldoVirtual() < atraccion.getCostoAdicional()) {
                return String.format("Acceso denegado: Saldo insuficiente. Requiere $%.2f adicionales",
                        atraccion.getCostoAdicional());
            }
            // Deducir el saldo
            visitante.deducirSaldo(atraccion.getCostoAdicional());
        }

        // Registrar el ingreso en la atracción (incrementa contador y valida mantenimiento)
        atraccion.registrarIngresoVisitante();

        // Crear registro de visita para el historial del visitante
        RegistroVisita registro = new RegistroVisita(
                atraccion,
                atraccion.getCostoAdicional(),
                atraccion.getTiempoEsperaMinutos()
        );
        visitante.agregarVisita(registro);

        return "Acceso concedido exitosamente a " + atraccion.getNombre();
    }

    /**
     * Activa alerta climática y cierra atracciones vulnerables
     * Envía notificaciones a todos los visitantes activos
     */
    public void activarAlertaClimatica(String tipoClima) {
        this.climaActual = tipoClima;

        List<Atraccion> atraccionesCerradas = new ArrayList<>();

        // Evaluar todas las atracciones
        for (Atraccion atraccion : atraccionesGlobales) {
            EstadoAtraccion estadoAnterior = atraccion.getEstado();
            atraccion.evaluarCierrePorClima(tipoClima);

            // Si cambió de ACTIVA a CERRADA, agregar a la lista
            if (estadoAnterior == EstadoAtraccion.ACTIVA &&
                    atraccion.getEstado() == EstadoAtraccion.CERRADA) {
                atraccionesCerradas.add(atraccion);
            }
        }

        // Si hay atracciones cerradas, notificar a todos los visitantes
        if (!atraccionesCerradas.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("ALERTA CLIMÁTICA: " + tipoClima.toUpperCase());
            mensaje.append(". Atracciones cerradas: ");
            for (int i = 0; i < atraccionesCerradas.size(); i++) {
                mensaje.append(atraccionesCerradas.get(i).getNombre());
                if (i < atraccionesCerradas.size() - 1) {
                    mensaje.append(", ");
                }
            }

            Notificacion notificacion = new Notificacion(mensaje.toString());

            // Enviar notificación a todos los visitantes
            for (Visitante visitante : visitantes) {
                visitante.recibirNotificacion(notificacion);
            }
        }
    }

    /**
     * Calcula los ingresos totales del día
     * Suma: precio de tickets vendidos + costos adicionales pagados en torniquetes
     */
    public double calcularIngresosDiarios() {
        // Ingresos por venta de tickets
        double ingresosPorTickets = ticketsVendidos.stream()
                .mapToDouble(Ticket::calcularPrecioFinal)
                .sum();

        // Ingresos por costos adicionales en atracciones
        double ingresosPorCostosAdicionales = visitantes.stream()
                .flatMap(v -> v.getHistorialVisitas().stream())
                .mapToDouble(RegistroVisita::getGastoAdicional)
                .sum();

        return ingresosPorTickets + ingresosPorCostosAdicionales;
    }

    /**
     * Obtiene las N atracciones más visitadas ordenadas descendentemente
     */
    public List<Atraccion> obtenerAtraccionesMasVisitadas(int cantidad) {
        return atraccionesGlobales.stream()
                .sorted(Comparator.comparingInt(Atraccion::getVisitantesAcumulados).reversed())
                .limit(cantidad)
                .collect(Collectors.toList());
    }

    /**
     * Calcula el tiempo promedio de espera de todas las atracciones activas
     */
    public double calcularTiempoPromedioEspera() {
        return atraccionesGlobales.stream()
                .filter(a -> a.getEstado() == EstadoAtraccion.ACTIVA)
                .mapToInt(Atraccion::getTiempoEsperaMinutos)
                .average()
                .orElse(0.0);
    }

    // ========== MÉTODOS DE GESTIÓN DE COLECCIONES ==========

    public void agregarZona(Zona zona) {
        if (zona != null && !zonas.contains(zona)) {
            zonas.add(zona);
        }
    }

    public void agregarAtraccion(Atraccion atraccion) {
        if (atraccion != null && !atraccionesGlobales.contains(atraccion)) {
            atraccionesGlobales.add(atraccion);
        }
    }

    public void registrarVisitante(Visitante visitante) {
        if (visitante != null && !visitantes.contains(visitante)) {
            visitantes.add(visitante);
        }
    }

    public void registrarEmpleado(Empleado empleado) {
        if (empleado != null && !empleados.contains(empleado)) {
            empleados.add(empleado);
        }
    }

    public void venderTicket(Ticket ticket) {
        if (ticket != null) {
            ticketsVendidos.add(ticket);
        }
    }

    // ========== MÉTODOS DE BÚSQUEDA ==========

    public Visitante buscarVisitantePorDocumento(String documento) {
        return visitantes.stream()
                .filter(v -> v.getIdDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    public Empleado autenticarEmpleado(String usuario, String contrasena) {
        return empleados.stream()
                .filter(e -> e.autenticar(usuario, contrasena))
                .findFirst()
                .orElse(null);
    }

    public Atraccion buscarAtraccionPorId(String idAtraccion) {
        return atraccionesGlobales.stream()
                .filter(a -> a.getIdAtraccion().equals(idAtraccion))
                .findFirst()
                .orElse(null);
    }

    // ========== GETTERS Y SETTERS ==========

    public List<Zona> getZonas() {
        return zonas;
    }

    public List<Atraccion> getAtraccionesGlobales() {
        return atraccionesGlobales;
    }

    public List<Visitante> getVisitantes() {
        return visitantes;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public List<Ticket> getTicketsVendidos() {
        return ticketsVendidos;
    }

    public String getClimaActual() {
        return climaActual;
    }

    public void setClimaActual(String climaActual) {
        this.climaActual = climaActual;
    }

    /**
     * Calcula el aforo total del parque sumando todos los aforos de las zonas
     */
    public int calcularAforoTotal() {
        return zonas.stream()
                .mapToInt(Zona::getAforoMaximo)
                .sum();
    }

    /**
     * Calcula la cantidad de visitantes actualmente en el parque
     * (visitantes que tienen ticket y han ingresado al menos una vez)
     */
    public int calcularVisitantesActivos() {
        return (int) visitantes.stream()
                .filter(v -> v.getTicket() != null && !v.getHistorialVisitas().isEmpty())
                .count();
    }

    /**
     * Calcula el porcentaje de ocupación del parque
     */
    public double calcularPorcentajeOcupacion() {
        int aforoTotal = calcularAforoTotal();
        if (aforoTotal == 0) return 0.0;
        return (calcularVisitantesActivos() * 100.0) / aforoTotal;
    }
}