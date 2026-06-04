package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Coordinacion;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.services.EspectaculoService;
import com.luisdbb.tarea3AD2024base.services.PersonaService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

@Controller
public class EspectaculoController implements Initializable {

	@FXML
	private TableView<Espectaculo> tablaEspectaculos;
	@FXML
	private TableColumn<Espectaculo, String> colNombre;
	@FXML
	private TableColumn<Espectaculo, LocalDate> colFechaInicio;
	@FXML
	private TableColumn<Espectaculo, LocalDate> colFechaFin;
	@FXML
	private TableColumn<Espectaculo, String> colCoordinador;

	@FXML
	private TextField txtNombre;
	@FXML
	private DatePicker dateInicio;
	@FXML
	private DatePicker dateFin;
	@FXML
	private ComboBox<Coordinacion> comboCoordinador;

	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnNuevo;
	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnNumeros;
	
	@FXML
	private Button btnDetalles;

	@Autowired
	private EspectaculoService espectaculoService;

	@Autowired
	private PersonaService personaService;

	private StageManager stageManager;

	private Espectaculo espectaculoEditando;

	@Autowired
	private SesionService sesionService;

	public EspectaculoController() {

	}

	public void setStageManager(StageManager stageManager) {
		this.stageManager = stageManager;
	}

	public void initialize(URL location, ResourceBundle resources) {

		configurarTabla();

		cargarCoordinadores();

		comboCoordinador.setCellFactory(new Callback<ListView<Coordinacion>, ListCell<Coordinacion>>() {

			@Override
			public ListCell<Coordinacion> call(ListView<Coordinacion> lv) {
				return new ListCell<Coordinacion>() {
					@Override
					protected void updateItem(Coordinacion item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty || item == null ? null : item.getNombre());
					}
				};
			}
		});

		comboCoordinador.setButtonCell(new ListCell<Coordinacion>() {
			@Override
			protected void updateItem(Coordinacion item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.getNombre());
			}
		});

		cargarTablaEspectaculos();

		tablaEspectaculos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
			if (newSel != null) {
				cargarEspectaculoEnFormulario(newSel);
			}
		});
	}

	private void configurarTabla() {
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
		colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
		colCoordinador.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
				() -> cellData.getValue().getCoordinador() != null ? cellData.getValue().getCoordinador().getNombre()
						: ""));
	}

	private void cargarCoordinadores() {
		List<Coordinacion> lista = personaService.findAllCoordinadores();
		System.out.println("Coordinadores encontrados: " + lista.size());
		comboCoordinador.setItems(FXCollections.observableArrayList(lista));
		System.out.println("personaService: " + personaService);
		System.out.println("Coordinadores: " + lista.size());

		System.out.println(">>> cargarCoordinadores ejecutado");

	}

	private void cargarTablaEspectaculos() {
		tablaEspectaculos.setItems(FXCollections.observableArrayList(espectaculoService.listarTodos()));
	}

	@FXML
	private void limpiarFormulario() {
		txtNombre.clear();
		dateInicio.setValue(null);
		dateFin.setValue(null);
		comboCoordinador.getSelectionModel().clearSelection();
		espectaculoEditando = null;
		tablaEspectaculos.getSelectionModel().clearSelection();
	}

	private void validarFormulario() {
		if (txtNombre.getText().isBlank() || dateInicio.getValue() == null || dateFin.getValue() == null
				|| comboCoordinador.getValue() == null) {

			throw new RuntimeException("Todos los campos son obligatorios");
		}

		// Nombre maximo 25 caracteres
		if (txtNombre.getText().trim().length() > 25) {
			throw new RuntimeException("El nombre no puede superar los 25 caracteres");
		}

		// Fecha fin posterior a fecha inicio
		if (dateFin.getValue().isAfter(dateInicio.getValue().plusYears(1))) {
			throw new RuntimeException("El periodo no puede ser superior a 1 año");
		}

		// Periodo no superior a 1 año
		if (dateFin.getValue().isAfter(dateInicio.getValue().plusYears(1))) {
			throw new RuntimeException("El periodo no puede ser superior a 1 año");
		}

		// Nombre único
		if (espectaculoEditando == null) {
			if (espectaculoService.existsByNombre(txtNombre.getText().trim())) {
				throw new RuntimeException("Ya existe un espectáculo con ese nombre");
			}
		} else {
			if (!espectaculoEditando.getNombre().equalsIgnoreCase(txtNombre.getText().trim())
					&& espectaculoService.existsByNombre(txtNombre.getText().trim())) {
				throw new RuntimeException("Ya existe un espectáculo con ese nombre");
			}
		}
	}

	private Espectaculo construirEspectaculoDesdeFormulario() {

		Espectaculo e = (espectaculoEditando != null) ? espectaculoEditando : new Espectaculo();

		e.setNombre(txtNombre.getText().trim());
		e.setFechaInicio(dateInicio.getValue());
		e.setFechaFin(dateFin.getValue());
		e.setCoordinador(comboCoordinador.getValue());

		return e;
	}

	private void cargarEspectaculoEnFormulario(Espectaculo e) {
		espectaculoEditando = e;

		txtNombre.setText(e.getNombre());
		dateInicio.setValue(e.getFechaInicio());
		dateFin.setValue(e.getFechaFin());
		comboCoordinador.setValue(e.getCoordinador());
	}

	@FXML
	private void guardarEspectaculo(ActionEvent event) {
		try {
			validarFormulario();
			Espectaculo e = construirEspectaculoDesdeFormulario();
			espectaculoService.guardar(e);

			mostrarInfo("Espectaculo guardado correctamente");
			limpiarFormulario();
			cargarTablaEspectaculos();

		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	@FXML
	private void eliminarEspectaculo(ActionEvent event) {
		Espectaculo seleccionado = tablaEspectaculos.getSelectionModel().getSelectedItem();

		if (seleccionado == null) {
			mostrarError("Seleccione un espectáculo");
			return;
		}

		try {
			espectaculoService.eliminar(seleccionado.getId());
			mostrarInfo("Eliminado correctamente");
			cargarTablaEspectaculos();
			limpiarFormulario();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@FXML
	private void editarEspectaculo(ActionEvent event) {
		Espectaculo seleccionado = tablaEspectaculos.getSelectionModel().getSelectedItem();
		if (seleccionado == null) {
			mostrarError("Selecciona un espectáculo de la tabla");
			return;
		}
		cargarEspectaculoEnFormulario(seleccionado);
	}

	
	@FXML
	private void onVerDetalles(ActionEvent event) {
		Espectaculo seleccionado = tablaEspectaculos.getSelectionModel().getSelectedItem();
		
		if (seleccionado == null) {
			mostrarError("Seleccione un espectáculo");
			return;
		}
		Espectaculo completo = espectaculoService.cargarEspectaculoCompleto(seleccionado.getId());
		

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detalle_espectaculo.fxml"));
			Parent root = loader.load();

			DetalleEspectaculoController controller = loader.getController();
			controller.setEspectaculo(completo);

			Stage stage = new Stage();
			stage.setTitle("Detalle del espectáculo");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
			mostrarError(e.getMessage());
		}
	}

	@FXML
	private void abrirNumeros(ActionEvent event) {
		Espectaculo seleccionado = tablaEspectaculos.getSelectionModel().getSelectedItem();
		if (seleccionado == null) {
			mostrarError("Selecciona un espectáculo primero");
			return;
		}
		sesionService.setEspectaculoActual(seleccionado);
		stageManager.switchScene(FxmlView.NUMEROS);
	}

	
	@FXML
	private void logout(ActionEvent event) throws IOException {
		sesionService.cerrarSesion();
		stageManager.switchScene(FxmlView.LOGIN);
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
