package com.example.Assignment2.assignment;

import com.example.Assignment2.laboratory.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    public Assignment findAssignmentById(int id);
    public List<Assignment> findAllByLabAssignment(Laboratory labAssignment);

}
