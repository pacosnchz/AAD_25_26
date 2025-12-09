-- ==========================================================
--  Datos de ejemplo para pruebas iniciales
-- ==========================================================

-- Alumnos
INSERT INTO alumno (nif, nombre, email)
VALUES
    ('00000001A', 'Alumno Uno', 'alumno1@example.com'),
    ('00000002B', 'Alumno Dos', 'alumno2@example.com');

-- Módulos
INSERT INTO modulo (codigo, nombre, horas)
VALUES
    ('M001', 'Programación', 200),
    ('M002', 'Bases de Datos', 150);

-- Matrículas iniciales
INSERT INTO matricula (id_alumno, id_modulo, fecha)
VALUES
    (1, 1, CURRENT_DATE),
    (1, 2, CURRENT_DATE),
    (2, 1, CURRENT_DATE);
