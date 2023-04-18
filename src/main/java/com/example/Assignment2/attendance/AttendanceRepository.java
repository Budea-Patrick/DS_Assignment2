package com.example.Assignment2.attendance;

import com.example.Assignment2.laboratory.Laboratory;
import com.example.Assignment2.student.Student;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository  extends JpaRepository<Attendance, Integer> {

    public Attendance findAttendanceByStudentIdAndLaboratoryId(@NonNull Student studentId, @NonNull Laboratory laboratoryId);
    public Attendance findAttendanceById(int id);

}
