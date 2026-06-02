package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Coordinacion;
import com.luisdbb.tarea3AD2024base.repositorios.CoordinacionRepository;

@Service
public class CoordinacionService {

    @Autowired
    private CoordinacionRepository coordinacionRepository;

    public Coordinacion guardar(Coordinacion coordinacion) {
        return coordinacionRepository.save(coordinacion);
    }

    public List<Coordinacion> listarTodos() {
        return coordinacionRepository.findAll();
    }
    
    private void validarCoordinacion(Coordinacion c) {
        if (c.isSenior() && c.getFechaSenior() == null) {
            throw new RuntimeException("Debe indicar la fecha desde que es senior");
        }
    }

}
