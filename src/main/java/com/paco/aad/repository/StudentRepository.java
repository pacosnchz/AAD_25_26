package com.paco.aad.repository;

import com.paco.aad.model.Student;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    private final DataSource dataSource;

    public StudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Student insert(Student s) {
        String sql = "INSERT INTO alumno (nif, nombre, email) VALUES (?, ?, ?) RETURNING id_alumno";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, s.getNif());
            pst.setString(2, s.getName());
            pst.setString(3, s.getEmail());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                s.setId(rs.getInt(1));
            }
            return s;

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting student", e);
        }
    }

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM alumno";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all students", e);
        }
        return list;
    }

    public Student findById(int id) {
        String sql = "SELECT * FROM alumno WHERE id_alumno = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return mapStudent(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding student by id", e);
        }
        return null;
    }

    public boolean update(Student s) {
        String sql = "UPDATE alumno SET nif = ?, nombre = ?, email = ? WHERE id_alumno = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, s.getNif());
            pst.setString(2, s.getName());
            pst.setString(3, s.getEmail());
            pst.setInt(4, s.getId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM alumno WHERE id_alumno = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student", e);
        }
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id_alumno"));
        s.setNif(rs.getString("nif"));
        s.setName(rs.getString("nombre"));
        s.setEmail(rs.getString("email"));
        s.setModules(List.of()); // sin cargar módulos todavía
        return s;
    }
}
