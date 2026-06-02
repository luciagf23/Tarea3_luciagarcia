package com.luisdbb.tarea3AD2024base.controller;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.Coordinacion;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.services.EspectaculoService;
import com.luisdbb.tarea3AD2024base.services.PersonaService;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@Controller
public class EspectaculoController implements Serializable {

	@FXML
	private TableView<Espectaculo> tablaEspectaculos;
	@FXML
	private TableColumn<Espectaculo, String> colNombre;
	@FXML
	private TableColumn<Espectaculo, String> colFechaInicio;
	@FXML
	private TableColumn<Espectaculo, String> colFechaFin;
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

	@Autowired
	private EspectaculoService espectaculoService;

	@Autowired
	private PersonaService personaService;

	private Espectaculo espectaculoEditando;
	
	
	public EspectaculoController() {
	    
	}


	public void initialize(URL location, ResourceBundle resources) {
		configurarTabla();
		cargarCoordinadores();
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
	    List<Coordinacion> lista = personaService.listarCoordinadores();
	    System.out.println("Coordinadores encontrados: " + lista.size());
	    comboCoordinador.setItems(FXCollections.observableArrayList(lista));
	    System.out.println("personaService es: " + personaService);

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
	private void onVerDetalles(ActionEvent event) {
	    Espectaculo seleccionado = tablaEspectaculos.getSelectionModel().getSelectedItem();

	    if (seleccionado == null) {
	        mostrarError("Seleccione un espectáculo");
	        return;
	    }

	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detalle_espectaculo.fxml"));
	        Parent root = loader.load();

	        DetalleEspectaculoController controller = loader.getController();
	        controller.setEspectaculo(seleccionado);

	        Stage stage = new Stage();
	        stage.setTitle("Detalle del espectáculo");
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (Exception e) {
	    	e.printStackTrace();
	        mostrarError(e.getMessage());
	    }
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
