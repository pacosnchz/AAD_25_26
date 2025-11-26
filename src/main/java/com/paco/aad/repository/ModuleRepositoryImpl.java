package com.paco.aad.repository;

import com.paco.aad.model.Module;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ModuleRepositoryImpl implements ModuleRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Module> mapper = (rs, rowNum) ->
            new Module(
                    rs.getInt("id_modulo"),
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("horas")
            );

    public ModuleRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Module create(Module m) {

        Integer id = jdbc.queryForObject(
                """
                        INSERT INTO modulo (codigo, nombre, horas)
                        VALUES (?, ?, ?)
                        RETURNING id_modulo
                        """,
                Integer.class,
                m.getCodigo(),
                m.getNombre(),
                m.getHoras()
        );

        m.setId_modulo(id);
        return m;
    }

    @Override
    public Module findById(Integer id) {
        return jdbc.queryForObject(
                "SELECT * FROM modulo WHERE id_modulo = ?",
                mapper,
                id
        );
    }

    @Override
    public void delete(Integer id) {
        jdbc.update("DELETE FROM modulo WHERE id_modulo = ?", id);
    }
}
