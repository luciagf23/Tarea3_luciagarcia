package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Numero;
import com.luisdbb.tarea3AD2024base.repositorios.NumeroRepository;

@Service
public class NumeroService {

    @Autowired
    private NumeroRepository numeroRepository;

    public Numero guardar(Numero numero) {
        return numeroRepository.save(numero);
    }

    public List<Numero> listarTodos() {
        return numeroRepository.findAll();
    }
}
