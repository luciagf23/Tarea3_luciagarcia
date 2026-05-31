package com.luisdbb.tarea3AD2024base.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credencial;
import com.luisdbb.tarea3AD2024base.repositorios.CredencialRepository;

@Service
public class LoginService {

    @Autowired
    private CredencialRepository credencialRepository;

    private Credencial usuarioActual;

    public boolean login(String username, String password) {

        Optional<Credencial> credencial =
                credencialRepository.findByUsername(username);

        if (credencial.isPresent()
                && credencial.get().getPassword().equals(password)) {

            usuarioActual = credencial.get();
            return true;
        }

        return false;
    }

    public void logout() {
        usuarioActual = null;
    }

    public Credencial getUsuarioActual() {
        return usuarioActual;
    }

    public boolean haySesion() {
        return usuarioActual != null;
    }
}
