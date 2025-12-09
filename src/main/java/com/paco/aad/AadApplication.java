package com.paco.aad;

import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.repository.ModuleRepository;
import com.paco.aad.repository.StudentRepository;
import com.paco.aad.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {

    private final StudentRepository studentRepo;
    private final ModuleRepository moduleRepo;
    private final EnrollmentService enrollmentService;

    public AadApplication(
            StudentRepository studentRepo,
            ModuleRepository moduleRepo,
            EnrollmentService enrollmentService
    ) {
        this.studentRepo = studentRepo;
        this.moduleRepo = moduleRepo;
        this.enrollmentService = enrollmentService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Student sofi = new Student(null, "NIF-" + System.nanoTime(), "Sofi", "sofia" + System.nanoTime() + "@mail.com");
        sofi = studentRepo.create(sofi);
        log.info("Alumno creado: {}", sofi);

        Module acceso = new Module(null, "0004", "Interfaces", 250);
        acceso = moduleRepo.create(acceso);
        log.info("Módulo creado: {}", acceso);

        // AHORA SI: matriculación con servicio (transaccional)
        enrollmentService.enrollStudentInModule(
                sofi.getId_alumno(),
                acceso.getId_modulo()
        );

        log.info("Alumno matriculado correctamente.");
    }
}
