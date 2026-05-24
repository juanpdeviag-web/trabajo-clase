package com.techpark.control;

import com.techpark.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class MainController {

    @FXML private StackPane stackContenedor;
    @FXML private Label lblUsuarioActivo;
    @FXML private Button btnAccesoPersonal;

    @FXML private VBox panelLoginVisitante;
    @FXML private VBox panelRegistroVisitante;
    @FXML private VBox panelPrincipalVisitante;
    @FXML private VBox panelAdministrador;

    @FXML private TextField txtDocumentoVisitante;

    @FXML private TextField txtRegDocumento;
    @FXML private TextField txtRegNombre;
    @FXML private TextField txtRegEdad;
    @FXML private TextField txtRegEstatura;
    @FXML private TextField txtRegSaldo;
    @FXML private TextField txtRegFoto;

    private SistemaParque sistema;

    @FXML
    public void initialize() {
        sistema = SistemaParque.getInstancia();
        DataSingleton.setInstancia(sistema);
        mostrarVista(panelLoginVisitante);
    }

    @FXML
    private void onIngresarVisitante() {
        String documento = txtDocumentoVisitante.getText().trim();
        if (documento.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un documento", Alert.AlertType.ERROR);
            return;
        }

        Visitante visitante = sistema.buscarVisitantePorDocumento(documento);
        if (visitante == null) {
            mostrarAlerta("Error", "Visitante no encontrado. Debe registrarse primero.",
                    Alert.AlertType.WARNING);
            return;
        }

        DataSingleton.setVisitanteActivo(visitante);
        lblUsuarioActivo.setText("Visitante: " + visitante.getNombre());
        btnAccesoPersonal.setVisible(false);

        // Cargar vista de visitante dinámicamente
        cargarVistaVisitante();
    }

    @FXML
    private void onRegistrarVisitante() {
        mostrarVista(panelRegistroVisitante);
    }

    @FXML
    private void onGuardarRegistro() {
        try {
            String documento = txtRegDocumento.getText().trim();
            String nombre = txtRegNombre.getText().trim();
            int edad = Integer.parseInt(txtRegEdad.getText().trim());
            double estatura = Double.parseDouble(txtRegEstatura.getText().trim());
            double saldo = Double.parseDouble(txtRegSaldo.getText().trim());
            String foto = txtRegFoto.getText().trim();

            if (documento.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta("Error", "Documento y nombre son obligatorios", Alert.AlertType.ERROR);
                return;
            }

            Visitante nuevoVisitante = new Visitante(documento, nombre, edad, estatura, saldo, foto);
            sistema.registrarVisitante(nuevoVisitante);

            mostrarAlerta("Éxito", "Visitante registrado correctamente", Alert.AlertType.INFORMATION);
            limpiarFormularioRegistro();
            mostrarVista(panelLoginVisitante);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Verifique los datos numéricos ingresados", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelarRegistro() {
        limpiarFormularioRegistro();
        mostrarVista(panelLoginVisitante);
    }

    @FXML
    private void onAccesoPersonal() {
        TextInputDialog dialogUsuario = new TextInputDialog();
        dialogUsuario.setTitle("Acceso Personal");
        dialogUsuario.setHeaderText("Ingrese sus credenciales");
        dialogUsuario.setContentText("Usuario:");

        dialogUsuario.showAndWait().ifPresent(usuario -> {
            TextInputDialog dialogPassword = new TextInputDialog();
            dialogPassword.setTitle("Acceso Personal");
            dialogPassword.setHeaderText("Ingrese su contraseña");
            dialogPassword.setContentText("Contraseña:");

            dialogPassword.showAndWait().ifPresent(password -> {
                Empleado empleado = sistema.autenticarEmpleado(usuario, password);

                if (empleado == null) {
                    mostrarAlerta("Error", "Credenciales inválidas", Alert.AlertType.ERROR);
                    return;
                }

                if (empleado instanceof Administrador) {
                    DataSingleton.setAdministradorActivo((Administrador) empleado);
                    lblUsuarioActivo.setText("Admin: " + empleado.getNombre());
                    btnAccesoPersonal.setVisible(false);

                    // Cargar vista de administrador dinámicamente
                    cargarVistaAdministrador();
                } else {
                    mostrarAlerta("Info", "Acceso de Operadores en desarrollo",
                            Alert.AlertType.INFORMATION);
                }
            });
        });
    }

    private void cargarVistaVisitante() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VisitanteView.fxml"));
            Parent vistaVisitante = loader.load();

            panelPrincipalVisitante.getChildren().clear();
            panelPrincipalVisitante.getChildren().add(vistaVisitante);

            mostrarVista(panelPrincipalVisitante);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista del visitante: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void cargarVistaAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdministradorView.fxml"));
            Parent vistaAdmin = loader.load();

            panelAdministrador.getChildren().clear();
            panelAdministrador.getChildren().add(vistaAdmin);

            mostrarVista(panelAdministrador);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista del administrador: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void mostrarVista(VBox vista) {
        panelLoginVisitante.setVisible(false);
        panelLoginVisitante.setManaged(false);
        panelRegistroVisitante.setVisible(false);
        panelRegistroVisitante.setManaged(false);
        panelPrincipalVisitante.setVisible(false);
        panelPrincipalVisitante.setManaged(false);
        panelAdministrador.setVisible(false);
        panelAdministrador.setManaged(false);

        vista.setVisible(true);
        vista.setManaged(true);
    }

    private void limpiarFormularioRegistro() {
        txtRegDocumento.clear();
        txtRegNombre.clear();
        txtRegEdad.clear();
        txtRegEstatura.clear();
        txtRegSaldo.clear();
        txtRegFoto.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}