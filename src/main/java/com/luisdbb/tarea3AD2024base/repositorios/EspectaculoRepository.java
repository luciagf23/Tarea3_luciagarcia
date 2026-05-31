package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Espectaculo;

@Repository
public interface EspectaculoRepository
        extends JpaRepository<Espectaculo, Long> {

    Optional<Espectaculo> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}