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
    	validarCredenciales(credencial);

        if (credencialRepository.existsByUsername(credencial.getUsername())) {
            throw new RuntimeException("Username ya registrado");
        
        }

        return credencialRepository.save(credencial);
    }

    public Credencial findByUsername(String username) {
        return credencialRepository.findByUsername(username).orElse(null);
    }
    
    
    private void validarCredenciales(Credencial credencial) {

        String username = credencial.getUsername();
        String password = credencial.getPassword();

        if (username == null || password == null) {
            throw new RuntimeException("Usuario y contraseña obligatorios");
        }

        if (username.contains(" ") || password.contains(" ")) {
            throw new RuntimeException("Usuario y contraseña no pueden contener espacios");
        }

        if (username.length() <= 2 || password.length() <= 2) {
            throw new RuntimeException("Usuario y contraseña deben tener más de 2 caracteres");
        }

        if (!username.matches("[a-zA-Z]+")) {
            throw new RuntimeException("El usuario solo puede contener letras sin tildes");
        }

        // normalizar
        credencial.setUsername(username.toLowerCase());
    }

    
}
