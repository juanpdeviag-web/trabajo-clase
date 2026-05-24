package com.techpark.control;

import com.techpark.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisitanteController {

    @FXML private Circle avatarCircle;
    @FXML private Label lblNombreVisitante;
    @FXML private Label lblSaldoVisitante;
    @FXML private TabPane tabPaneVisitante;

    @FXML private TableView<Atraccion> tableAtracciones;
    @FXML private TableColumn<Atraccion, String> colNombre;
    @FXML private TableColumn<Atraccion, TipoAtraccion> colTipo;
    @FXML private TableColumn<Atraccion, EstadoAtraccion> colEstado;
    @FXML private TableColumn<Atraccion, Integer> colEspera;
    @FXML private TableColumn<Atraccion, Double> colCosto;

    @FXML private TableView<Atraccion> tableFavoritas;
    @FXML private TableColumn<Atraccion, String> colFavNombre;
    @FXML private TableColumn<Atraccion, TipoAtraccion> colFavTipo;
    @FXML private TableColumn<Atraccion, EstadoAtraccion> colFavEstado;
    @FXML private TableColumn<Atraccion, Integer> colFavVisitas;

    private ObservableList<Atraccion> favoritasObservable;

    @FXML private Label lblTicketActual;
    @FXML private TextField txtPrecioBase;
    @FXML private TextField txtDescuento;
    @FXML private TextField txtRecargo;

    @FXML private TableView<RegistroVisita> tableHistorial;
    @FXML private TableColumn<RegistroVisita, String> colHistAtraccion;
    @FXML private TableColumn<RegistroVisita, String> colHistFecha;
    @FXML private TableColumn<RegistroVisita, Double> colHistGasto;
    @FXML private TableColumn<RegistroVisita, Integer> colHistEspera;

    @FXML private ListView<String> listNotificaciones;

    @FXML private ListView<String> listZonas;

    private SistemaParque sistema;
    private Visitante visitante;
    private ObservableList<Atraccion> atraccionesObservable;
    private ObservableList<RegistroVisita> historialObservable;

    @FXML
    public void initialize() {
        sistema = DataSingleton.getInstancia();
        visitante = DataSingleton.getVisitanteActivo();

        if (visitante != null) {
            cargarDatosVisitante();
            configurarTablas();
            cargarAtracciones();
            cargarHistorial();
            cargarNotificaciones();
            cargarMapa();
            cargarRankings();
            cargarFavoritas();
        }
    }

    //Mapa
    private void cargarMapa() {
        ObservableList<String> zonas = FXCollections.observableArrayList();
        sistema.getZonas().forEach(z -> {
            int cantidadAtracciones = z.getAtracciones().size();
            int atraccionesActivas = (int) z.getAtracciones().stream()
                    .filter(a -> a.getEstado() == EstadoAtraccion.ACTIVA)
                    .count();

            zonas.add(String.format("%s - Aforo: %d | Atracciones: %d (%d activas)",
                    z.getNombre(), z.getAforoMaximo(), cantidadAtracciones, atraccionesActivas));
        });
        listZonas.setItems(zonas);
    }

    @FXML
    private void onVerAtraccionesZona() {
        String seleccion = listZonas.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarAlerta("Error", "Debe seleccionar una zona", Alert.AlertType.WARNING);
            return;
        }

        String nombreZona = seleccion.split(" - ")[0];
        Zona zona = sistema.getZonas().stream()
                .filter(z -> z.getNombre().equals(nombreZona))
                .findFirst()
                .orElse(null);

        if (zona != null) {
            StringBuilder mensaje = new StringBuilder("Atracciones en " + zona.getNombre() + ":\n\n");
            zona.getAtracciones().forEach(a ->
                    mensaje.append(String.format("• %s [%s] - Estado: %s\n",
                            a.getNombre(), a.getTipo(), a.getEstado()))
            );

            mostrarAlerta("Atracciones de la Zona", mensaje.toString(), Alert.AlertType.INFORMATION);
        }
    }

    private void cargarDatosVisitante() {
        lblNombreVisitante.setText(visitante.getNombre());
        actualizarSaldo();

        String rutaFoto = visitante.getRutaFotografia();
        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            try {
                Image imagen = new Image(rutaFoto, false);
                avatarCircle.setFill(new ImagePattern(imagen));
            } catch (Exception e) {
                avatarCircle.setFill(Color.web("#1a73e8"));
            }
        } else {
            avatarCircle.setFill(Color.web("#1a73e8"));
        }

        actualizarTicketActual();
    }

    private void actualizarSaldo() {
        lblSaldoVisitante.setText(String.format("Saldo: $%.2f", visitante.getSaldoVirtual()));
    }

    private void actualizarTicketActual() {
        Ticket ticket = visitante.getTicket();
        if (ticket != null) {
            lblTicketActual.setText(String.format("Ticket Actual: %s - Precio: $%.2f",
                    ticket.getTipo(), ticket.calcularPrecioFinal()));
        } else {
            lblTicketActual.setText("Ticket Actual: Ninguno");
        }
    }

    private void configurarTablas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEspera.setCellValueFactory(new PropertyValueFactory<>("tiempoEsperaMinutos"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costoAdicional"));

        colHistAtraccion.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getAtraccion().getNombre()));
        colHistFecha.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getFechaHora().format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        colHistGasto.setCellValueFactory(new PropertyValueFactory<>("gastoAdicional"));
        colHistEspera.setCellValueFactory(new PropertyValueFactory<>("tiempoEsperaMinutos"));
        colFavNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFavTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colFavEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFavVisitas.setCellValueFactory(cellData -> {
            Atraccion atraccion = cellData.getValue();
            long visitas = visitante.getHistorialVisitas().stream()
                    .filter(r -> r.getAtraccion().equals(atraccion))
                    .count();
            return new javafx.beans.property.SimpleObjectProperty<>((int) visitas);
        });
    }

    //Favoritas
    private void cargarFavoritas() {
        favoritasObservable = FXCollections.observableArrayList(visitante.getAtraccionesFavoritas());
        tableFavoritas.setItems(favoritasObservable);
    }

    @FXML
    private void onAgregarFavorita() {
        Atraccion seleccionada = tableAtracciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una atracción", Alert.AlertType.WARNING);
            return;
        }

        if (visitante.esFavorita(seleccionada)) {
            mostrarAlerta("Info", "Esta atracción ya está en tus favoritas", Alert.AlertType.INFORMATION);
            return;
        }

        visitante.agregarFavorita(seleccionada);
        cargarFavoritas();
        mostrarAlerta("Éxito", seleccionada.getNombre() + " agregada a favoritas",
                Alert.AlertType.INFORMATION);
    }

    @FXML
    private void onQuitarFavorita() {
        Atraccion seleccionada = tableFavoritas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una atracción de la lista de favoritas",
                    Alert.AlertType.WARNING);
            return;
        }

        visitante.removerFavorita(seleccionada);
        cargarFavoritas();
        mostrarAlerta("Éxito", seleccionada.getNombre() + " eliminada de favoritas",
                Alert.AlertType.INFORMATION);
    }


    private void cargarAtracciones() {
        atraccionesObservable = FXCollections.observableArrayList(sistema.getAtraccionesGlobales());
        tableAtracciones.setItems(atraccionesObservable);
    }

    private void cargarHistorial() {
        historialObservable = FXCollections.observableArrayList(visitante.getHistorialVisitas());
        tableHistorial.setItems(historialObservable);
    }

    private void cargarNotificaciones() {
        ObservableList<String> notificaciones = FXCollections.observableArrayList();
        visitante.getBuzonNotificaciones().forEach(n -> notificaciones.add(n.toString()));
        listNotificaciones.setItems(notificaciones);
    }

    @FXML
    private void onRecargarSaldo() {
        TextInputDialog dialog = new TextInputDialog("50000");
        dialog.setTitle("Recargar Saldo");
        dialog.setHeaderText("Ingrese el monto a recargar");
        dialog.setContentText("Monto:");

        dialog.showAndWait().ifPresent(monto -> {
            try {
                double cantidad = Double.parseDouble(monto);
                visitante.recargarSaldo(cantidad);
                actualizarSaldo();
                mostrarAlerta("Éxito", String.format("Saldo recargado: $%.2f", cantidad),
                        Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Monto inválido", Alert.AlertType.ERROR);
            }
        });
    }

    @FXML
    private void onCerrarSesion() {
        DataSingleton.cerrarSesion();

        // Recargar la aplicación completa
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) lblNombreVisitante.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root, 1200, 800);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onIngresarAtraccion() {
        Atraccion seleccionada = tableAtracciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una atracción", Alert.AlertType.WARNING);
            return;
        }

        String resultado = sistema.validarYRegistrarAcceso(visitante, seleccionada);

        if (resultado.contains("concedido")) {
            mostrarAlerta("Éxito", resultado, Alert.AlertType.INFORMATION);
            actualizarSaldo();
            actualizarTicketActual();
            cargarAtracciones();
            cargarHistorial();
            cargarRankings();
        } else {
            mostrarAlerta("Acceso Denegado", resultado, Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onComprarGeneral() {
        try {
            double precio = Double.parseDouble(txtPrecioBase.getText());
            String id = "TG-" + System.currentTimeMillis();
            Ticket ticket = new TicketGeneral(id, precio);

            if (visitante.deducirSaldo(ticket.calcularPrecioFinal())) {
                visitante.setTicket(ticket);
                sistema.venderTicket(ticket);
                actualizarSaldo();
                actualizarTicketActual();
                mostrarAlerta("Éxito", "Ticket General adquirido", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Saldo insuficiente", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Precio inválido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onComprarFamiliar() {
        try {
            double precio = Double.parseDouble(txtPrecioBase.getText());
            double descuento = Double.parseDouble(txtDescuento.getText());
            String id = "TF-" + System.currentTimeMillis();
            Ticket ticket = new TicketFamiliar(id, precio, descuento);

            if (visitante.deducirSaldo(ticket.calcularPrecioFinal())) {
                visitante.setTicket(ticket);
                sistema.venderTicket(ticket);
                actualizarSaldo();
                actualizarTicketActual();
                mostrarAlerta("Éxito", "Ticket Familiar adquirido", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Saldo insuficiente", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Datos inválidos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onComprarFastPass() {
        try {
            double precio = Double.parseDouble(txtPrecioBase.getText());
            double recargo = Double.parseDouble(txtRecargo.getText());
            String id = "TFP-" + System.currentTimeMillis();
            Ticket ticket = new TicketFastPass(id, precio, recargo);

            if (visitante.deducirSaldo(ticket.calcularPrecioFinal())) {
                visitante.setTicket(ticket);
                sistema.venderTicket(ticket);
                actualizarSaldo();
                actualizarTicketActual();
                mostrarAlerta("Éxito", "Ticket FastPass adquirido", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Saldo insuficiente", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Datos inválidos", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML private ListView<String> listTopAtraccionesVisitante;

    private void cargarRankings() {
        ObservableList<String> rankings = FXCollections.observableArrayList();
        List<Atraccion> top10 = sistema.obtenerAtraccionesMasVisitadas(10);

        for (int i = 0; i < top10.size(); i++) {
            Atraccion a = top10.get(i);
            rankings.add(String.format("%d. %s - %d visitantes [%s]",
                    (i + 1), a.getNombre(), a.getVisitantesAcumulados(), a.getTipo()));
        }

        listTopAtraccionesVisitante.setItems(rankings);
    }

    @FXML
    private void onActualizarRankings() {
        cargarRankings();
        mostrarAlerta("Rankings", "Rankings actualizados correctamente", Alert.AlertType.INFORMATION);
    }

}