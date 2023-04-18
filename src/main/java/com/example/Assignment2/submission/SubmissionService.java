package com.example.Assignment2.submission;

import com.example.Assignment2.assignment.Assignment;
import com.example.Assignment2.assignment.AssignmentRepository;
import com.example.Assignment2.student.Student;
import com.example.Assignment2.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    private boolean checkIfAssignmentExists(int assignmentId, int studentId) {

        boolean found = false;

        List<Submission> submissions = submissionRepository.findAll();

        for(Submission sub : submissions) {
            if(assignmentId == sub.getAssignment().getId() && studentId == sub.getStudent().getId()) {
                found = true;
                break;
            }
        }
        return found;
    }

    public Submission toEntity(SubmissionDTO submissionDTO) {

        Assignment assignment = assignmentRepository.findAssignmentById(submissionDTO.getAssignment());
        Student student = studentRepository.findStudentById(submissionDTO.getStudent());

        return Submission.builder()
                .assignment(assignment)
                .student(student)
                .gitLink(submissionDTO.getGitLink())
                .comment(submissionDTO.getComment())
                .grade(submissionDTO.getGrade())
                .build();
    }

    public SubmissionDTO fromEntity(Submission submission) {

        return SubmissionDTO.builder()
                .id(submission.getId())
                .assignment(submission.getAssignment().getId())
                .student(submission.getStudent().getId())
                .gitLink(submission.getGitLink())
                .comment(submission.getComment())
                .grade(submission.getGrade())
                .build();
    }



    public Pair<String, HttpStatus> create(SubmissionDTO submissionDTO) {
        Submission submission = toEntity(submissionDTO);

        Assignment assignment = assignmentRepository.findAssignmentById(submissionDTO.getAssignment());
        Student student = studentRepository.findStudentById(submissionDTO.getStudent());

        if(assignment == null) {
            return Pair.of("Assignment does not exist", HttpStatus.BAD_REQUEST);
        }
        if(student == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }
        if(checkIfAssignmentExists(submissionDTO.getAssignment(), submissionDTO.getStudent())){
            return Pair.of("Assignment already exists", HttpStatus.BAD_REQUEST);
        }

        submissionRepository.save(submission);
        return Pair.of("Assignment created successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> grade(SubmissionDTO submissionDTO) {
        Submission submission = submissionRepository.findSubmissionById(submissionDTO.getId());
        if(submission == null) {
            return Pair.of("Submission does not exist", HttpStatus.BAD_REQUEST);
        }

        submission.setGrade(submissionDTO.getGrade());
        submissionRepository.save(submission);
        return Pair.of("Submission graded successfully", HttpStatus.OK);
    }

}
