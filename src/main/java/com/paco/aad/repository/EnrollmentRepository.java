package com.paco.aad.repository;

import com.paco.aad.model.Enrollment;

public interface EnrollmentRepository {

    Enrollment enroll(Integer studentId, Integer moduleId);
}
