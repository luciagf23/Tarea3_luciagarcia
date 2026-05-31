package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Credencial;

@Repository
public interface CredencialRepository
        extends JpaRepository<Credencial, Long> {

    Optional<Credencial> findByUsername(String username);

    boolean existsByUsername(String username);
}
