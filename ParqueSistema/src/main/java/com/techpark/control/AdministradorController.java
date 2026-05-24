package com.techpark.control;

import com.techpark.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class AdministradorController {

    @FXML private TabPane tabPaneAdmin;

    @FXML private TableView<Empleado> tableEmpleados;
    @FXML private TableColumn<Empleado, String> colEmpDocumento;
    @FXML private TableColumn<Empleado, String> colEmpNombre;
    @FXML private TableColumn<Empleado, String> colEmpUsuario;
    @FXML private TableColumn<Empleado, String> colEmpZona;

    @FXML private TableView<Atraccion> tableAtraccionesAdmin;
    @FXML private TableColumn<Atraccion, String> colAdminId;
    @FXML private TableColumn<Atraccion, String> colAdminNombre;
    @FXML private TableColumn<Atraccion, TipoAtraccion> colAdminTipo;
    @FXML private TableColumn<Atraccion, EstadoAtraccion> colAdminEstado;
    @FXML private TableColumn<Atraccion, Integer> colAdminVisitantes;
    @FXML private TableColumn<Atraccion, String> colAdminMotivo;

    @FXML private Label lblIngresos;
    @FXML private Label lblTiempoPromedio;
    @FXML private Label lblTotalVisitantes;
    @FXML private ListView<String> listTopAtracciones;

    @FXML private Label lblAforoTotal;
    @FXML private Label lblVisitantesActivos;
    @FXML private Label lblOcupacion;

    private SistemaParque sistema;
    private ObservableList<Empleado> empleadosObservable;
    private ObservableList<Atraccion> atraccionesObservable;

    @FXML
    public void initialize() {
        sistema = DataSingleton.getInstancia();
        configurarTablas();
        cargarDatos();


    }

    private void configurarTablas() {
        colEmpDocumento.setCellValueFactory(new PropertyValueFactory<>("idDocumento"));
        colEmpNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmpUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colEmpZona.setCellValueFactory(cellData -> {
            Empleado emp = cellData.getValue();
            if (emp instanceof Operador) {
                Zona zona = ((Operador) emp).getZonaAsignada();
                return new javafx.beans.property.SimpleStringProperty(
                        zona != null ? zona.getNombre() : "Sin asignar");
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });

        colAdminId.setCellValueFactory(new PropertyValueFactory<>("idAtraccion"));
        colAdminNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAdminTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colAdminEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAdminVisitantes.setCellValueFactory(new PropertyValueFactory<>("visitantesAcumulados"));
        colAdminMotivo.setCellValueFactory(new PropertyValueFactory<>("motivoEstado"));
    }

    private void cargarDatos() {
        empleadosObservable = FXCollections.observableArrayList(sistema.getEmpleados());
        tableEmpleados.setItems(empleadosObservable);

        atraccionesObservable = FXCollections.observableArrayList(sistema.getAtraccionesGlobales());
        tableAtraccionesAdmin.setItems(atraccionesObservable);
    }

    @FXML
    private void onContratarOperador() {
        Dialog<Operador> dialog = new Dialog<>();
        dialog.setTitle("Contratar Operador");
        dialog.setHeaderText("Ingrese los datos del nuevo operador");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtDoc = new TextField();
        TextField txtNombre = new TextField();
        TextField txtEdad = new TextField();
        TextField txtEstatura = new TextField();
        TextField txtUsuario = new TextField();
        TextField txtPassword = new TextField();
        ComboBox<Zona> comboZona = new ComboBox<>(FXCollections.observableArrayList(sistema.getZonas()));

        grid.add(new Label("Documento:"), 0, 0);
        grid.add(txtDoc, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Edad:"), 0, 2);
        grid.add(txtEdad, 1, 2);
        grid.add(new Label("Estatura:"), 0, 3);
        grid.add(txtEstatura, 1, 3);
        grid.add(new Label("Usuario:"), 0, 4);
        grid.add(txtUsuario, 1, 4);
        grid.add(new Label("Contraseña:"), 0, 5);
        grid.add(txtPassword, 1, 5);
        grid.add(new Label("Zona:"), 0, 6);
        grid.add(comboZona, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    return new Operador(
                            txtDoc.getText(),
                            txtNombre.getText(),
                            Integer.parseInt(txtEdad.getText()),
                            Double.parseDouble(txtEstatura.getText()),
                            txtUsuario.getText(),
                            txtPassword.getText(),
                            comboZona.getValue()
                    );
                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Operador> resultado = dialog.showAndWait();
        resultado.ifPresent(operador -> {
            sistema.registrarEmpleado(operador);
            if (operador.getZonaAsignada() != null) {
                operador.getZonaAsignada().agregarOperador(operador);
            }
            cargarDatos();
            mostrarAlerta("Éxito", "Operador contratado correctamente", Alert.AlertType.INFORMATION);
        });
    }

    @FXML
    private void onReasignarZona() {
        Empleado seleccionado = tableEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null || !(seleccionado instanceof Operador)) {
            mostrarAlerta("Error", "Debe seleccionar un operador", Alert.AlertType.WARNING);
            return;
        }

        Operador operador = (Operador) seleccionado;
        ChoiceDialog<Zona> dialog = new ChoiceDialog<>(operador.getZonaAsignada(), sistema.getZonas());
        dialog.setTitle("Reasignar Zona");
        dialog.setHeaderText("Seleccione la nueva zona para " + operador.getNombre());
        dialog.setContentText("Zona:");

        Optional<Zona> resultado = dialog.showAndWait();
        resultado.ifPresent(nuevaZona -> {
            if (operador.getZonaAsignada() != null) {
                operador.getZonaAsignada().removerOperador(operador);
            }
            operador.setZonaAsignada(nuevaZona);
            nuevaZona.agregarOperador(operador);
            cargarDatos();
            mostrarAlerta("Éxito", "Zona reasignada correctamente", Alert.AlertType.INFORMATION);
        });
    }

    @FXML
    private void onCrearAtraccion() {
        Dialog<Atraccion> dialog = new Dialog<>();
        dialog.setTitle("Crear Atracción");
        dialog.setHeaderText("Ingrese los datos de la nueva atracción");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtId = new TextField();
        TextField txtNombre = new TextField();
        ComboBox<TipoAtraccion> comboTipo = new ComboBox<>(
                FXCollections.observableArrayList(TipoAtraccion.values()));
        TextField txtCapacidad = new TextField();
        TextField txtAltura = new TextField();
        TextField txtEdad = new TextField();
        TextField txtCosto = new TextField();

        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Tipo:"), 0, 2);
        grid.add(comboTipo, 1, 2);
        grid.add(new Label("Capacidad:"), 0, 3);
        grid.add(txtCapacidad, 1, 3);
        grid.add(new Label("Altura Mínima:"), 0, 4);
        grid.add(txtAltura, 1, 4);
        grid.add(new Label("Edad Mínima:"), 0, 5);
        grid.add(txtEdad, 1, 5);
        grid.add(new Label("Costo Adicional:"), 0, 6);
        grid.add(txtCosto, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    return new Atraccion(
                            txtId.getText(),
                            txtNombre.getText(),
                            comboTipo.getValue(),
                            Integer.parseInt(txtCapacidad.getText()),
                            Double.parseDouble(txtAltura.getText()),
                            Integer.parseInt(txtEdad.getText()),
                            Double.parseDouble(txtCosto.getText())
                    );
                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Atraccion> resultado = dialog.showAndWait();
        resultado.ifPresent(atraccion -> {
            sistema.agregarAtraccion(atraccion);
            cargarDatos();
            mostrarAlerta("Éxito", "Atracción creada correctamente", Alert.AlertType.INFORMATION);
        });
    }

    @FXML
    private void onAlertaClimatica() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("lluvia",
                List.of("despejado", "lluvia", "tormenta"));
        dialog.setTitle("Alerta Climática");
        dialog.setHeaderText("Seleccione el estado climático");
        dialog.setContentText("Clima:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(clima -> {
            sistema.activarAlertaClimatica(clima);
            cargarDatos();
            mostrarAlerta("Alerta Activada", "Se ha activado la alerta de: " + clima,
                    Alert.AlertType.INFORMATION);
        });
    }

    @FXML
    private void onActualizarReportes() {
        lblIngresos.setText(String.format("$%.2f", sistema.calcularIngresosDiarios()));
        lblTiempoPromedio.setText(String.format("%.1f min", sistema.calcularTiempoPromedioEspera()));
        lblTotalVisitantes.setText(String.valueOf(sistema.getVisitantes().size()));

        // NUEVOS REPORTES DE AFORO
        lblAforoTotal.setText(String.valueOf(sistema.calcularAforoTotal()));
        lblVisitantesActivos.setText(String.valueOf(sistema.calcularVisitantesActivos()));
        lblOcupacion.setText(String.format("%.1f%%", sistema.calcularPorcentajeOcupacion()));

        ObservableList<String> topAtracciones = FXCollections.observableArrayList();
        sistema.obtenerAtraccionesMasVisitadas(5).forEach(a ->
                topAtracciones.add(String.format("%s - %d visitantes",
                        a.getNombre(), a.getVisitantesAcumulados())));
        listTopAtracciones.setItems(topAtracciones);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //Cerrar Sesion
    @FXML
    private void onCerrarSesionAdmin() {
        DataSingleton.cerrarSesion();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) tabPaneAdmin.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root, 1200, 800);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cerrar sesión", Alert.AlertType.ERROR);
        }
    }
}