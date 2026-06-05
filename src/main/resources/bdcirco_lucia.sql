-- Base de datos limpia
DROP DATABASE IF EXISTS bdcirco_luciagarcia;
CREATE DATABASE bdcirco_luciagarcia;
USE bdcirco_luciagarcia;

-- PERSONAS
INSERT INTO persona (id, nombre, email, nacionalidad, dtype)
VALUES
(1, 'Luis García', 'luis@circo.com', 'española', 'COORDINACION'),
(2, 'María López', 'maria@circo.com', 'española', 'ARTISTA'),
(3, 'Pedro Ruiz', 'pedro@circo.com', 'española', 'ARTISTA'),
(4, 'Ana Martín', 'ana@circo.com', 'francesa', 'ARTISTA'),
(5, 'Carlos Díaz', 'carlos@circo.com', 'italiana', 'ARTISTA');

-- COORDINACION
INSERT INTO coordinacion (id, senior, fecha_senior)
VALUES
(1, TRUE, '2020-01-15');

-- ARTISTAS
INSERT INTO artista (id, apodo)
VALUES
(2, NULL),
(3, 'El Gran Pedro'),
(4, NULL),
(5, 'Carlitos');

-- ESPECIALIDADES
INSERT INTO artista_especialidades (artista_id, especialidades) VALUES
(3, 0),
(3, 3),
(4, 1),
(4, 2),
(5, 4);

-- CREDENCIALES
INSERT INTO credencial (username, password, rol, persona_id)
VALUES
('luisgarcia', '1234', 'COORDINACION', 1),
('marialopez', '1234', 'ARTISTA', 2),
('pedroruiz', '1234', 'ARTISTA', 3),
('anamartin', '1234', 'ARTISTA', 4),
('carlosdiaz', '1234', 'ARTISTA', 5);

-- ESPECTÁCULOS
INSERT INTO espectaculo (id, nombre, fecha_inicio, fecha_fin, coordinador_id)
VALUES
(1, 'El Gran Circo', '2026-06-01', '2026-08-31', 1),
(2, 'Noche Mágica', '2026-09-01', '2026-11-30', 1);

-- NÚMEROS
INSERT INTO numero (id, nombre, duracion, orden, espectaculo_id)
VALUES
(1, 'Acrobacias Aéreas', 15.0, 1, 1),
(2, 'El Mago Asombroso', 20.5, 2, 1),
(3, 'Equilibrio Extremo', 10.0, 3, 1),
(4, 'Humor en Escena', 12.5, 1, 2),
(5, 'Magia y Misterio', 18.0, 2, 2),
(6, 'Malabares de Fuego', 15.5, 3, 2);

-- ARTISTAS EN NÚMEROS
INSERT INTO numero_artistas (numero_id, artista_id)
VALUES
(1, 3),
(1, 5),
(2, 4),
(3, 3),
(4, 4),
(5, 4),
(6, 5);
