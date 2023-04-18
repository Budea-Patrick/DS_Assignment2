package com.example.Assignment2.submission;

import com.example.Assignment2.assignment.Assignment;
import com.example.Assignment2.student.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "assignment")
    @NonNull
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student")
    @NonNull
    private Student student;

    @Column
    @NonNull
    private String gitLink;

    @Column
    private String comment;

    @Column
    private float grade;
}
