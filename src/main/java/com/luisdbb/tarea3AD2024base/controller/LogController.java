package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.log.LogOperacion;
import com.luisdbb.tarea3AD2024base.log.LogOperacionService;
import com.luisdbb.tarea3AD2024base.log.TipoOperacion;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class LogController implements Initializable {

	@FXML
	private ComboBox<String> comboUsuario;
	@FXML
	private ComboBox<TipoOperacion> comboTipo;
	@FXML
	private DatePicker fechaInicio;
	@FXML
	private DatePicker fechaFin;

	@FXML
	private TableView<LogOperacion> tablaLogs;
	@FXML
	private TableColumn<LogOperacion, String> colUsuario;
	@FXML
	private TableColumn<LogOperacion, TipoOperacion> colTipo;
	@FXML
	private TableColumn<LogOperacion, String> colResumen;
	@FXML
	private TableColumn<LogOperacion, String> colFecha;

	@Autowired
	private LogOperacionService logService;

	private StageManager stageManager;

	public void setStageManager(StageManager stageManager) {
		this.stageManager = stageManager;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoOperacion"));
		colResumen.setCellValueFactory(new PropertyValueFactory<>("resumen"));

		cargarUsuarios();
		comboTipo.setItems(FXCollections.observableArrayList(TipoOperacion.values()));

		cargarTodos();
	}

	private void cargarUsuarios() {
		List<LogOperacion> logs = logService.buscarTodos();
		List<String> usuarios = logs.stream().map(LogOperacion::getUsuario).distinct().sorted()
				.collect(Collectors.toList());

		comboUsuario.setItems(FXCollections.observableArrayList(usuarios));
	}

	private void cargarTodos() {
		tablaLogs.setItems(FXCollections.observableArrayList(logService.buscarTodos()));
	}

	@FXML
	private void buscar() {

		List<LogOperacion> logs = logService.buscarTodos();

		// FILTRO USUARIO
		if (comboUsuario.getValue() != null) {
			logs = logs.stream().filter(l -> l.getUsuario().equals(comboUsuario.getValue()))
					.collect(Collectors.toList());
		}

		// FILTRO TIPO
		if (comboTipo.getValue() != null) {
			logs = logs.stream().filter(l -> l.getTipoOperacion().equals(comboTipo.getValue().name()))
					.collect(Collectors.toList());
		}

		// FILTRO FECHAS
		LocalDate ini = fechaInicio.getValue();
		LocalDate fin = fechaFin.getValue();

		if (fechaInicio.getValue() != null) {
			logs = logs.stream().filter(l -> {
				LocalDate fechaLog = LocalDate.parse(l.getFechaHora().substring(0, 10));
				return !fechaLog.isBefore(fechaInicio.getValue());
			}).collect(Collectors.toList());
		}

		if (fechaFin.getValue() != null) {
			logs = logs.stream().filter(l -> {
				LocalDate fechaLog = LocalDate.parse(l.getFechaHora().substring(0, 10));
				return !fechaLog.isAfter(fechaFin.getValue());
			}).collect(Collectors.toList());
		}

		tablaLogs.setItems(FXCollections.observableArrayList(logs));
	}

}
