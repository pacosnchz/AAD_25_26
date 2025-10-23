package com.paco.aad.application;

import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repostitory.ModuleRepository;
import com.paco.aad.repostitory.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentService implements CustomService<Student> {

    public final StudentRepository studentRepository;
    public final ModuleRepository moduleRepository;

//    public StudentService(Student student) {          ---para esto es el @RequiredArgsConstructor
//        this.student = student;
//    }

    /**
     * @param entity
     * @return
     */
    @Override
    public boolean validate(Student entity) {
        return !entity.getDni().isBlank() && !entity.getName().isBlank();
    }

    public Student createStudent(final Student student, List<Module> modules) {
        if (validate(student)) {
            student.setModules(modules);
            return studentRepository.create(student);
        }
        return null;
    }
}
