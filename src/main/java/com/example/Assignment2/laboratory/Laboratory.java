package com.example.Assignment2.laboratory;


import com.example.Assignment2.assignment.Assignment;
import com.example.Assignment2.attendance.Attendance;
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
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int nr;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Date date;

    @OneToOne(mappedBy = "labAssignment")
    private Assignment labAssignment;

    @OneToMany(mappedBy = "laboratoryId")
    private List<Attendance> attendances;

}
