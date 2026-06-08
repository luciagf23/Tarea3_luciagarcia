package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.services.EspectaculoService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class InvitadoController implements Initializable {

	@FXML
	private TableView<Espectaculo> tablaEspectaculos;
	@FXML
	private TableColumn<Espectaculo, Long> colId;
	@FXML
	private TableColumn<Espectaculo, String> colNombre;
	@FXML
	private TableColumn<Espectaculo, LocalDate> colFechaInicio;
	@FXML
	private TableColumn<Espectaculo, LocalDate> colFechaFin;

	@Autowired
	private EspectaculoService espectaculoService;

	@Autowired
	private SesionService sesionService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
		colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));

		cargarEspectaculos();
	}

	private void cargarEspectaculos() {
		ObservableList<Espectaculo> lista = FXCollections.observableArrayList(espectaculoService.listarTodos());
		tablaEspectaculos.setItems(lista);
	}

	@FXML
	private void volverLogin(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}
}
