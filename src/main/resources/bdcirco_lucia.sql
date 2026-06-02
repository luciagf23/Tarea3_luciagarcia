-- ============================================================
-- BASE DE DATOS CIRCO - TAREA 3 (Lucía)
-- Adaptado EXACTAMENTE a tus entidades JPA
-- ============================================================

DROP DATABASE IF EXISTS bdcirco_lucia;
CREATE DATABASE bdcirco_lucia;
USE bdcirco_lucia;

-- ============================================================
-- PERSONAS (tabla base de la herencia JOINED)
-- ============================================================

INSERT INTO persona (id, nombre, email, nacionalidad)
VALUES
(1, 'Administrador General', 'admin@circo.com', 'España'),
(2, 'Laura Gómez', 'laura@circo.com', 'España'),
(3, 'Carlos Ruiz', 'carlos@circo.com', 'España'),
(4, 'Ana Torres', 'ana@circo.com', 'México'),
(5, 'Pedro López', 'pedro@circo.com', 'Argentina'),
(6, 'Marta Díaz', 'marta@circo.com', 'España');

-- ============================================================
-- CREDENCIALES
-- ============================================================

INSERT INTO credencial (id, username, password, rol, persona_id)
VALUES
(1, 'admin', 'admin', 'ADMIN', 1),
(2, 'laura', 'clave123', 'COORDINACION', 2),
(3, 'carlos', 'clave123', 'COORDINACION', 3),
(4, 'ana', 'ana123', 'ARTISTA', 4),
(5, 'pedro', 'pedro123', 'ARTISTA', 5),
(6, 'marta', 'marta123', 'ARTISTA', 6);

-- ============================================================
-- COORDINACIÓN (tabla hija de Persona)
-- ============================================================

INSERT INTO coordinacion (id, senior, fecha_senior)
VALUES
(2, FALSE, NULL),              -- Laura (no senior)
(3, TRUE, '2022-01-01');       -- Carlos (senior)

-- ============================================================
-- ARTISTAS (tabla hija de Persona)
-- ============================================================

INSERT INTO artista (id, apodo)
VALUES
(4, 'La Fiera'),
(5, NULL),
(6, 'Volátil');

-- ============================================================
-- ESPECIALIDADES (tabla generada por @ElementCollection)
-- ============================================================

INSERT INTO artista_especialidades (artista_id, especialidades)
VALUES
(4, 'ACROBACIA'),
(4, 'MAGIA'),
(5, 'HUMOR'),
(6, 'EQUILIBRISMO'),
(6, 'MALABARISMO');

-- ============================================================
-- ESPECTÁCULOS
-- ============================================================

INSERT INTO espectaculo (id, nombre, fecha_inicio, fecha_fin, coordinador_id
