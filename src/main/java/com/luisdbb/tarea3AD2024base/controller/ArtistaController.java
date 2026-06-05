package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Numero;
import com.luisdbb.tarea3AD2024base.services.NumeroService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;

@Controller
public class ArtistaController implements Initializable {

	@FXML
	private Label lblNombre;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblNacionalidad;
	@FXML
	private Label lblApodo;
	@FXML
	private Label lblEspecialidades;

	@FXML
	private TableView<Numero> tablaNumeros;
	@FXML
	private TableColumn<Numero, String> colEspectaculo;
	@FXML
	private TableColumn<Numero, String> colNumero;

	@Autowired
	private SesionService sesionService;

	@Autowired
	private NumeroService numeroService;

	private StageManager stageManager;

	public void setStageManager(StageManager stageManager) {
		this.stageManager = stageManager;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		colEspectaculo
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspectaculo().getId()
						+ " - " + cellData.getValue().getEspectaculo().getNombre()));
		colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getId() + " - " + cellData.getValue().getNombre()));

		cargarFicha();
	}

	private void cargarFicha() {
		Artista artista = (Artista) sesionService.getUsuarioActual().getPersona();

		lblNombre.setText(artista.getNombre());
		lblEmail.setText(artista.getEmail());
		lblNacionalidad.setText(artista.getNacionalidad());
		lblApodo.setText(artista.getApodo() != null ? artista.getApodo() : "-");
		lblEspecialidades
				.setText(artista.getEspecialidades().stream().map(Enum::name).collect(Collectors.joining(", ")));

		tablaNumeros.setItems(FXCollections.observableArrayList(numeroService.findByArtista(artista.getId())));
	}

	@FXML
	private void volver(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}
}