package com.example.Assignment2;

import com.example.Assignment2.teacher.Teacher;
import com.example.Assignment2.teacher.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(TeacherRepository teacherRepository) {
        return args -> {
            Teacher teacher = new Teacher(
                    "Pelican",
                    "aaaa",
                    Boolean.FALSE
            );

            teacherRepository.saveAll(List.of(teacher));
        };
    }

}
