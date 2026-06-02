package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.*;
import com.luisdbb.tarea3AD2024base.repositorios.*;

@Service
public class RegistroService {

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private CredencialRepository credencialRepository;

	// MÉTODO PRINCIPAL DE REGISTRO

	public Persona registrarPersona(Persona persona, Credencial credencial) {

		persona.setEmail(persona.getEmail().toLowerCase());

		validarDatosPersona(persona);

		// validar email solo al crear
		if (persona.getId() == null) {
			validarEmailUnico(persona.getEmail());
		}

		if (persona instanceof Artista a) {
			validarEspecialidades(a.getEspecialidades());
		}

		if (persona instanceof Coordinacion c) {
			validarCoordinacion(c);
		}

		// Validar credenciales
		validarCredenciales(credencial);

		// Normalizar username antes validar
		credencial.setUsername(credencial.getUsername().toLowerCase());

		// Validar username unico
		if (credencial.getId() == null) {
			if (credencialRepository.existsByUsername(credencial.getUsername())) {
				throw new RuntimeException("Username ya registrado");
			}
		}

		// Guardar persona
		Persona guardada = personaRepository.save(persona);

		// Guardar credencial
		credencial.setPersona(guardada);
		guardada.setCredencial(credencial);
		
		credencialRepository.save(credencial);

		return guardada;
	}

	// VALIDACIONES
	private void validarDatosPersona(Persona p) {
		if (p.getNombre() == null || p.getNombre().isBlank() || p.getEmail() == null || p.getEmail().isBlank()
				|| p.getNacionalidad() == null || p.getNacionalidad().isBlank()) {

			throw new RuntimeException("Faltan datos personales obligatorios");
		}
	}

	private void validarEmailUnico(String email) {
		if (personaRepository.existsByEmail(email.toLowerCase())) {
			throw new RuntimeException("Email ya registrado");
		}
	}

	private void validarCredenciales(Credencial cred) {

		String username = cred.getUsername();
		String password = cred.getPassword();

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

		cred.setUsername(username.toLowerCase());
	}

	private void validarEspecialidades(java.util.Set<Especialidad> especialidades) {
		if (especialidades == null || especialidades.isEmpty()) {
			throw new RuntimeException("El artista debe tener al menos una especialidad");
		}
	}

	private void validarCoordinacion(Coordinacion c) {
		if (c.isSenior() && c.getFechaSenior() == null) {
			throw new RuntimeException("Debe indicar la fecha desde que es senior");
		}
	}
}
