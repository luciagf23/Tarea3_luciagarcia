package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

	@Query("SELECT a FROM Artista a LEFT JOIN FETCH a.especialidades")
	List<Artista> findAllWithEspecialidades();

	
	
}
