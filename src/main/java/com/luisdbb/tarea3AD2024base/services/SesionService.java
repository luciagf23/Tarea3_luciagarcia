package com.luisdbb.tarea3AD2024base.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Credencial;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;

@Service
@Scope("singleton")
public class SesionService {

	private Credencial usuarioActual;
	private Espectaculo espectaculoActual;
	private Artista artistaActual;

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

	public Artista getArtistaActual() {
		return artistaActual;
	}

	public void setArtistaActual(Artista artistaActual) {
		this.artistaActual = artistaActual;
	}

	public void cerrarSesion() {
		this.usuarioActual = null;
		this.espectaculoActual = null;
	}

	public boolean isLogueado() {
		return usuarioActual != null;
	}

	public String getRol() {
		if (usuarioActual == null)
			return "INVITADO";
		return usuarioActual.getRol().toString();
	}
}
