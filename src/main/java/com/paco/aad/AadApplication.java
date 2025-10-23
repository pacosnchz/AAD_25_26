package com.paco.aad;

import com.paco.aad.application.StudentService;
import com.paco.aad.model.Module;
import com.paco.aad.model.Student;
import com.paco.aad.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class AadApplication implements CommandLineRunner {

    private final StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Student student = new Student("12A", "paco", "sanchez", "ciencia");
        Module module1 = new Module("MOD1", "Ciencia");
        Module module2 = new Module("MOD2", "Mates");
        List<Module> modules = List.of(module1, module2);
        Student create = studentService.createStudent(student, modules);
        if (create != null) {
            log.info("create: {}", create);
        } else {
            log.error(Constant.STUDENT_NOT_FOUND);
        }
    }
}
