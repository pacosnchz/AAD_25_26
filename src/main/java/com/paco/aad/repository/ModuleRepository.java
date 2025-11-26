package com.paco.aad.repository;

import com.paco.aad.model.Module;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ModuleRepository {

    private final DataSource dataSource;

    public ModuleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Module insert(Module m) {
        String sql = "INSERT INTO modulo (codigo, nombre, horas) VALUES (?, ?, ?) RETURNING id_modulo";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, m.getCode());
            pst.setString(2, m.getName());
            pst.setInt(3, m.getHours());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                m.setId(rs.getInt(1));
            }
            return m;

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting module", e);
        }
    }

    public List<Module> findAll() {
        List<Module> list = new ArrayList<>();
        String sql = "SELECT * FROM modulo";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(mapModule(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all modules", e);
        }
        return list;
    }

    public Module findById(int id) {
        String sql = "SELECT * FROM modulo WHERE id_modulo = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return mapModule(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding module by id", e);
        }
        return null;
    }

    public boolean update(Module m) {
        String sql = "UPDATE modulo SET codigo = ?, nombre = ?, horas = ? WHERE id_modulo = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, m.getCode());
            pst.setString(2, m.getName());
            pst.setInt(3, m.getHours());
            pst.setInt(4, m.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating module", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM modulo WHERE id_modulo = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting module", e);
        }
    }

    private Module mapModule(ResultSet rs) throws SQLException {
        Module m = new Module();
        m.setId(rs.getInt("id_modulo"));
        m.setCode(rs.getString("codigo"));
        m.setName(rs.getString("nombre"));
        m.setHours(rs.getInt("horas"));
        return m;
    }
}
