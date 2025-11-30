-- ================================================
--  Datos iniciales para pruebas en la Actividad 2_2
-- ================================================

-- Insertar alumnos
INSERT INTO alumno (nif, nombre, email)
VALUES
    ('12345678A', 'Juan Pérez', 'juan.perez@example.com'),
    ('87654321B', 'María López', 'maria.lopez@example.com');

-- Insertar módulos
INSERT INTO modulo (codigo, nombre, horas)
VALUES
    ('PROG', 'Programación', 200),
    ('BD', 'Bases de Datos', 180);

-- Insertar matrículas de ejemplo
INSERT INTO matricula (id_alumno, id_modulo, fecha)
VALUES
    (1, 1, CURRENT_DATE),
    (1, 2, CURRENT_DATE),
    (2, 1, CURRENT_DATE);
