package com.paco.aad.repository;

import com.paco.aad.model.Enrollment;

public interface EnrollmentRepository {

    Enrollment enroll(Integer studentId, Integer moduleId);

    int countEnrollments(Integer studentId);   // llamada a la función almacenada
}
