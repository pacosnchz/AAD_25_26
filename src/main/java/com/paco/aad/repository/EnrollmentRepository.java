package com.paco.aad.repository;

import com.paco.aad.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // JPQL: matrículas con nota mayor o igual a un valor
    @Query("SELECT e FROM Enrollment e WHERE e.finalGrade >= :minGrade")
    List<Enrollment> findByMinFinalGrade(@Param("minGrade") Double minGrade);

    // JPQL: matrículas de un estudiante
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentId(@Param("studentId") Long studentId);

    // JPQL: contar matrículas de un estudiante (OBLIGATORIO en el enunciado)
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.student.id = :studentId")
    int countByStudentId(@Param("studentId") Long studentId);

    // Procedimiento almacenado (función en PostgreSQL)
    @Procedure("avg_grade_by_module")
    Double avgGradeByModule(Long moduleId);
}
