package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Especialidad;
import com.luisdbb.tarea3AD2024base.repositorios.EspecialidadRepository;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository repo;

    public List<Especialidad> findAll() {
        return repo.findAll();
    }

    public Especialidad save(Especialidad e) {
        return repo.save(e);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

