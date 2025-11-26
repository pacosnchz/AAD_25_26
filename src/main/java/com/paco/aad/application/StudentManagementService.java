package com.paco.aad.application;

import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repository.EnrollmentRepository;
import com.paco.aad.repository.ModuleRepository;
import com.paco.aad.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentManagementService(
            StudentRepository studentRepository,
            ModuleRepository moduleRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Student createStudent(Student s) {
        return studentRepository.create(s);
    }

    public Module createModule(Module m) {
        return moduleRepository.create(m);
    }


    @Transactional
    public void enrollStudentInModule(Integer studentId, Integer moduleId) {
        enrollmentRepository.enroll(studentId, moduleId);
    }
}
