package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credencial;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialRepository;

@Service
public class CredencialService {

    @Autowired
    private CredencialRepository credencialRepository;

    public Credencial guardar(Credencial credencial) {

        if (credencialRepository.existsByUsername(credencial.getUsername())) {
            throw new RuntimeException("Username ya registrado");
        }

        return credencialRepository.save(credencial);
    }

    public Credencial findByUsername(String username) {
        return credencialRepository.findByUsername(username).orElse(null);
    }
}
