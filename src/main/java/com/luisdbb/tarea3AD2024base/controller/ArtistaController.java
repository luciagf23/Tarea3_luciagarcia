package com.luisdbb.tarea3AD2024base.controller;



import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Especialidad;
import com.luisdbb.tarea3AD2024base.services.ArtistaService;
import com.luisdbb.tarea3AD2024base.services.EspecialidadService;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

@Controller
public class ArtistaController implements Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNacionalidad;

    @FXML
    private TextField txtApodo;

    @FXML
    private ComboBox<Especialidad> comboEspecialidad;

    @FXML
    private ListView<Especialidad> listaEspecialidades;

    @FXML
    private TableView<Artista> tablaArtistas;

    @FXML
    private TableColumn<Artista, Long> colId;

    @FXML
    private TableColumn<Artista, String> colNombre;

    @FXML
    private TableColumn<Artista, String> colNacionalidad;

    @FXML
    private TableColumn<Artista, String> colApodo;

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private EspecialidadService especialidadService;

    private Artista artistaSeleccionado;
    
    private StageManager stageManager;
    
    public void setStageManager(StageManager stageManager) {
		this.stageManager = stageManager;
	}

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configurar columnas
    	colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    	colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
    	colApodo.setCellValueFactory(new PropertyValueFactory<>("apodo"));


        // Cargar artistas
        cargarTabla();

        // Cargar especialidades desde BD
        comboEspecialidad.setItems(
                FXCollections.observableArrayList(especialidadService.findAll())
        );

        // Selección en tabla
        tablaArtistas.setOnMouseClicked(e -> {
            artistaSeleccionado = tablaArtistas.getSelectionModel().getSelectedItem();
            if (artistaSeleccionado != null) {
                cargarDatosArtista();
            }
        });
    }

    private void cargarTabla() {
        tablaArtistas.setItems(
                FXCollections.observableArrayList(artistaService.findAll())
        );
    }

    private void cargarDatosArtista() {
        txtNombre.setText(artistaSeleccionado.getNombre());
        txtNacionalidad.setText(artistaSeleccionado.getNacionalidad());
        txtApodo.setText(artistaSeleccionado.getApodo());

        listaEspecialidades.setItems(
                FXCollections.observableArrayList(artistaSeleccionado.getEspecialidades())
        );
    }

    @FXML
    private void guardarArtista() {

        if (artistaSeleccionado == null) {
            artistaSeleccionado = new Artista();
        }

        artistaSeleccionado.setNombre(txtNombre.getText());
        artistaSeleccionado.setNacionalidad(txtNacionalidad.getText());
        artistaSeleccionado.setApodo(txtApodo.getText());

        artistaSeleccionado.setEspecialidades(
                new HashSet<>(listaEspecialidades.getItems())
        );

        artistaService.guardar(artistaSeleccionado);

        limpiar();
        cargarTabla();
    }

    @FXML
    private void eliminarArtista() {
        if (artistaSeleccionado != null) {
            artistaService.delete(artistaSeleccionado.getId());
            limpiar();
            cargarTabla();
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtNacionalidad.clear();
        txtApodo.clear();
        comboEspecialidad.getSelectionModel().clearSelection();
        listaEspecialidades.getItems().clear();
        tablaArtistas.getSelectionModel().clearSelection();
        artistaSeleccionado = null;
    }

    @FXML
    private void añadirEspecialidad() {
        Especialidad esp = comboEspecialidad.getValue();

        if (esp != null && !listaEspecialidades.getItems().contains(esp)) {
            listaEspecialidades.getItems().add(esp);
        }
    }

    @FXML
    private void eliminarEspecialidad() {
        Especialidad esp = listaEspecialidades.getSelectionModel().getSelectedItem();
        if (esp != null) {
            listaEspecialidades.getItems().remove(esp);
        }
    }

	
}

