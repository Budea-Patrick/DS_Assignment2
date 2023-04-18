package com.example.Assignment2.laboratory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    private boolean checkIfLaboratoryExists(int nr) {
        boolean found = false;

        List<Laboratory> labs = laboratoryRepository.findAll();
        for (Laboratory lab : labs) {
            if (nr == lab.getNr()) {
                found = true;
                break;
            }
        }
        return found;
    }

    public Laboratory toEntity(LaboratoryDTO laboratoryDTO) {
        return Laboratory.builder()
                .nr(laboratoryDTO.getNr())
                .title(laboratoryDTO.getTitle())
                .description(laboratoryDTO.getDescription())
                .date(laboratoryDTO.getDate())
                .build();
    }

    public LaboratoryDTO fromEntity(Laboratory laboratory) {
        return LaboratoryDTO.builder()
                .nr(laboratory.getNr())
                .title(laboratory.getTitle())
                .description(laboratory.getDescription())
                .date(laboratory.getDate())
                .build();
    }

    public Pair<String, HttpStatus> create(LaboratoryDTO laboratoryDTO) {
        Laboratory laboratory = toEntity(laboratoryDTO);
        if (checkIfLaboratoryExists(laboratory.getNr())) {
            return Pair.of("Laboratory already exists", HttpStatus.BAD_REQUEST);
        }
        laboratoryRepository.save(laboratory);
        return Pair.of("Laboratory created successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> delete(LaboratoryDTO laboratoryDTO) {
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(laboratoryDTO.getId());
        if (laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }

        laboratoryRepository.delete(laboratory);
        return Pair.of("Laboratory deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> update(LaboratoryDTO laboratoryDTO) {
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(laboratoryDTO.getId());
        if (laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }

        laboratory.setNr(laboratoryDTO.getNr());
        laboratory.setTitle(laboratoryDTO.getTitle());
        laboratory.setDescription(laboratoryDTO.getDescription());
        laboratory.setDate(laboratoryDTO.getDate());

        laboratoryRepository.save(laboratory);
        return Pair.of("Laboratory updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findLaboratory(LaboratoryDTO laboratoryDTO) {
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(laboratoryDTO.getId());
        if (laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(fromEntity(laboratory), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllLaboratories() {
        List<Laboratory> laboratoryList = laboratoryRepository.findAll();
        List<LaboratoryDTO> laboratoryDTOS = new ArrayList<>();
        for (Laboratory laboratory : laboratoryList) {
            laboratoryDTOS.add(fromEntity(laboratory));
        }
        return Pair.of(laboratoryDTOS, HttpStatus.OK);
    }
}
