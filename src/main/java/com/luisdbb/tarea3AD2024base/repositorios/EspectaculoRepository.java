package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;

@Repository
public interface EspectaculoRepository
        extends JpaRepository<Espectaculo, Long> {

    Optional<Espectaculo> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
    
    boolean existsByNombreAndFechaInicio(String nombre, LocalDate fechaInicio);

    @Query("""
    	       SELECT DISTINCT e
    	       FROM Espectaculo e
    	       LEFT JOIN FETCH e.numeros n
    	       LEFT JOIN FETCH n.artistas
    	       WHERE e.id = :id
    	       """)
    	Espectaculo findByIdWithNumerosAndArtistas(@Param("id") Long id);

}