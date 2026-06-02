package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.modelo.Numero;
import com.luisdbb.tarea3AD2024base.services.NumeroService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DetalleEspectaculoController implements Initializable {

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblFechaInicio;

    @FXML
    private Label lblFechaFin;

    @FXML
    private Label lblCoordinador;

    @FXML
    private TableView<Numero> tablaNumeros;

    @FXML
    private TableColumn<Numero, Integer> colOrden;

    @FXML
    private TableColumn<Numero, String> colEspecialidad;

    @FXML
    private TableColumn<Numero, Double> colDuracion;

    @FXML
    private Button btnAgregarNumero;

    @FXML
    private Button btnEditarNumero;

    @FXML
    private Button btnEliminarNumero;

    private Espectaculo espectaculo;

    private final NumeroService numeroService;

    public DetalleEspectaculoController(NumeroService numeroService) {
        this.numeroService = numeroService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
    }

  
   
    public void setEspectaculo(Espectaculo espectaculo) {
        this.espectaculo = espectaculo;
        cargarDatosEspectaculo();
        cargarNumeros();
    }

   
    private void cargarDatosEspectaculo() {
        lblNombre.setText(espectaculo.getNombre());
        lblFechaInicio.setText(espectaculo.getFechaInicio().toString());
        lblFechaFin.setText(espectaculo.getFechaFin().toString());
        lblCoordinador.setText(espectaculo.getCoordinador().getNombre());
    }

    
    private void configurarTabla() {
        colOrden.setCellValueFactory(new PropertyValueFactory<>("orden"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
    }

 
    private void cargarNumeros() {
        tablaNumeros.setItems(
                FXCollections.observableArrayList(espectaculo.getNumeros())
        );
    }

  
    @FXML
    private void onAgregarNumero() {
        mostrarInfo("Funcionalidad pendiente (se implementa en el módulo de Números)");
    }

    
    @FXML
    private void onEditarNumero() {
        Numero seleccionado = tablaNumeros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Seleccione un número");
            return;
        }

        mostrarInfo("Funcionalidad pendiente (se implementa en el módulo de Números)");
    }

  
    @FXML
    private void onEliminarNumero() {
        Numero seleccionado = tablaNumeros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Seleccione un número");
            return;
        }

        mostrarInfo("Funcionalidad pendiente (se implementa en el módulo de Números)");
    }

   
    private void mostrarInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void mostrarError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Error");
        a.setContentText(msg);
        a.showAndWait();
    }
}
