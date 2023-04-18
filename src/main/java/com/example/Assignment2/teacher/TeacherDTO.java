package com.example.Assignment2.teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDTO {

    private String username;
    private String password;
}
