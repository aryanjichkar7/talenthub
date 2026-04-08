package com.talenthub.controller;

import com.talenthub.entity.Attendance;
import com.talenthub.entity.Leave;
import com.talenthub.repository.AttendanceRepository;
import com.talenthub.repository.EmployeeRepository;
import com.talenthub.repository.LeaveRepository;
import com.talenthub.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportsController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    // GET dashboard summary
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();

        // Total employees
        summary.put("totalEmployees", employeeRepository.count());

        // Total leaves
        summary.put("totalLeaves", leaveRepository.count());

        // Pending leaves
        summary.put("pendingLeaves",
            leaveRepository.findByStatus(Leave.LeaveStatus.PENDING).size());

        // Approved leaves
        summary.put("approvedLeaves",
            leaveRepository.findByStatus(Leave.LeaveStatus.APPROVED).size());

        // Total attendance records
        summary.put("totalAttendance", attendanceRepository.count());

        // Present count
        long presentCount = attendanceRepository.findAll()
            .stream()
            .filter(a -> a.getStatus() == Attendance.AttendanceStatus.PRESENT)
            .count();
        summary.put("presentCount", presentCount);

        // Total payroll processed
        double totalPayroll = payrollRepository.findAll()
            .stream()
            .mapToDouble(p -> p.getNetSalary())
            .sum();
        summary.put("totalPayroll", Math.round(totalPayroll * 100.0) / 100.0);

        // Department wise employee count
        Map<String, Long> deptCount = new HashMap<>();
        employeeRepository.findAll().forEach(emp -> {
            String dept = emp.getDepartment();
            deptCount.put(dept, deptCount.getOrDefault(dept, 0L) + 1);
        });
        summary.put("departmentStats", deptCount);

        return ResponseEntity.ok(summary);
    }

    // GET attendance stats
    @GetMapping("/attendance")
    public ResponseEntity<Map<String, Object>> getAttendanceStats() {
        Map<String, Object> stats = new HashMap<>();

        List<Attendance> all = attendanceRepository.findAll();

        long present = all.stream()
            .filter(a -> a.getStatus() == Attendance.AttendanceStatus.PRESENT)
            .count();
        long absent = all.stream()
            .filter(a -> a.getStatus() == Attendance.AttendanceStatus.ABSENT)
            .count();
        long halfDay = all.stream()
            .filter(a -> a.getStatus() == Attendance.AttendanceStatus.HALF_DAY)
            .count();

        stats.put("present", present);
        stats.put("absent", absent);
        stats.put("halfDay", halfDay);
        stats.put("total", all.size());

        return ResponseEntity.ok(stats);
    }

    // GET payroll stats
    @GetMapping("/payroll")
    public ResponseEntity<Map<String, Object>> getPayrollStats() {
        Map<String, Object> stats = new HashMap<>();

        var payrolls = payrollRepository.findAll();

        double totalNet = payrolls.stream()
            .mapToDouble(p -> p.getNetSalary()).sum();
        double totalPF = payrolls.stream()
            .mapToDouble(p -> p.getPfDeduction()).sum();
        double totalESI = payrolls.stream()
            .mapToDouble(p -> p.getEsiDeduction()).sum();
        double totalGross = payrolls.stream()
            .mapToDouble(p -> p.getGrossSalary()).sum();

        stats.put("totalNetSalary", Math.round(totalNet * 100.0) / 100.0);
        stats.put("totalPFDeducted", Math.round(totalPF * 100.0) / 100.0);
        stats.put("totalESIDeducted", Math.round(totalESI * 100.0) / 100.0);
        stats.put("totalGrossSalary", Math.round(totalGross * 100.0) / 100.0);

        return ResponseEntity.ok(stats);
    }
}