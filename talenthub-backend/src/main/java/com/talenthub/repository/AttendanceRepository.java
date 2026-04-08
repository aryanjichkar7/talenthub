package com.talenthub.repository;

import com.talenthub.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Get all attendance for one employee
    List<Attendance> findByEmployeeId(Long employeeId);

    // Check if attendance already marked for this employee on this date
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);

    // Get attendance for specific employee on specific date
    List<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}