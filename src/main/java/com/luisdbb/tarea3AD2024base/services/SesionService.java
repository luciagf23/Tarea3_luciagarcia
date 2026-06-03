package com.luisdbb.tarea3AD2024base.services;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Credencial;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;

@Service
public class SesionService {

    private Credencial usuarioActual;
    private Espectaculo espectaculoActual;

    public Credencial getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Credencial usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Espectaculo getEspectaculoActual() {
        return espectaculoActual;
    }

    public void setEspectaculoActual(Espectaculo espectaculoActual) {
        this.espectaculoActual = espectaculoActual;
    }
    
    public void cerrarSesion() {
        this.usuarioActual = null;
        this.espectaculoActual = null;
    }
    
    public boolean isLogueado() {
        return usuarioActual != null;
    }
    
    public String getRol() {
        if (usuarioActual == null) return "INVITADO";
        return usuarioActual.getRol().toString();
    }
}
