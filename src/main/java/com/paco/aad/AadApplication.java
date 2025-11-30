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
    public void run(String... args) throws Exception {

        // Crear estudiante
        Student pako = new Student(null, "49108229E", "Pako", "pako@gmail.com");
        pako = studentRepo.create(pako);
        log.info("Alumno creado: {}", pako);

        // Crear módulo
        Module ipe = new Module(null, "0005", "IPE", 250);
        ipe = moduleRepo.create(ipe);
        log.info("Módulo creado: {}", ipe);

        // Matricular (ahora vía servicio transaccional)
        enrollmentService.enrollStudentInModule(
                pako.getId_alumno(),
                ipe.getId_modulo()
        );
        log.info("Alumno matriculado en el módulo (vía servicio)");

        log.info("Prueba ACT_2_2 completada correctamente");
    }
}
