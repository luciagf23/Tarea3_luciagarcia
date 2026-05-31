package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Persona;

@Repository
public interface PersonaRepository
        extends JpaRepository<Persona, Long> {

    boolean existsByEmail(String email);
}
