package com.example.Assignment2.teacher;

import com.example.Assignment2.assignment.AssignmentDTO;
import com.example.Assignment2.assignment.AssignmentService;
import com.example.Assignment2.attendance.AttendanceDTO;
import com.example.Assignment2.attendance.AttendanceService;
import com.example.Assignment2.laboratory.LaboratoryDTO;
import com.example.Assignment2.laboratory.LaboratoryService;
import com.example.Assignment2.student.StudentDTO;
import com.example.Assignment2.student.StudentService;
import com.example.Assignment2.submission.SubmissionDTO;
import com.example.Assignment2.submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final LaboratoryService laboratoryService;
    private final AttendanceService attendanceService;
    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;


    @Autowired
    public TeacherController(TeacherService teacherService, StudentService studentService, LaboratoryService laboratoryService, AttendanceService attendanceService, AssignmentService assignmentService, SubmissionService submissionService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.laboratoryService = laboratoryService;
        this.attendanceService = attendanceService;
        this.assignmentService = assignmentService;
        this.submissionService = submissionService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody TeacherDTO teacherDTO) {
        Pair<String, HttpStatus> pair = teacherService.login(teacherDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TeacherDTO teacherDTO) {
        Pair<String, HttpStatus> pair = teacherService.logout(teacherDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/student/create")
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = studentService.create(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/student/delete")
    public ResponseEntity<String> deleteStudent(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = studentService.delete(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/student/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = studentService.update(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/student/find")
    public ResponseEntity<?> findStudent(@RequestBody StudentDTO studentDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = studentService.findStudent(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/student/all")
    public ResponseEntity<?> findAllStudents(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = studentService.findAllStudents();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/laboratory/create")
    public ResponseEntity<String> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(laboratoryDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = laboratoryService.create(laboratoryDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/laboratory/delete")
    public ResponseEntity<String> deleteLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(laboratoryDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = laboratoryService.delete(laboratoryDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/laboratory/update")
    public ResponseEntity<String> updateLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(laboratoryDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = laboratoryService.update(laboratoryDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/laboratory/find")
    public ResponseEntity<?> findLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(laboratoryDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = laboratoryService.findLaboratory(laboratoryDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/laboratory/all")
    public ResponseEntity<?> findAllLaboratories(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = laboratoryService.findAllLaboratories();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/attendance/create")
    public ResponseEntity<String> createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = attendanceService.create(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/attendance/delete")
    public ResponseEntity<String> deleteAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = attendanceService.delete(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/attendance/update")
    public ResponseEntity<String> updateAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = attendanceService.update(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/attendance/find")
    public ResponseEntity<?> findAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = attendanceService.find(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/attendance/all")
    public ResponseEntity<?> findAllAttendances(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = attendanceService.findAll();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/assignment/create")
    public ResponseEntity<String> createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = assignmentService.create(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/assignment/delete")
    public ResponseEntity<String> deleteAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = assignmentService.delete(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/assignment/update")
    public ResponseEntity<String> updateAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = assignmentService.update(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/assignment/find")
    public ResponseEntity<?> findAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = assignmentService.findAssignment(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/assignment/all")
    public ResponseEntity<?> findAllAssignments(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = assignmentService.findAllAssignments();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/submission/grade")
    public ResponseEntity<String> findAllAssignments(@RequestBody SubmissionDTO submissionDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(submissionDTO.getRequester());
        if (!teacherService.isTeacherLoggedIn(teacher)) {
            pair = teacherService.invalidLogin();
        } else {
            pair = submissionService.grade(submissionDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }


}
