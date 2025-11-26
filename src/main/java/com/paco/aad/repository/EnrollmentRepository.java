package com.paco.aad.repository;

import com.paco.aad.config.PostgresqlDriver;
import com.paco.aad.model.Enrollment;
import com.paco.aad.model.Module;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnrollmentRepository {

    private final DataSource dataSource;
    private final PostgresqlDriver postgresqlDriver;

    public EnrollmentRepository(DataSource dataSource, PostgresqlDriver postgresqlDriver) {
        this.dataSource = dataSource;
        this.postgresqlDriver = postgresqlDriver;
    }

    // -------------------------
    // CREATE ENROLLMENT (con transacción)
    // -------------------------
    public Enrollment createEnrollment(Enrollment e, List<Module> modules) throws SQLException {
        String sql = "INSERT INTO matricula (id_alumno, id_modulo, fecha) VALUES (?, ?, ?)";

        Connection con = postgresqlDriver.getConnection(); // conexión transaccional

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, e.getStudentId());
            pst.setInt(2, e.getModuleId());
            pst.setDate(3, Date.valueOf(LocalDate.now()));

            pst.executeUpdate();
            return e;

        } catch (SQLException ex) {
            throw new RuntimeException("Error creating enrollment", ex);
        }
    }

    // -------------------------
    // FIND ALL
    // -------------------------
    public List<Enrollment> findAll() {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM matricula";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(mapEnrollment(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all enrollments", e);
        }
    }

    // -------------------------
    // FIND BY STUDENT
    // -------------------------
    public List<Enrollment> findByStudent(int studentId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM matricula WHERE id_alumno = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(mapEnrollment(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding enrollments by student", e);
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    public boolean delete(int studentId, int moduleId) {
        String sql = "DELETE FROM matricula WHERE id_alumno = ? AND id_modulo = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, studentId);
            pst.setInt(2, moduleId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting enrollment", e);
        }
    }

    // -------------------------
    // COUNT ENROLLMENTS (llamada a la función PL/pgSQL)
    // -------------------------
    public int countEnrollments(int studentId) {
        String call = "{ ? = call count_enrollments(?) }";

        try (Connection con = dataSource.getConnection();
             CallableStatement cs = con.prepareCall(call)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, studentId);
            cs.execute();

            return cs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("Error calling count_enrollments()", e);
        }
    }

    // -------------------------
    // MAP ENROLLMENT
    // -------------------------
    private Enrollment mapEnrollment(ResultSet rs) throws SQLException {
        return new Enrollment(
                null,
                rs.getInt("id_alumno"),
                rs.getInt("id_modulo"),
                rs.getDate("fecha").toLocalDate()
        );
    }
}
