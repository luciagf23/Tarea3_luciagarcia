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

		validarNombreUnico(espectaculo);
		validarNombreYFecha(espectaculo);
		validarFechas(espectaculo);
		validarDuracionMaxima(espectaculo);
		validarCoordinador(espectaculo);

		return espectaculoRepository.save(espectaculo);
	}

	public void eliminar(Long id) {
		espectaculoRepository.deleteById(id);
	}

	// Validaciones
	private void validarNombreUnico(Espectaculo e) {

		// Si es edicion
		if (e.getId() != null)
			return;

		if (espectaculoRepository.existsByNombre(e.getNombre())) {
			throw new RuntimeException("Ya existe un espectáculo con ese nombre");
		}

	}

	private void validarNombreYFecha(Espectaculo e) {
		if (espectaculoRepository.existsByNombreAndFechaInicio(e.getNombre(), e.getFechaInicio())) {
			throw new RuntimeException("Ya existe un espectáculo con ese nombre en esa fecha");
		}
	}

	private void validarFechas(Espectaculo e) {
		if (e.getFechaInicio().isAfter(e.getFechaFin())) {
			throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin");
		}
	}

	private void validarDuracionMaxima(Espectaculo e) {
		if (e.getFechaInicio().plusYears(1).isBefore(e.getFechaFin())) {
			throw new RuntimeException("El espectáculo no puede durar más de 1 año");
		}
	}

	private void validarCoordinador(Espectaculo e) {
		if (e.getCoordinador() == null) {
			throw new RuntimeException("Debe asignarse un coordinador");
		}
	}

	public List<Espectaculo> listarTodos() {
		return espectaculoRepository.findAll();
	}

	public Espectaculo buscarPorId(Long id) {
		return espectaculoRepository.findById(id).orElse(null);
	}

	public boolean existsByNombre(String nombre) {
		return espectaculoRepository.existsByNombre(nombre);
	}
}
