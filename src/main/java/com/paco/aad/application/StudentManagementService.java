package com.paco.aad.application;

import com.paco.aad.config.PostgresqlDriver;
import com.paco.aad.model.Enrollment;
import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repository.EnrollmentRepository;
import com.paco.aad.repository.ModuleRepository;
import com.paco.aad.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PostgresqlDriver postgresqlDriver;

    public StudentManagementService(
            StudentRepository studentRepository,
            ModuleRepository moduleRepository,
            EnrollmentRepository enrollmentRepository,
            PostgresqlDriver postgresqlDriver) {

        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.postgresqlDriver = postgresqlDriver;
    }

    // ------------------------------------------------------
    // CREATE STUDENT
    // ------------------------------------------------------
    public Student createStudent(Student student) {
        // Validaciones básicas
        if (student.getName() == null || student.getName().isBlank())
            throw new IllegalArgumentException("Name is required");

        if (student.getNif() == null || student.getNif().isBlank())
            throw new IllegalArgumentException("NIF is required");

        if (student.getEmail() == null || student.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");

        // Comprobar si existe ya un estudiante con ese NIF
        return studentRepository.findAll().stream()
                .filter(s -> s.getNif().equals(student.getNif()))
                .findFirst()
                .orElseGet(() -> studentRepository.insert(student));
    }

    // ------------------------------------------------------
    // CREATE MODULE
    // ------------------------------------------------------
    public Module createModule(Module module) {
        // Validaciones básicas
        if (module.getCode() == null || module.getCode().isBlank())
            throw new IllegalArgumentException("Module code is required");

        if (module.getName() == null || module.getName().isBlank())
            throw new IllegalArgumentException("Module name is required");

        // Comprobar duplicado por código
        return moduleRepository.findAll().stream()
                .filter(m -> m.getCode().equals(module.getCode()))
                .findFirst()
                .orElseGet(() -> moduleRepository.insert(module));
    }

    // ------------------------------------------------------
    // ENROLL STUDENT IN MODULE (con transacción)
    // ------------------------------------------------------
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {
        try {
            postgresqlDriver.beginTransaction();

            // Validar estudiante
            var student = studentRepository.findById(studentId);
            if (student == null)
                throw new IllegalArgumentException("Student not found: " + studentId);

            // Validar módulo
            var module = moduleRepository.findById(moduleId);
            if (module == null)
                throw new IllegalArgumentException("Module not found: " + moduleId);

            // Crear matrícula
            Enrollment enrollment = new Enrollment(
                    null,
                    student.getId(),
                    module.getId(),
                    LocalDate.now()
            );

            Enrollment created = enrollmentRepository.createEnrollment(enrollment, List.of(module));

            postgresqlDriver.commit();
            return created;

        } catch (Exception e) {
            try {
                postgresqlDriver.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed", ex);
            }
            throw new RuntimeException("Error enrolling student", e);
        }
    }
}
