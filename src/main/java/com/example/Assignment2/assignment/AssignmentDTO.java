package com.example.Assignment2.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {

    private int id;
    private String requester;
    private String name;
    private Date deadline;
    private String assignmentDescription;
    private int labId;
}
