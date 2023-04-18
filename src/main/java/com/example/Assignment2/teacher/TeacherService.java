package com.example.Assignment2.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;


@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public Teacher findByUserName(String username) {
        return teacherRepository.findTeacherByUsername(username);
    }

    public Boolean isTeacherLoggedIn(Teacher teacher) {
        if (teacher == null) {
            return false;
        }
        return !teacher.getLoggedIn().equals(Boolean.FALSE);
    }

    public Pair<String, HttpStatus> invalidLogin() {
        return Pair.of("User is not authorized", HttpStatus.BAD_REQUEST);
    }

    public Boolean checkPasswordMatch(String password, String passwordHash) {
        return Base64.getEncoder().encodeToString(password.getBytes()).equals(passwordHash);
    }

    public Pair<String, HttpStatus> login(TeacherDTO teacherDTO) {
        Teacher foundTeacher = findByUserName(teacherDTO.getUsername());
        if (foundTeacher == null || !checkPasswordMatch(teacherDTO.getPassword(), foundTeacher.getPassword())) {
            return Pair.of("Username or password incorrect", HttpStatus.BAD_REQUEST);
        }
        if (isTeacherLoggedIn(foundTeacher)) {
            return Pair.of("User already logged in.", HttpStatus.BAD_REQUEST);
        }

        foundTeacher.setLoggedIn(Boolean.TRUE);
        teacherRepository.save(foundTeacher);
        return Pair.of("You have successfully logged in", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> logout(TeacherDTO teacherDTO) {
        Teacher foundTeacher = findByUserName(teacherDTO.getUsername());
        if (foundTeacher == null) {
            return Pair.of("Username is incorrect", HttpStatus.BAD_REQUEST);
        }
        if (!isTeacherLoggedIn(foundTeacher)) {
            return Pair.of("User already logged out.", HttpStatus.BAD_REQUEST);
        }

        foundTeacher.setLoggedIn(Boolean.FALSE);
        teacherRepository.save(foundTeacher);
        return Pair.of("You have successfully logged out", HttpStatus.OK);
    }


}
