package com.luisdbb.tarea3AD2024base.controller;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Numero;
import com.luisdbb.tarea3AD2024base.services.NumeroService;
import com.luisdbb.tarea3AD2024base.services.SesionService;

import java.net.URL;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

@Controller
public class FichaArtistaController implements Initializable {

    @FXML private Label lblNombre;
    @FXML private Label lblEmail;
    @FXML private Label lblNacionalidad;
    @FXML private Label lblApodo;
    @FXML private Label lblEspecialidades;

    @FXML private ListView<String> listaNumeros;
    @FXML private ListView<String> listaEspectaculos;

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
        cargarFicha();
    }

    private void cargarFicha() {

        Artista artista = (Artista) sesionService.getUsuarioActual().getPersona();

        lblNombre.setText("Nombre: " + artista.getNombre());
        lblEmail.setText("Email: " + artista.getEmail());
        lblNacionalidad.setText("Nacionalidad: " + artista.getNacionalidad());
        lblApodo.setText("Apodo: " + (artista.getApodo() != null ? artista.getApodo() : "-"));
        lblEspecialidades.setText("Especialidades: " +
            artista.getEspecialidades().stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "))
        );

        List<Numero> numeros = numeroService.findByArtista(artista.getId());

        listaNumeros.setItems(FXCollections.observableArrayList(
            numeros.stream()
                .map(n -> n.getId() + " - " + n.getNombre())
                .collect(Collectors.toList())
        ));

        listaEspectaculos.setItems(FXCollections.observableArrayList(
            numeros.stream()
                .map(Numero::getEspectaculo)
                .distinct()
                .map(e -> e.getId() + " - " + e.getNombre())
                .collect(Collectors.toList())
        ));
    }
}



