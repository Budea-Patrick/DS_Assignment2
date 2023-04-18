package com.example.Assignment2.laboratory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Integer> {

    public Laboratory findLaboratoryById(int id);
    public Laboratory findLaboratoryByNr(int nr);

}
