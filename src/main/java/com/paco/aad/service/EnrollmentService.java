package com.paco.aad.service;

import com.paco.aad.model.Enrollment;
import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repository.EnrollmentRepository;
import com.paco.aad.repository.ModuleRepository;
import com.paco.aad.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepo;
    private final StudentRepository studentRepo;
    private final ModuleRepository moduleRepo;

    public EnrollmentService(EnrollmentRepository enrollmentRepo,
                             StudentRepository studentRepo,
                             ModuleRepository moduleRepo) {
        this.enrollmentRepo = enrollmentRepo;
        this.studentRepo = studentRepo;
        this.moduleRepo = moduleRepo;
    }

    @Transactional
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {

        // Validaciones sencillas
        Student s = studentRepo.findById(studentId);
        if (s == null) {
            throw new RuntimeException("El alumno no existe");
        }

        Module m = moduleRepo.findById(moduleId);
        if (m == null) {
            throw new RuntimeException("El módulo no existe");
        }

        // Operación real
        return enrollmentRepo.enroll(studentId, moduleId);
    }
}
