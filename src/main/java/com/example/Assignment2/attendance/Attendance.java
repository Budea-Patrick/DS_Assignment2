package com.example.Assignment2.attendance;


import com.example.Assignment2.laboratory.Laboratory;
import com.example.Assignment2.student.Student;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "studentId")
    @NonNull
    private Student studentId;

    @ManyToOne
    @JoinColumn(name = "laboratoryId")
    @NonNull
    private Laboratory laboratoryId;

    @Column
    private boolean present;
}
