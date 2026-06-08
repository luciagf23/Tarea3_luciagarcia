package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.modelo.Numero;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
	private TableColumn<Numero, String> colArtistas;

	@FXML
	private TableColumn<Numero, Double> colDuracion;

	private Espectaculo espectaculo;

	public DetalleEspectaculoController() {

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
		colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
		colArtistas.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getArtistas().stream().map(a -> a.getNombre()).collect(Collectors.joining(", "))));

	}

	private void cargarNumeros() {
		List<Numero> numerosOrdenados = espectaculo.getNumeros().stream()
				.sorted(Comparator.comparingInt(Numero::getOrden)).toList();
		tablaNumeros.setItems(FXCollections.observableArrayList(espectaculo.getNumeros()));
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
