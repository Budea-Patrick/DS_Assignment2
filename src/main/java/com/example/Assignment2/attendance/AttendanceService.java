package com.example.Assignment2.attendance;

import com.example.Assignment2.laboratory.Laboratory;
import com.example.Assignment2.laboratory.LaboratoryDTO;
import com.example.Assignment2.laboratory.LaboratoryRepository;
import com.example.Assignment2.student.Student;
import com.example.Assignment2.student.StudentDTO;
import com.example.Assignment2.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final LaboratoryRepository laboratoryRepository;

    private boolean checkIfAttendanceExists(int studentId, int labId) {
        boolean found = false;

        List<Attendance> all = attendanceRepository.findAll();

        for(Attendance a : all) {
            if(studentId == a.getStudentId().getId() && labId == a.getLaboratoryId().getId()) {
                found = true;
                break;
            }
        }
        return found;
    }

    public AttendanceDTO fromEntity(Attendance attendance) {
        return AttendanceDTO.builder()
                .id(attendance.getId())
                .studentId(attendance.getStudentId().getId())
                .laboratoryId(attendance.getLaboratoryId().getId())
                .present(attendance.isPresent())
                .build();
    }

    public Attendance toEntity(AttendanceDTO attendanceDTO) {
        return Attendance.builder()
                .studentId(studentRepository.findStudentById(attendanceDTO.getStudentId()))
                .laboratoryId(laboratoryRepository.findLaboratoryById(attendanceDTO.getLaboratoryId()))
                .present(attendanceDTO.isPresent())
                .build();
    }



    public Pair<String, HttpStatus> create(AttendanceDTO attendanceDTO) {
        Attendance attendance = toEntity(attendanceDTO);

        Student student = studentRepository.findStudentById(attendanceDTO.getStudentId());
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(attendanceDTO.getLaboratoryId());

        if(student == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }
        if(laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }
        if(checkIfAttendanceExists(student.getId(), laboratory.getId())) {
            return Pair.of("Attendance already exists", HttpStatus.BAD_REQUEST);
        }

        attendanceRepository.save(attendance);
        return Pair.of("Attendance created successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> delete(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findAttendanceById(attendanceDTO.getId());
        if(attendance == null) {
            return Pair.of("Attendance does not exist", HttpStatus.BAD_REQUEST);
        }

        attendanceRepository.delete(attendance);
        return Pair.of("Attendance deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> update(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findAttendanceById(attendanceDTO.getId());
        if(attendance == null) {
            return Pair.of("Attendance does not exist", HttpStatus.BAD_REQUEST);
        }

        Student student = studentRepository.findStudentById(attendanceDTO.getStudentId());
        Laboratory laboratory = laboratoryRepository.findLaboratoryById(attendanceDTO.getLaboratoryId());

        if(student == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }
        if(laboratory == null) {
            return Pair.of("Laboratory does not exist", HttpStatus.BAD_REQUEST);
        }

        attendance.setStudentId(student);
        attendance.setLaboratoryId(laboratory);
        attendance.setPresent(attendanceDTO.isPresent());
        attendanceRepository.save(attendance);
        return Pair.of("Attendance updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> find(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findAttendanceById(attendanceDTO.getId());
        if(attendance == null) {
            return Pair.of("Attendance does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(fromEntity(attendance), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAll() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for(Attendance attendance : attendanceList) {
            attendanceDTOS.add(fromEntity(attendance));
        }
        return Pair.of(attendanceDTOS, HttpStatus.OK);
    }

}
