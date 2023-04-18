package com.example.Assignment2.student;

import com.example.Assignment2.assignment.Assignment;
import com.example.Assignment2.assignment.AssignmentDTO;
import com.example.Assignment2.assignment.AssignmentRepository;
import com.example.Assignment2.groupTeam.GroupTeam;
import com.example.Assignment2.groupTeam.GroupTeamRepository;
import com.example.Assignment2.laboratory.Laboratory;
import com.example.Assignment2.laboratory.LaboratoryDTO;
import com.example.Assignment2.laboratory.LaboratoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final StudentRepository studentRepository;
    private final GroupTeamRepository groupTeamRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final AssignmentRepository assignmentRepository;

    private boolean checkIfStudentExists(String firstName, String lastName) {
        Student foundStudent = studentRepository.findStudentByFirstNameAndLastName(firstName, lastName);
        return foundStudent != null;
    }

    private String generateToken() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 128; i++) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public Student toEntity(StudentDTO studentDTO) {
        Optional<GroupTeam> groupTeam = groupTeamRepository.findById(studentDTO.getGroupTeam());
        GroupTeam foundGroupTeam;
        foundGroupTeam = groupTeam.orElse(null);

        return Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .email(studentDTO.getEmail())
                .username(studentDTO.getUsername())
                .password(studentDTO.getPassword())
                .groupTeam(foundGroupTeam)
                .hobby(studentDTO.getHobby())
                .build();
    }

    public StudentDTO fromEntity(Student student) {
        int groupTeamId = student.getGroupTeam().getId();

        return StudentDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .username(student.getUsername())
                .password(student.getPassword())
                .groupTeam(groupTeamId)
                .hobby(student.getHobby())
                .build();
    }

    public LaboratoryDTO fromEntityLab(Laboratory laboratory) {
        return LaboratoryDTO.builder()
                .nr(laboratory.getNr())
                .title(laboratory.getTitle())
                .description(laboratory.getDescription())
                .date(laboratory.getDate())
                .build();
    }

    public AssignmentDTO fromEntityAssignment(Assignment assignment) {
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .name(assignment.getName())
                .assignmentDescription(assignment.getAssignmentDescription())
                .deadline(assignment.getDeadline())
                .labId(assignment.getLabAssignment().getId())
                .build();
    }

    public Pair<String, HttpStatus> create(StudentDTO studentDTO) {
        Student student = toEntity(studentDTO);
        student.setToken(generateToken());

        if (checkIfStudentExists(student.getFirstName(), student.getLastName())) {
            return Pair.of("Student already exists", HttpStatus.BAD_REQUEST);
        }

        studentRepository.save(student);
        return Pair.of("Student created successfully", HttpStatus.OK);

    }

    public Pair<String, HttpStatus> delete(StudentDTO studentDTO) {
        Optional<Student> student = studentRepository.findById(studentDTO.getId());
        Student foundStudent;
        foundStudent = student.orElse(null);
        if (foundStudent == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }

        studentRepository.deleteById(foundStudent.getId());
        return Pair.of("Student deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> update(StudentDTO studentDTO) {
        Optional<Student> student = studentRepository.findById(studentDTO.getId());
        Student foundStudent;
        foundStudent = student.orElse(null);
        if (foundStudent == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }
        if (groupTeamRepository.findById(studentDTO.getGroupTeam()).isEmpty()) {
            return Pair.of("Group does not exist", HttpStatus.BAD_REQUEST);
        }

        foundStudent.setFirstName(studentDTO.getFirstName());
        foundStudent.setLastName(studentDTO.getLastName());
        foundStudent.setEmail(studentDTO.getEmail());
        foundStudent.setUsername(studentDTO.getUsername());
        foundStudent.setGroupTeam(groupTeamRepository.findById(studentDTO.getGroupTeam()).get());
        foundStudent.setHobby(studentDTO.getHobby());
        studentRepository.save(foundStudent);
        return Pair.of("Student updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findStudent(StudentDTO studentDTO) {
        Student student = studentRepository.findStudentByFirstNameAndLastName(studentDTO.getFirstName(), studentDTO.getLastName());
        if (student == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(fromEntity(student), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : studentList) {
            studentDTOS.add(fromEntity(student));
        }
        return Pair.of(studentDTOS, HttpStatus.OK);
    }

    public Pair<String, HttpStatus> register(StudentDTO studentDTO) {
        Student student = studentRepository.findStudentByToken(studentDTO.getToken());
        if (student == null) {
            return Pair.of("Token is incorrect", HttpStatus.BAD_REQUEST);
        }

        student.setPassword(Base64.getEncoder().encodeToString(studentDTO.getPassword().getBytes()));
        studentRepository.save(student);
        return Pair.of("Student registered successfully", HttpStatus.OK);

    }

    public Student findByUserName(String username) {
        return studentRepository.findStudentByUsername(username);
    }

    public Boolean isStudentLoggedIn(Student student) {
        if (student == null) {
            return false;
        }
        return !student.getLoggedIn().equals(Boolean.FALSE);
    }

    public Pair<String, HttpStatus> invalidLogin() {
        return Pair.of("Student is not logged in", HttpStatus.BAD_REQUEST);
    }

    public Boolean checkPasswordMatch(String password, String passwordHash) {
        return Base64.getEncoder().encodeToString(password.getBytes()).equals(passwordHash);
    }

    public Pair<String, HttpStatus> login(StudentDTO studentDTO) {
        Student foundStudent = findByUserName(studentDTO.getUsername());
        if (foundStudent == null || !checkPasswordMatch(studentDTO.getPassword(), foundStudent.getPassword())) {
            return Pair.of("Username or password incorrect", HttpStatus.BAD_REQUEST);
        }
        if (isStudentLoggedIn(foundStudent)) {
            return Pair.of("User already logged in.", HttpStatus.BAD_REQUEST);
        }

        foundStudent.setLoggedIn(Boolean.TRUE);
        studentRepository.save(foundStudent);
        return Pair.of("You have successfully logged in", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> logout(StudentDTO studentDTO) {
        Student foundStudent = findByUserName(studentDTO.getUsername());
        if (foundStudent == null) {
            return Pair.of("Username is incorrect", HttpStatus.BAD_REQUEST);
        }
        if (!isStudentLoggedIn(foundStudent)) {
            return Pair.of("User already logged out.", HttpStatus.BAD_REQUEST);
        }

        foundStudent.setLoggedIn(Boolean.FALSE);
        studentRepository.save(foundStudent);
        return Pair.of("You have successfully logged out", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllLabs() {
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        List<LaboratoryDTO> laboratoryDTOS = new ArrayList<>();
        for (Laboratory laboratory : laboratoryList) {
            laboratoryDTOS.add(fromEntityLab(laboratory));
        }
        return Pair.of(laboratoryDTOS, HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAssignmentsByLab(LaboratoryDTO laboratoryDTO) {
        Laboratory laboratory = laboratoryRepository.findLaboratoryByNr(laboratoryDTO.getNr());
        if (laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }

        List<Assignment> assignmentList = assignmentRepository.findAllByLabAssignment(laboratory);
        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            assignmentDTOS.add(fromEntityAssignment(assignment));
        }
        return Pair.of(assignmentDTOS, HttpStatus.OK);
    }

}
