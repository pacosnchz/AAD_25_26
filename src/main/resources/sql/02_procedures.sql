DO $block$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_proc
        WHERE proname = 'count_enrollments'
    ) THEN

        CREATE OR REPLACE FUNCTION count_enrollments(student_id INT)
        RETURNS INT AS $func$
        DECLARE
            total INT;
        BEGIN
            SELECT COUNT(*)
            INTO total
            FROM matricula
            WHERE id_alumno = student_id;

            RETURN total;
        END;
        $func$ LANGUAGE plpgsql;

    END IF;
END
$block$;
