package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.SpringFXMLLoader;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.log.LogOperacionService;
import com.luisdbb.tarea3AD2024base.log.TipoOperacion;
import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Coordinacion;
import com.luisdbb.tarea3AD2024base.modelo.Credencial;
import com.luisdbb.tarea3AD2024base.modelo.Especialidad;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.modelo.Rol;
import com.luisdbb.tarea3AD2024base.repositorios.ArtistaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialRepository;
import com.luisdbb.tarea3AD2024base.repositorios.NumeroRepository;
import com.luisdbb.tarea3AD2024base.repositorios.PersonaRepository;
import com.luisdbb.tarea3AD2024base.modelo.Persona;
import com.luisdbb.tarea3AD2024base.services.PersonaService;
import com.luisdbb.tarea3AD2024base.services.RegistroService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class UserController implements Initializable {

	@FXML
	private Button btnLogout;

	@FXML
	private TextField nombre;

	@FXML
	private TextField email;

	@FXML
	private TextField username;

	@FXML
	private TextField nacionalidad;

	@FXML
	private ComboBox<String> tipoPersona;

	@FXML
	private VBox camposArtista;

	@FXML
	private VBox camposCoordinacion;

	@FXML
	private TextField apodo;

	@FXML
	private CheckBox chkAcrobacia, chkHumor, chkMagia, chkEquilibrismo, chkMalabarismo;
	@FXML
	private CheckBox chkSenior;

	@FXML
	private DatePicker fechaSenior;

	@FXML
	private PasswordField password;

	@FXML
	private Button reset;

	@FXML
	private Button saveUser;

	@FXML
	private Button gestionEspectaculos;

	@FXML
	private Button btnDetalles;

	@FXML
	private TableView<Persona> userTable;

	@FXML
	private TableColumn<Persona, Long> colUserId;

	@FXML
	private TableColumn<Persona, String> colName;

	@FXML
	private TableColumn<Persona, String> colNacion;

	@FXML
	private TableColumn<Persona, String> colEmail;

	@FXML
	private TableColumn<Persona, String> colTipoPersona;

	@FXML
	private TableColumn<Persona, Boolean> colEdit;

	@FXML
	private TableView<Espectaculo> tablaEspectaculos;

	@FXML
	private MenuItem deleteUsers;

	private StageManager stageManager;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private RegistroService registroService;

	@Autowired
	private CredencialRepository credencialRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private ArtistaRepository artistaRepository;

	@Autowired
	private SesionService sesionService;

	@Autowired
	private LogOperacionService logService;

	@Autowired
	private SpringFXMLLoader springFXMLLoader;

	@Autowired
	private NumeroRepository numeroRepository;

	private ObservableList<Persona> userList = FXCollections.observableArrayList();
	private ObservableList<String> roles = FXCollections.observableArrayList("Artista", "Coordinacion");

	public void setStageManager(StageManager stageManager) {
		this.stageManager = stageManager;
	}

	@FXML
	private void exit(ActionEvent event) {
		Platform.exit();
	}

	/**
	 * Logout and go to the login page
	 */
	@FXML
	private void logout(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	void limpiar(ActionEvent event) {
		clearFields();
	}

	private Persona personaEditando = null;

	@FXML
	private void cargarPersonaEnFormulario(Persona persona) {

		personaEditando = persona;

		System.out.println("Persona: " + persona.getNombre());
		System.out.println("Credencial: " + persona.getCredencial());

		nombre.setText(persona.getNombre());
		nacionalidad.setText(persona.getNacionalidad());
		email.setText(persona.getEmail());

		Credencial credencial = credencialRepository.findByPersonaId(persona.getId()).orElse(null);

		if (credencial != null) {
			username.setText(credencial.getUsername());
			password.setText(credencial.getPassword());
		}

		if (persona instanceof Artista) {

			Artista a = artistaRepository.findById(persona.getId()).orElseThrow();
			personaEditando = a;

			chkAcrobacia.setSelected(false);
			chkHumor.setSelected(false);
			chkMagia.setSelected(false);
			chkEquilibrismo.setSelected(false);
			chkMalabarismo.setSelected(false);

			tipoPersona.setValue("Artista");

			camposArtista.setVisible(true);
			camposArtista.setManaged(true);

			camposCoordinacion.setVisible(false);
			camposCoordinacion.setManaged(false);

			apodo.setText(a.getApodo());

			chkAcrobacia.setSelected(a.getEspecialidades().contains(Especialidad.ACROBACIA));
			chkHumor.setSelected(a.getEspecialidades().contains(Especialidad.HUMOR));
			chkMagia.setSelected(a.getEspecialidades().contains(Especialidad.MAGIA));
			chkEquilibrismo.setSelected(a.getEspecialidades().contains(Especialidad.EQUILIBRISMO));
			chkMalabarismo.setSelected(a.getEspecialidades().contains(Especialidad.MALABARISMO));

		} else if (persona instanceof Coordinacion c) {

			tipoPersona.setValue("Coordinacion");

			camposCoordinacion.setVisible(true);
			camposCoordinacion.setManaged(true);

			camposArtista.setVisible(false);
			camposArtista.setManaged(false);

			chkSenior.setSelected(c.isSenior());
			fechaSenior.setValue(c.getFechaSenior());
		}
	}

	@FXML
	private void saveUser(ActionEvent event) {
		try {

			boolean esEdicion = (personaEditando != null);
			Persona persona;

			if (esEdicion) {

				boolean tipoCorrectoArtista = tipoPersona.getValue().equals("Artista")
						&& personaEditando instanceof Artista;
				boolean tipoCorrectoCoord = tipoPersona.getValue().equals("Coordinacion")
						&& personaEditando instanceof Coordinacion;

				if (!tipoCorrectoArtista && !tipoCorrectoCoord) {
					throw new RuntimeException("No se puede cambiar el tipo de una persona existente");
				}
			}

			if (tipoPersona.getValue().equals("Artista")) {

				Artista artista = esEdicion ? (Artista) personaEditando : new Artista();

				artista.setNombre(nombre.getText());
				artista.setEmail(email.getText());
				artista.setNacionalidad(nacionalidad.getText());

				if (!apodo.getText().isEmpty()) {
					artista.setApodo(apodo.getText());
				} else {
					artista.setApodo(null);
				}

				Set<Especialidad> especialidades = new HashSet<>();
				if (chkAcrobacia.isSelected())
					especialidades.add(Especialidad.ACROBACIA);
				if (chkHumor.isSelected())
					especialidades.add(Especialidad.HUMOR);
				if (chkMagia.isSelected())
					especialidades.add(Especialidad.MAGIA);
				if (chkEquilibrismo.isSelected())
					especialidades.add(Especialidad.EQUILIBRISMO);
				if (chkMalabarismo.isSelected())
					especialidades.add(Especialidad.MALABARISMO);
				artista.setEspecialidades(especialidades);

				persona = artista;

			} else {
				Coordinacion coord = esEdicion ? (Coordinacion) personaEditando : new Coordinacion();
				coord.setNombre(nombre.getText());
				coord.setEmail(email.getText());
				coord.setNacionalidad(nacionalidad.getText());
				coord.setSenior(chkSenior.isSelected());

				if (chkSenior.isSelected() && fechaSenior.getValue() != null) {
					coord.setFechaSenior(fechaSenior.getValue());
				} else {
					coord.setFechaSenior(null);
				}

				persona = coord;
			}

			Credencial credencial = esEdicion
					? credencialRepository.findByPersonaId(personaEditando.getId()).orElse(new Credencial())
					: new Credencial();
			String nuevoUsername = username.getText();
			String nuevoPassword = password.getText();

			// Validación de duplicados si cambian
			if (esEdicion) {
				if (credencial.getUsername() != null && !credencial.getUsername().equalsIgnoreCase(nuevoUsername)) {
					if (credencialRepository.existsByUsername(nuevoUsername.toLowerCase())) {
						throw new RuntimeException("El username ya existe");
					}
				}
			}

			if (esEdicion) {
				if (personaEditando.getEmail() != null
						&& !personaEditando.getEmail().equalsIgnoreCase(persona.getEmail())) {
					if (personaRepository.existsByEmail(persona.getEmail().toLowerCase())) {
						throw new RuntimeException("El email ya existe");
					}
				}
			}

			credencial.setUsername(username.getText());
			credencial.setPassword(password.getText());
			credencial.setRol(tipoPersona.getValue().equals("Artista") ? Rol.ARTISTA : Rol.COORDINACION);
			credencial.setPersona(persona);

			// Guardar
			// registroService.registrarPersona(persona, credencial);

			// Guardar
			Persona guardada = registroService.registrarPersona(persona, credencial);

			String usuario = (sesionService.getUsuarioActual() != null) ? sesionService.getUsuarioActual().getUsername()
					: "INVITADO";

			// Log
			logService.registrar(usuario, esEdicion ? TipoOperacion.ACTUALIZACION : TipoOperacion.NUEVO,
					"Persona " + (esEdicion ? "modificada" : "creada") + ": id=" + guardada.getId() + ", email="
							+ guardada.getEmail());

			// Reset
			personaEditando = null;
			clearFields();
			loadUserDetails();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Usuario guardado correctamente");
			alert.showAndWait();

		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	private void deleteUsers(ActionEvent event) {

		List<Persona> personas = userTable.getSelectionModel().getSelectedItems();

		if (personas == null || personas.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Seleccione al menos un usuario");
			alert.showAndWait();
			return;
		}

		// evitar borrar artistas con números asignados
		for (Persona p : personas) {
			if (p instanceof Artista a){
				if (!numeroRepository.findByArtistasId(a.getId()).isEmpty()) {
	                mostrarError("No se puede eliminar al artista '" + a.getNombre() +
	                             "' porque tiene números asignados.");
	                return;
	            }
			}
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {

			String usuario = (sesionService.getUsuarioActual() != null) ? sesionService.getUsuarioActual().getUsername()
					: "INVITADO";

			for (Persona p : personas) {
				String usernamePersona = (p.getCredencial() != null ? p.getCredencial().getUsername()
						: "SIN_CREDENCIAL");

				logService.registrar(usuario, TipoOperacion.BORRADO, "Persona eliminada: id=" + p.getId() + ", email="
						+ p.getEmail() + ", username=" + usernamePersona);
			}

			try {
				personaService.deleteInBatch(personas);
			} catch (Exception e) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setContentText("No se pudo eliminar. El usuario tiene relaciones asociadas.");
				a.showAndWait();
			}
		}

		loadUserDetails();
	}

	@FXML
	private void onGestionarEspecialidades(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(springFXMLLoader.getContext()::getBean);
			loader.setLocation(getClass().getResource("/fxml/especialidad.fxml"));

			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Gestión de Especialidades");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearFields() {
		personaEditando = null;
		nombre.clear();
		email.clear();
		nacionalidad.clear();
		username.clear();
		password.clear();
		tipoPersona.getSelectionModel().clearSelection();
		apodo.clear();
		chkAcrobacia.setSelected(false);
		chkHumor.setSelected(false);
		chkMagia.setSelected(false);
		chkEquilibrismo.setSelected(false);
		chkMalabarismo.setSelected(false);
		chkSenior.setSelected(false);
		fechaSenior.setValue(null);
		camposArtista.setVisible(false);
		camposArtista.setManaged(false);
		camposCoordinacion.setVisible(false);
		camposCoordinacion.setManaged(false);
	}

	/*
	 * @FXML private void abrirEspectaculos(ActionEvent event) { try { FXMLLoader
	 * loader = new FXMLLoader(getClass().getResource("/fxml/espectaculo.fxml"));
	 * Parent root = loader.load();
	 * 
	 * Stage stage = new Stage(); stage.setTitle("Gestión de Espectáculos");
	 * stage.setScene(new Scene(root)); stage.show();
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * mostrarError("No se pudo abrir la ventana de espectáculos"); } }
	 * 
	 */

	@FXML
	private void abrirEspectaculos(ActionEvent event) {
		stageManager.switchScene(FxmlView.ESPECTACULOS);
	}

	@FXML
	private void abrirArtistas(ActionEvent event) {
		stageManager.switchScene(FxmlView.ARTISTA);
	}

	private void saveAlert(Persona user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Guardado");
		alert.setHeaderText(null);
		alert.setContentText("Registro guardado correctamente.");
		alert.showAndWait();
	}

	private void updateAlert(Persona user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Actualizado");
		alert.setHeaderText(null);
		alert.setContentText("Registro actualizado correctamente.");
		alert.showAndWait();
	}

	public String getNombre() {
		return nombre.getText();
	}

	public void setNombre(TextField nombre) {
		this.nombre = nombre;
	}

	public TextField getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(TextField nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void setEmail(TextField email) {
		this.email = email;
	}

	public String getEmail() {
		return email.getText();
	}

	public String getPassword() {
		return password.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tipoPersona.setItems(roles);

		userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		setColumnProperties();

		loadUserDetails();

		// Listener para mostrar campos según tipo
		tipoPersona.valueProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal == null) {
				camposArtista.setVisible(false);
				camposArtista.setManaged(false);
				camposCoordinacion.setVisible(false);
				camposCoordinacion.setManaged(false);
			} else if (newVal.equals("Artista")) {
				camposArtista.setVisible(true);
				camposArtista.setManaged(true);
				camposCoordinacion.setVisible(false);
				camposCoordinacion.setManaged(false);
			} else {
				camposCoordinacion.setVisible(true);
				camposCoordinacion.setManaged(true);
				camposArtista.setVisible(false);
				camposArtista.setManaged(false);
			}
		});

		// Mostrar/ocultar fechaSenior según el checkbox
		chkSenior.selectedProperty().addListener((obs, oldVal, newVal) -> {
			fechaSenior.setVisible(newVal);
			fechaSenior.setManaged(newVal);
		});
	}

	/*
	 * Set All userTable column properties
	 */
	private void setColumnProperties() {
		/*
		 * Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new
		 * StringConverter<LocalDate>() { String pattern = "dd/MM/yyyy";
		 * DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		 * 
		 * @Override public String toString(LocalDate date) { if (date != null) { return
		 * dateFormatter.format(date); } else { return ""; } }
		 * 
		 * @Override public LocalDate fromString(String string) { if (string != null &&
		 * !string.isEmpty()) { return LocalDate.parse(string, dateFormatter); } else {
		 * return null; } } }));
		 */

		colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colNacion.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colTipoPersona.setCellValueFactory(cellData -> {

			Persona p = cellData.getValue();

			if (p instanceof Artista) {
				return new SimpleStringProperty("Artista");
			} else if (p instanceof Coordinacion) {
				return new SimpleStringProperty("Coordinacion");
			}

			return new SimpleStringProperty("Desconocido");
		});

		colEdit.setCellFactory(cellFactory);
	}

	Callback<TableColumn<Persona, Boolean>, TableCell<Persona, Boolean>> cellFactory=new Callback<TableColumn<Persona,Boolean>,TableCell<Persona,Boolean>>(){@Override public TableCell<Persona,Boolean>call(final TableColumn<Persona,Boolean>param){final TableCell<Persona,Boolean>cell=new TableCell<Persona,Boolean>(){Image imgEdit=new Image(getClass().getResourceAsStream("/images/edit.png"));final Button btnEdit=new Button();

	@Override public void updateItem(Boolean check,boolean empty){super.updateItem(check,empty);if(empty){setGraphic(null);setText(null);}else{btnEdit.setOnAction(e->{Persona user=getTableView().getItems().get(getIndex());cargarPersonaEnFormulario(user);});

	btnEdit.setStyle("-fx-background-color: transparent;");ImageView iv=new ImageView();iv.setImage(imgEdit);iv.setPreserveRatio(true);iv.setSmooth(true);iv.setCache(true);btnEdit.setGraphic(iv);

	setGraphic(btnEdit);setAlignment(Pos.CENTER);setText(null);}}

	};return cell;}};

	/*
	 * Add All users to observable list and update table
	 */
	private void loadUserDetails() {
		userList.clear();
		userList.addAll(personaRepository.findAllByOrderByIdAsc());

		userTable.setItems(userList);
	}

	/*
	 * Validations
	 */
	private boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (field.equals("Role"))
			alert.setContentText("Please Select " + field);
		else {
			if (empty)
				alert.setContentText("Please Enter " + field);
			else
				alert.setContentText("Please Enter Valid " + field);
		}
		alert.showAndWait();
	}

	private void mostrarError(String msg) {
		Alert a = new Alert(Alert.AlertType.ERROR);
		a.setHeaderText("Error");
		a.setContentText(msg);
		a.showAndWait();
	}
}
