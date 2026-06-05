package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Coordinacion;
import com.luisdbb.tarea3AD2024base.modelo.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	boolean existsByEmail(String email);

	@Query("SELECT p FROM Persona p WHERE TYPE(p) = Coordinacion")
	List<Coordinacion> findAllCoordinadores();

	@Query("""
			    SELECT p FROM Persona p
			    LEFT JOIN FETCH TREAT(p AS Artista).especialidades
			""")
	List<Persona> findAllWithEspecialidades();

	List<Artista> findAllByOrderByIdAsc();
}
