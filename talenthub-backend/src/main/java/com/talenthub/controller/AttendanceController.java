package com.talenthub.controller;

import com.talenthub.entity.Attendance;
import com.talenthub.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // POST mark attendance
    // Body: { "status": "PRESENT", "remarks": "On time" }
    @PostMapping("/mark/{employeeId}")
    public ResponseEntity<Attendance> markAttendance(
            @PathVariable Long employeeId,
            @RequestBody Map<String, String> body) {

        Attendance.AttendanceStatus status =
                Attendance.AttendanceStatus.valueOf(body.get("status"));
        String remarks = body.getOrDefault("remarks", "");

        Attendance attendance = attendanceService
                .markAttendance(employeeId, status, remarks);
        return ResponseEntity.ok(attendance);
    }

    // GET attendance for one employee
    @GetMapping("/{employeeId}")
    public List<Attendance> getAttendanceByEmployee(
            @PathVariable Long employeeId) {
        return attendanceService.getAttendanceByEmployee(employeeId);
    }

    // GET all attendance
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }
}