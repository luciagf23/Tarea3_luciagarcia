package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.repositorios.EspectaculoRepository;

@Service
public class EspectaculoService {

    @Autowired
    private EspectaculoRepository espectaculoRepository;

    public Espectaculo guardar(Espectaculo espectaculo) {

        if(espectaculoRepository.existsByNombre(espectaculo.getNombre())) {
            throw new RuntimeException("Ya existe un espectáculo con ese nombre");
        }

        return espectaculoRepository.save(espectaculo);
    }

    public List<Espectaculo> listarTodos() {
        return espectaculoRepository.findAll();
    }

    public Espectaculo buscarPorId(Long id) {
        return espectaculoRepository.findById(id).orElse(null);
    }
}
