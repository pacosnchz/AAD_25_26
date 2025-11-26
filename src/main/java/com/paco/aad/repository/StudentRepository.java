package com.paco.aad.repository;

import com.paco.aad.model.Student;

public interface StudentRepository {

    Student create(Student s);

    Student findById(Integer id);

    void delete(Integer id);
}
