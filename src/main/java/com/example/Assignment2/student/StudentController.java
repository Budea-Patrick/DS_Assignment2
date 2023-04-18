package com.example.Assignment2.student;

import com.example.Assignment2.laboratory.LaboratoryDTO;
import com.example.Assignment2.submission.SubmissionDTO;
import com.example.Assignment2.submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;
    private final SubmissionService submissionService;

    @Autowired
    public StudentController(StudentService studentService, SubmissionService submissionService) {
        this.studentService = studentService;
        this.submissionService = submissionService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair = studentService.login(studentDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair = studentService.logout(studentDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair = studentService.register(studentDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/labs")
    public ResponseEntity<?> findAllLabs(@RequestBody StudentDTO studentDTO) {
        Pair<?, HttpStatus> pair;
        Student student = studentService.findByUserName(studentDTO.getUsername());
        if (!studentService.isStudentLoggedIn(student)) {
            pair = studentService.invalidLogin();
        } else {
            pair = studentService.findAllLabs();
        }

        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/assignments")
    public ResponseEntity<?> findAssignmentByLab(@RequestBody LaboratoryDTO laboratoryDTO) {
        Pair<?, HttpStatus> pair = studentService.findAssignmentsByLab(laboratoryDTO);
        ;
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/submission/create")
    public ResponseEntity<?> createSubmission(@RequestBody SubmissionDTO submissionDTO) {
        Pair<?, HttpStatus> pair;
        Student student = studentService.findByUserName(submissionDTO.getRequester());
        if (!studentService.isStudentLoggedIn(student)) {
            pair = studentService.invalidLogin();
        } else {
            pair = submissionService.create(submissionDTO);
        }

        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

}
