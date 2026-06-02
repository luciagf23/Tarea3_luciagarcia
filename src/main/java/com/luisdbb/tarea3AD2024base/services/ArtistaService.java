package com.luisdbb.tarea3AD2024base.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Especialidad;
import com.luisdbb.tarea3AD2024base.repositorios.ArtistaRepository;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public Artista guardar(Artista artista) {
        return artistaRepository.save(artista);
    }

    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }
    
    private void validarEspecialidades(Set<Especialidad> especialidades) {
        if (especialidades == null || especialidades.isEmpty()) {
            throw new RuntimeException("El artista debe tener al menos una especialidad");
        }
    }

}
