package com.example.Assignment2.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {

    private int id;
    private String requester;
    private int studentId;
    private int laboratoryId;
    private boolean present;

}
