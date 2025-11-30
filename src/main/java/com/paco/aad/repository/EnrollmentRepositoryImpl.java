package com.paco.aad.repository;

import com.paco.aad.model.Enrollment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcCall countEnrollmentsFn;

    private final RowMapper<Enrollment> mapper = (rs, rowNum) ->
            new Enrollment(
                    rs.getInt("id"),
                    rs.getInt("id_alumno"),
                    rs.getInt("id_modulo"),
                    rs.getDate("fecha").toLocalDate()
            );

    public EnrollmentRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.countEnrollmentsFn = new SimpleJdbcCall(jdbc)
                .withFunctionName("count_enrollments");
    }

    @Override
    public Enrollment enroll(Integer studentId, Integer moduleId) {

        Integer id = jdbc.queryForObject(
                """
                            INSERT INTO matricula (id_alumno, id_modulo, fecha)
                            VALUES (?, ?, ?)
                            RETURNING id
                        """,
                Integer.class,
                studentId,
                moduleId,
                LocalDate.now()
        );

        return new Enrollment(
                id,
                studentId,
                moduleId,
                LocalDate.now()
        );
    }

    @Override
    public int countEnrollments(Integer studentId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("student_id", studentId);

        return countEnrollmentsFn.executeFunction(Integer.class, params);
    }
}
