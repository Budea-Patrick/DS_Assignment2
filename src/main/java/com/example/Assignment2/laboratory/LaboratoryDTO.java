package com.example.Assignment2.laboratory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaboratoryDTO {

    private int id;
    private String requester;
    private int nr;
    private String title;
    private String description;
    private Date date;

}
