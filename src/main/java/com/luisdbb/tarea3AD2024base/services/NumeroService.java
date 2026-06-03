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
		validarNumero(numero);

		return numeroRepository.save(numero);
	}

	public List<Numero> listarTodos() {
		return numeroRepository.findAll();
	}

	public List<Numero> findByEspectaculo(Long espectaculoId) {
		return numeroRepository.findByEspectaculo(espectaculoId);
	}

	public Numero findByIdConArtistas(Long id) {
		return numeroRepository.findByIdConArtistas(id);
	}

	public void eliminar(Long id) {
		numeroRepository.deleteById(id);
	}

	private void validarNumero(Numero numero) {

		// Duración > 0
		if (numero.getDuracion() == null || numero.getDuracion() <= 0) {
			throw new RuntimeException("La duración debe ser mayor que 0");
		}

		validarOrden(numero);

	}

	private void validarOrden(Numero numero) {

		if (numero.getOrden() == null || numero.getOrden() <= 0) {
			throw new RuntimeException("El orden debe ser mayor que 0");
		}
		if (numero.getId() == null) {

			// Creación
			if (numeroRepository.existsByEspectaculoAndOrden(numero.getEspectaculo(), numero.getOrden())) {

				throw new RuntimeException("Ya existe un número con ese orden en el espectáculo");
			}

		} else {

			// Edición
			if (numeroRepository.existsByEspectaculoAndOrdenAndIdNot(numero.getEspectaculo(), numero.getOrden(),
					numero.getId())) {

				throw new RuntimeException("Ya existe un número con ese orden en el espectáculo");
			}
		}
	}

}
