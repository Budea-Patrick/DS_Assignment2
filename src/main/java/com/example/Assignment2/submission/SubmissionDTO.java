package com.example.Assignment2.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {

    private int id;
    private String requester;
    private int assignment;
    private int student;
    private String gitLink;
    private String comment;
    private float grade;

}
