package com.techpark;

import com.techpark.control.SistemaParque;
import com.techpark.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        inicializarDatosPrueba();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        primaryStage.setTitle("Tech-Park UQ - Sistema de Gestión del Parque");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private void inicializarDatosPrueba() {
        SistemaParque sistema = SistemaParque.getInstancia();

        Zona zonaAventura = new Zona("Z001", "Zona de Aventura", 500);
        Zona zonaFamiliar = new Zona("Z002", "Zona Familiar", 400);
        Zona zonaAcuatica = new Zona("Z003", "Zona Acuática", 350);

        sistema.agregarZona(zonaAventura);
        sistema.agregarZona(zonaFamiliar);
        sistema.agregarZona(zonaAcuatica);

        Atraccion montanaRusa = new Atraccion("ATR001", "Montaña Rusa Extrema",
                TipoAtraccion.MECANICA_ALTURA, 20, 1.40, 12, 5000);
        montanaRusa.setTiempoEsperaMinutos(25);

        Atraccion rapidos = new Atraccion("ATR002", "Rápidos Salvajes",
                TipoAtraccion.ACUATICA, 25, 1.20, 8, 3000);
        rapidos.setTiempoEsperaMinutos(15);

        Atraccion carrusel = new Atraccion("ATR003", "Carrusel Mágico",
                TipoAtraccion.NORMAL, 30, 1.00, 5, 0);
        carrusel.setTiempoEsperaMinutos(5);

        Atraccion ruedaFortuna = new Atraccion("ATR004", "Rueda de la Fortuna",
                TipoAtraccion.MECANICA_ALTURA, 40, 1.30, 10, 2000);
        ruedaFortuna.setTiempoEsperaMinutos(20);

        Atraccion toboganes = new Atraccion("ATR005", "Toboganes Gigantes",
                TipoAtraccion.ACUATICA, 15, 1.25, 9, 4000);
        toboganes.setTiempoEsperaMinutos(18);

        sistema.agregarAtraccion(montanaRusa);
        sistema.agregarAtraccion(rapidos);
        sistema.agregarAtraccion(carrusel);
        sistema.agregarAtraccion(ruedaFortuna);
        sistema.agregarAtraccion(toboganes);

        zonaAventura.agregarAtraccion(montanaRusa);
        zonaAventura.agregarAtraccion(ruedaFortuna);
        zonaFamiliar.agregarAtraccion(carrusel);
        zonaAcuatica.agregarAtraccion(rapidos);
        zonaAcuatica.agregarAtraccion(toboganes);

        Administrador admin = new Administrador("1001", "Carlos Administrador", 35, 1.75,
                "admin", "admin123");
        sistema.registrarEmpleado(admin);

        Operador operador1 = new Operador("2001", "María García", 28, 1.68,
                "maria.op", "pass123", zonaAventura);
        Operador operador2 = new Operador("2002", "Juan Pérez", 32, 1.80,
                "juan.op", "pass456", zonaAcuatica);

        sistema.registrarEmpleado(operador1);
        sistema.registrarEmpleado(operador2);

        zonaAventura.agregarOperador(operador1);
        zonaAcuatica.agregarOperador(operador2);

        Visitante visitante1 = new Visitante("V001", "Ana López", 25, 1.65, 150000,
                "");
        Visitante visitante2 = new Visitante("V002", "Pedro Martínez", 30, 1.75, 200000,
                "");
        Visitante visitante3 = new Visitante("V003", "Laura Rodríguez", 22, 1.60, 100000,
                "");

        sistema.registrarVisitante(visitante1);
        sistema.registrarVisitante(visitante2);
        sistema.registrarVisitante(visitante3);

        Ticket ticket1 = new TicketGeneral("TG001", 50000);
        visitante1.setTicket(ticket1);
        sistema.venderTicket(ticket1);

        Ticket ticket2 = new TicketFamiliar("TF001", 50000, 20);
        visitante2.setTicket(ticket2);
        sistema.venderTicket(ticket2);

        System.out.println("=== TECH-PARK UQ - SISTEMA INICIALIZADO ===");
        System.out.println("Zonas creadas: " + sistema.getZonas().size());
        System.out.println("Atracciones activas: " + sistema.getAtraccionesGlobales().size());
        System.out.println("Empleados registrados: " + sistema.getEmpleados().size());
        System.out.println("Visitantes registrados: " + sistema.getVisitantes().size());
        System.out.println("==========================================");
    }

    public static void main(String[] args) {
        launch(args);
    }
}