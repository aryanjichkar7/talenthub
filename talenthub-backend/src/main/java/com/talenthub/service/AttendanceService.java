package com.talenthub.service;

import com.talenthub.entity.Attendance;
import com.talenthub.entity.Employee;
import com.talenthub.repository.AttendanceRepository;
import com.talenthub.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // MARK attendance for an employee
    public Attendance markAttendance(Long employeeId, 
                                      Attendance.AttendanceStatus status,
                                      String remarks) {

        // Check if employee exists
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException(
                    "Employee not found with id: " + employeeId));

        // Prevent duplicate attendance for same day
        LocalDate today = LocalDate.now();
        if (attendanceRepository.existsByEmployeeIdAndDate(employeeId, today)) {
            throw new RuntimeException(
                "Attendance already marked for employee " + employeeId + " today!");
        }

        // Save attendance
        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(today)
                .status(status)
                .remarks(remarks)
                .build();

        return attendanceRepository.save(attendance);
    }

    // GET all attendance for one employee
    public List<Attendance> getAttendanceByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    // GET all attendance records
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
}