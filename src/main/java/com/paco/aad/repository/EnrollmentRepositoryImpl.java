package com.paco.aad.repository;

import com.paco.aad.model.Enrollment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Enrollment> mapper = (rs, rowNum) ->
            new Enrollment(
                    rs.getInt("id_alumno"),
                    rs.getInt("id_modulo"),
                    rs.getDate("fecha").toLocalDate()
            );

    public EnrollmentRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Enrollment enroll(Integer studentId, Integer moduleId) {

        jdbc.update(
                """
                        INSERT INTO matricula (id_alumno, id_modulo, fecha)
                        VALUES (?, ?, ?)
                        """,
                studentId,
                moduleId,
                LocalDate.now()
        );

        return new Enrollment(
                studentId,
                moduleId,
                LocalDate.now()
        );
    }
}
