DROP TABLE IF EXISTS matricula CASCADE;
DROP TABLE IF EXISTS modulo CASCADE;
DROP TABLE IF EXISTS alumno CASCADE;

CREATE TABLE alumno (
    id_alumno SERIAL PRIMARY KEY,
    nif VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE modulo (
    id_modulo SERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    horas INT NOT NULL
);

CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    id_alumno INT NOT NULL REFERENCES alumno(id_alumno) ON DELETE CASCADE,
    id_modulo INT NOT NULL REFERENCES modulo(id_modulo),
    fecha DATE NOT NULL
);

-- ==========================================================
--  Procedimiento almacenado desde 02_procedures.sql
--  Integrado en schema.sql para ejecución automática
-- ==========================================================

CREATE OR REPLACE FUNCTION count_enrollments(student_id INT)
RETURNS INT AS $$
DECLARE
    total INT;
BEGIN
    SELECT COUNT(*)
    INTO total
    FROM matricula
    WHERE id_alumno = student_id;

    RETURN total;
END;
$$ LANGUAGE plpgsql;
