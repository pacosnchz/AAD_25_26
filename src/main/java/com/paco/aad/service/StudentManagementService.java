package com.paco.aad.service;

import com.paco.aad.model.Enrollment;
import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repository.EnrollmentRepository;
import com.paco.aad.repository.ModuleRepository;
import com.paco.aad.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    // =========================
    // STUDENTS
    // =========================

    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // =========================
    // MODULES
    // =========================

    @Transactional
    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    // =========================
    // ENROLLMENTS
    // =========================

    @Transactional
    public Enrollment enrollStudent(Long studentId, Long moduleId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setModule(module);
        enrollment.setEnrollmentDate(LocalDate.now());

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    //OBLIGATORIO para contar las matriculas
    public int countEnrollments(Long studentId) {
        return enrollmentRepository.countByStudentId(studentId);
    }

    /**
     * Llamada a procedimiento almacenado (función en PostgreSQL).
     */
    public Double getAverageGradeByModule(Long moduleId) {
        return enrollmentRepository.avgGradeByModule(moduleId);
    }
}
