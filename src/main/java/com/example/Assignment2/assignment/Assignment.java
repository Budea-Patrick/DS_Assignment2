package com.example.Assignment2.assignment;

import com.example.Assignment2.laboratory.Laboratory;
import com.example.Assignment2.submission.Submission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String assignmentDescription;

    @Column
    private Date deadline;

    @OneToOne
    @JoinColumn(name = "labAssignment")
    private Laboratory labAssignment;

    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions;
}
