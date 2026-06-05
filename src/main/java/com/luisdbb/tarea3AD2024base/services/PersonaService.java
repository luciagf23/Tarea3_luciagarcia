package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Coordinacion;
import com.luisdbb.tarea3AD2024base.modelo.Persona;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.repositorios.ArtistaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.CoordinacionRepository;
import com.luisdbb.tarea3AD2024base.repositorios.PersonaRepository;

@Service
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private CoordinacionRepository coordinacionRepository;

	@Autowired
	private ArtistaRepository artistaRepository;

	public List<Artista> findAllArtistas() {
		return artistaRepository.findAll();
	}
	

	public Persona guardar(Persona persona) {

		persona.setEmail(persona.getEmail().toLowerCase());

		if (personaRepository.existsByEmail(persona.getEmail())) {
			throw new RuntimeException("Email ya registrado");
		}

		return personaRepository.save(persona);
	}

	public List<Coordinacion> findAllCoordinadores() {
		return personaRepository.findAllCoordinadores();
	}

	public List<Persona> listarTodas() {
		return personaRepository.findAll();
	}

	public Persona buscarPorId(Long id) {
		return personaRepository.findById(id).orElse(null);
	}

	public void eliminar(Long id) {
		personaRepository.deleteById(id);
	}

	public List<Persona> findAll() {
		 return personaRepository.findAllWithEspecialidades();
	}

	public void deleteInBatch(List<Persona> personas) {
		personaRepository.deleteAll(personas);
	}

	private void validarDatosPersona(Persona p) {
		if (p.getNombre() == null || p.getNombre().isBlank() || p.getEmail() == null || p.getEmail().isBlank()
				|| p.getNacionalidad() == null || p.getNacionalidad().isBlank()) {

			throw new RuntimeException("Faltan datos personales obligatorios");
		}
	}

}
