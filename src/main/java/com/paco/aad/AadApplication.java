package com.paco.aad;

import com.paco.aad.application.StudentManagementService;
import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {

    private final StudentManagementService studentManagementService;
    private final StudentRepository studentRepository;

    // Inyección por constructor (mejor práctica)
    public AadApplication(
            StudentManagementService studentManagementService,
            StudentRepository studentRepository
    ) {
        this.studentManagementService = studentManagementService;
        this.studentRepository = studentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Código EXACTO requerido por el enunciado
        Student valentin = new Student(null, "32902597Q", "Valentin",
                "valentin@gmail.com", "DAM", List.of());

        Module programacion = new Module(null, "0001", "Programación",
                250);

        // Crear entidades
        valentin = studentManagementService.createStudent(valentin);
        programacion = studentManagementService.createModule(programacion);

        // Matricular (con transacción)
        studentManagementService.enrollStudentInModule(
                valentin.getId(),
                programacion.getId()
        );

        // Eliminar al estudiante
        studentRepository.delete(valentin.getId());

        log.info("Prueba ACT_2_1 completada correctamente");
    }
}
