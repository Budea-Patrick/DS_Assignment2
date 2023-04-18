package com.example.Assignment2.student;

import com.example.Assignment2.attendance.Attendance;
import com.example.Assignment2.groupTeam.GroupTeam;
import com.example.Assignment2.submission.Submission;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 128)
    private String token;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "groupTeam")
    private GroupTeam groupTeam;

    @OneToMany(mappedBy = "studentId")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "student")
    private List<Submission> submissions;

    @Column(nullable = false)
    @Value("false")
    private Boolean loggedIn;

    @Column
    private String hobby;

    public Student(int id, String token, String firstName, String lastName, String email, String username, String password, GroupTeam groupTeam, List<Attendance> attendances, List<Submission> submissions, Boolean loggedIn, String hobby) {
        this.id = id;
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
        this.groupTeam = groupTeam;
        this.attendances = attendances;
        this.submissions = submissions;
        this.loggedIn = false;
        this.hobby = hobby;
    }
}

