package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.log.LogOperacionService;
import com.luisdbb.tarea3AD2024base.log.TipoOperacion;
import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.modelo.Numero;
import com.luisdbb.tarea3AD2024base.services.EspectaculoService;
import com.luisdbb.tarea3AD2024base.services.NumeroService;
import com.luisdbb.tarea3AD2024base.services.PersonaService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

@Controller
public class NumeroController implements Initializable {

	@FXML
	private Label lblEspectaculo;
	@FXML
	private TableView<Numero> tablaNumeros;
	@FXML
	private TableColumn<Numero, Integer> colOrden;
	@FXML
	private TableColumn<Numero, String> colNombre;
	@FXML
	private TableColumn<Numero, Double> colDuracion;

	@FXML
	private TableColumn<Numero, String> colArtistas;
	@FXML
	private TextField txtOrden;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtDuracion;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnNuevo;
	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnVolver;

	@FXML
	private ListView<Artista> listaArtistas;

	@Autowired
	private NumeroService numeroService;

	@Autowired
	private SesionService sesionService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private EspectaculoService espectaculoService;

	private StageManager stageManager;

	@Autowired
	private LogOperacionService logService;

	private Espectaculo espectaculoActual;
	private Numero numeroEditando = null;
	private ObservableList<Numero> listaNumeros = FXCollections.observableArrayList();

	public void setStageManager(StageManager stageManager) {
		this.stageManager = stageManager;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		espectaculoActual = sesionService.getEspectaculoActual();

		lblEspectaculo.setText("Espectáculo: " + espectaculoActual.getNombre());

		colOrden.setCellValueFactory(new PropertyValueFactory<>("orden"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
		colArtistas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtistasTexto()));
		cargarNumeros();
		cargarArtistas();

		listaArtistas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tablaNumeros.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

			if (newVal != null)
				cargarNumeroEnFormulario(newVal);
		});
	}

	/*
	 * private void cargarNumeros() { listaNumeros.clear();
	 * listaNumeros.addAll(numeroService.findByEspectaculo(espectaculoActual.getId()
	 * )); tablaNumeros.setItems(
	 * FXCollections.observableArrayList(numeroService.findByEspectaculo(
	 * espectaculoActual.getId()))); }
	 */
	private void cargarNumeros() {
		listaNumeros.setAll(numeroService.findByEspectaculo(espectaculoActual.getId()));
		tablaNumeros.setItems(listaNumeros);
	}

	private void cargarArtistas() {
		listaArtistas.getItems().clear();

		var artistas = personaService.findAllArtistas();
		listaArtistas.getItems().addAll(artistas);

		// Mostrar solo el nombre en la lista
		listaArtistas.setCellFactory(lv -> new ListCell<Artista>() {
			@Override
			protected void updateItem(Artista item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? "" : item.getNombre());
			}
		});
	}

	private void cargarNumeroEnFormulario(Numero n) {
		n = numeroService.findByIdConArtistas(n.getId());
		numeroEditando = n;

		txtOrden.setText(String.valueOf(n.getOrden()));
		txtNombre.setText(n.getNombre());
		txtDuracion.setText(String.valueOf(n.getDuracion()));

		// Seleccionar artista del numero
		listaArtistas.getSelectionModel().clearSelection();
		for (Artista artistaNumero : n.getArtistas()) {
			for (Artista artistaLista : listaArtistas.getItems()) {
				if (artistaLista.getId().equals(artistaNumero.getId())) {
					listaArtistas.getSelectionModel().select(artistaLista);
					break;
				}
			}
		}
		// Forzar multiple despues de select
		Platform.runLater(() -> {
			listaArtistas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		});
	}

	@FXML
	private void guardarNumero(ActionEvent event) {
		try {

			validar();
			Set<Artista> artistasSeleccionados = new HashSet<>(listaArtistas.getSelectionModel().getSelectedItems());

			if (artistasSeleccionados.isEmpty()) {
				throw new Exception("Debe asignar al menos un artista");
			}

			Numero n = numeroEditando != null ? numeroEditando : new Numero();
			n.setNombre(txtNombre.getText().trim());
			n.setOrden(Integer.parseInt(txtOrden.getText().trim()));

			// Validar duración formato x,y donde y es 0 o 5
			double duracion = parseDuracion(txtDuracion.getText().trim());
			n.setDuracion(duracion);
			n.setEspectaculo(espectaculoActual);

			n = numeroService.guardar(n);

			if (n.getArtistas() == null) {
				n.setArtistas(new HashSet<>());
			}

			// Comparar por ID para evitar reemplazos
			Set<Long> idsSeleccionados = artistasSeleccionados.stream().map(Artista::getId).collect(Collectors.toSet());

			// Quitar los desmarcados
			n.getArtistas().removeIf(a -> !idsSeleccionados.contains(a.getId()));

			// Añadir los nuevos
			for (Artista a : artistasSeleccionados) {
				boolean yaExiste = n.getArtistas().stream().anyMatch(existing -> existing.getId().equals(a.getId()));
				if (!yaExiste) {
					n.getArtistas().add(a);
				}
			}
			
			//Guardar de nuevo
			n = numeroService.guardar(n);

			// Registro del log
			String usuario = (sesionService.getUsuarioActual() != null) ? sesionService.getUsuarioActual().getUsername()
					: "INVITADO";

			logService.registrar(usuario, numeroEditando == null ? TipoOperacion.NUEVO : TipoOperacion.ACTUALIZACION,
					"Número guardado: id=" + n.getId() + ", nombre=" + n.getNombre());
			numeroEditando = n;

			limpiar();
			cargarNumeros();
			mostrarInfo("Número guardado correctamente");

		} catch (Exception e) {
			mostrarError(e.getMessage());
		}

	}

	@FXML
	private void nuevoNumero(ActionEvent event) {
		limpiar();
	}

	@FXML
	private void eliminarNumero(ActionEvent event) {
		if (numeroEditando == null) {
			mostrarError("Selecciona un número primero");
			return;
		}

		List<Numero> numerosActuales = numeroService.findByEspectaculo(espectaculoActual.getId());
		if (numerosActuales.size() <= 3) {
			mostrarError("Un espectáculo debe tener al menos 3 números");
			return;
		}

		// Registro del log
		logService.registrar(sesionService.getUsuarioActual().getUsername(), TipoOperacion.BORRADO,
				"Número eliminado: id=" + numeroEditando.getId() + ", nombre=" + numeroEditando.getNombre());

		numeroService.eliminar(numeroEditando.getId());

		limpiar();
		cargarNumeros();
	}

	@FXML
	private void volver(ActionEvent event) {
		try {
			espectaculoService.validarMinimoNumeros(espectaculoActual);
			stageManager.switchScene(FxmlView.ESPECTACULOS);
		} catch (Exception e) {
			mostrarError(e.getMessage());
		}
	}

	private double parseDuracion(String texto) {
		// Acepta tanto punto como coma decimal
		texto = texto.replace(",", ".");
		double valor = Double.parseDouble(texto);
		// Obtener la parte decimal
		double decimal = valor - Math.floor(valor);
		// Solo permite ,0 o ,5
		if (Math.abs(decimal - 0.0) > 0.001 && Math.abs(decimal - 0.5) > 0.001) {
			throw new RuntimeException("La duración debe ser en formato x,0 o x,5");
		}
		return valor;
	}

	private void validar() throws Exception {

		if (txtOrden.getText().trim().isEmpty()) {
			throw new Exception("Debe indicar el orden del número");
		}
		if (txtNombre.getText().isBlank() || txtOrden.getText().isBlank() || txtDuracion.getText().isBlank()) {
			throw new RuntimeException("Todos los campos son obligatorios");
		}

		try {
			Integer.parseInt(txtOrden.getText().trim());
		} catch (NumberFormatException e) {
			throw new RuntimeException("El orden debe ser un número entero");
		}
	}

	private void limpiar() {
		numeroEditando = null;
		txtNombre.clear();
		txtOrden.clear();
		txtDuracion.clear();
		tablaNumeros.getSelectionModel().clearSelection();
		listaArtistas.getSelectionModel().clearSelection();

	}

	private void mostrarError(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Ha ocurrido un error");
		alert.setContentText(msg);
		alert.showAndWait();
	}

	private void mostrarInfo(String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
