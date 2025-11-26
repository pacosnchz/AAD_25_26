package com.paco.aad.repository;

import com.paco.aad.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Student> mapper = (rs, rowNum) ->
            new Student(
                    rs.getInt("id_alumno"),
                    rs.getString("nif"),
                    rs.getString("nombre"),
                    rs.getString("email")
            );

    public StudentRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Student create(Student s) {
        Integer id = jdbc.queryForObject(
                """
                        INSERT INTO alumno (nif, nombre, email)
                        VALUES (?, ?, ?)
                        RETURNING id_alumno
                        """,
                Integer.class,
                s.getNif(),
                s.getNombre(),
                s.getEmail()
        );

        s.setId_alumno(id);
        return s;
    }

    @Override
    public Student findById(Integer id) {
        return jdbc.queryForObject(
                "SELECT * FROM alumno WHERE id_alumno = ?",
                mapper,
                id
        );
    }

    @Override
    public void delete(Integer id) {
        jdbc.update("DELETE FROM alumno WHERE id_alumno = ?", id);
    }
}
