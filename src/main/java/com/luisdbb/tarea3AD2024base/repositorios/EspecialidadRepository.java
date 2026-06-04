package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdbb.tarea3AD2024base.modelo.Especialidad;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    Optional<Especialidad> findByNombre(String nombre);
}
