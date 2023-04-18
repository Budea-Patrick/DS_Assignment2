package com.example.Assignment2.assignment;

import com.example.Assignment2.laboratory.Laboratory;
import com.example.Assignment2.laboratory.LaboratoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LaboratoryRepository laboratoryRepository;

    public Assignment toEntity(AssignmentDTO assignmentDTO) {
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(assignmentDTO.getLabId());
        return Assignment.builder()
                .name(assignmentDTO.getName())
                .assignmentDescription(assignmentDTO.getAssignmentDescription())
                .deadline(assignmentDTO.getDeadline())
                .labAssignment(laboratory)
                .build();
    }

    public AssignmentDTO fromEntity(Assignment assignment) {
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .name(assignment.getName())
                .assignmentDescription(assignment.getAssignmentDescription())
                .deadline(assignment.getDeadline())
                .labId(assignment.getLabAssignment().getId())
                .build();
    }

    private boolean checkIfAssignmentExists(int id) {
        boolean found = false;

        List<Assignment> assignments = assignmentRepository.findAll();

        for (Assignment as : assignments) {
            if (id == as.getLabAssignment().getId()) {
                found = true;
                break;
            }
        }
        return found;
    }

    public Pair<String, HttpStatus> create(AssignmentDTO assignmentDTO) {
        Assignment assignment = toEntity(assignmentDTO);
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(assignmentDTO.getLabId());

        if (checkIfAssignmentExists(assignmentDTO.getId())) {
            return Pair.of("Assignment already exists", HttpStatus.BAD_REQUEST);
        }

        if (laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }

        assignmentRepository.save(assignment);
        return Pair.of("Assignment created successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> delete(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findAssignmentById(assignmentDTO.getId());
        if (assignment == null) {
            return Pair.of("Assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        assignmentRepository.delete(assignment);
        return Pair.of("Assignment deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> update(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findAssignmentById(assignmentDTO.getId());
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(assignmentDTO.getLabId());

        if (assignment == null) {
            return Pair.of("Assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        if (laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }

        assignment.setName(assignmentDTO.getName());
        assignment.setDeadline(assignmentDTO.getDeadline());
        assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());
        assignment.setLabAssignment(laboratory);

        assignmentRepository.save(assignment);
        return Pair.of("Assignment updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findAssignmentById(assignmentDTO.getId());
        if (assignment == null) {
            return Pair.of("Assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(fromEntity(assignment), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllAssignments() {
        List<Assignment> assignmentList = assignmentRepository.findAll();
        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            assignmentDTOS.add(fromEntity(assignment));
        }
        return Pair.of(assignmentDTOS, HttpStatus.OK);
    }

}
