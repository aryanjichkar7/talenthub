package com.talenthub.controller;

import com.talenthub.entity.Leave;
import com.talenthub.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // POST apply leave
    @PostMapping("/apply/{employeeId}")
    public ResponseEntity<Leave> applyLeave(
            @PathVariable Long employeeId,
            @RequestBody Leave leaveRequest) {
        Leave leave = leaveService.applyLeave(employeeId, leaveRequest);
        return ResponseEntity.ok(leave);
    }

    // PUT approve or reject leave
    // Body: { "status": "APPROVED" }
    @PutMapping("/approve/{leaveId}")
    public ResponseEntity<Leave> updateLeaveStatus(
            @PathVariable Long leaveId,
            @RequestBody Map<String, String> body) {
        Leave.LeaveStatus status = 
                Leave.LeaveStatus.valueOf(body.get("status"));
        Leave leave = leaveService.updateLeaveStatus(leaveId, status);
        return ResponseEntity.ok(leave);
    }

    // GET leaves for one employee
    @GetMapping("/{employeeId}")
    public List<Leave> getLeavesByEmployee(@PathVariable Long employeeId) {
        return leaveService.getLeavesByEmployee(employeeId);
    }

    // GET all pending leaves
    @GetMapping("/pending")
    public List<Leave> getPendingLeaves() {
        return leaveService.getPendingLeaves();
    }

    // GET all leaves
    @GetMapping
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    // GET leave balance for employee
    @GetMapping("/balance/{employeeId}")
    public ResponseEntity<Map<String, Integer>> getLeaveBalance(
            @PathVariable Long employeeId) {
        int balance = leaveService.getLeaveBalance(employeeId);
        return ResponseEntity.ok(Map.of("leaveBalance", balance));
    }
}
