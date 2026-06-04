package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.modelo.Especialidad;
import com.luisdbb.tarea3AD2024base.services.EspecialidadService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class EspecialidadController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TableView<Especialidad> tabla;

    @FXML
    private TableColumn<Especialidad, Long> colId;

    @FXML
    private TableColumn<Especialidad, String> colNombre;

    @FXML
    private TableColumn<Especialidad, String> colDescripcion;

    @Autowired
    private EspecialidadService service;

    private Especialidad seleccionada;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        cargarTabla();

        tabla.setOnMouseClicked(e -> {
            seleccionada = tabla.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {
                txtNombre.setText(seleccionada.getNombre());
                txtDescripcion.setText(seleccionada.getDescripcion());
            }
        });
    }

    private void cargarTabla() {
        tabla.setItems(FXCollections.observableArrayList(service.findAll()));
    }

    @FXML
    private void guardar() {
        if (seleccionada == null) {
            seleccionada = new Especialidad();
        }

        seleccionada.setNombre(txtNombre.getText());
        seleccionada.setDescripcion(txtDescripcion.getText());

        service.save(seleccionada);
        seleccionada = null;
        limpiar();
        cargarTabla();
    }

    @FXML
    private void eliminar() {
        if (seleccionada != null) {
            service.delete(seleccionada.getId());
            seleccionada = null;
            limpiar();
            cargarTabla();
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtDescripcion.clear();
        tabla.getSelectionModel().clearSelection();
        seleccionada = null;
    }
}

