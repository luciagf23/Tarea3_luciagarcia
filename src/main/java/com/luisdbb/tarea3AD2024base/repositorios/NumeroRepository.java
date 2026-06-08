package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Artista;
import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;
import com.luisdbb.tarea3AD2024base.modelo.Numero;

@Repository
public interface NumeroRepository extends JpaRepository<Numero, Long> {

	@Query("""
			    SELECT DISTINCT n
			    FROM Numero n
			    LEFT JOIN FETCH n.artistas
			    WHERE n.espectaculo.id = :id
			    ORDER BY n.orden
			""")
	List<Numero> findByEspectaculoIdOrderByOrden(Long espectaculoId);

	@Query("""
			SELECT DISTINCT n
			FROM Numero n
			LEFT JOIN FETCH n.artistas
			WHERE n.espectaculo.id = :id
			ORDER BY n.orden
			""")
	List<Numero> findByEspectaculo(@Param("id") Long id);
	
	@Query("""
		    SELECT n 
		    FROM Numero n 
		    LEFT JOIN FETCH n.artistas 
		    WHERE n.id = :id
		""")
		Numero findByIdConArtistas(Long id);


	boolean existsByEspectaculoAndOrden(Espectaculo espectaculo, Integer orden);

	boolean existsByEspectaculoAndOrdenAndIdNot(Espectaculo espectaculo, Integer orden, Long id);

	long countByEspectaculoId(Long espectaculoId);
	
	List<Numero> findByArtistasId(Long artistaId);
	
	List<Numero> findByArtistasContains(Artista artista);
}
