package com.example.Assignment2.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public Student findStudentByFirstNameAndLastName(String firstName, String lastName);

    public Student findStudentById(int id);

    public Student findStudentByToken(String token);

    public Student findStudentByUsername(String username);

}
