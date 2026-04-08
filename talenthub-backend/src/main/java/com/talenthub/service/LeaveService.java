package com.talenthub.service;

import com.talenthub.entity.Employee;
import com.talenthub.entity.Leave;
import com.talenthub.repository.EmployeeRepository;
import com.talenthub.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // APPLY for leave
    public Leave applyLeave(Long employeeId, Leave leaveRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException(
                    "Employee not found with id: " + employeeId));

        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(Leave.LeaveStatus.PENDING); // Always starts as PENDING
        return leaveRepository.save(leaveRequest);
    }

    // APPROVE or REJECT a leave
    public Leave updateLeaveStatus(Long leaveId, Leave.LeaveStatus newStatus) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException(
                    "Leave not found with id: " + leaveId));

        leave.setStatus(newStatus);
        return leaveRepository.save(leave);
    }

    // GET all leaves for one employee
    public List<Leave> getLeavesByEmployee(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    // GET all PENDING leaves (for HR/Admin approval)
    public List<Leave> getPendingLeaves() {
        return leaveRepository.findByStatus(Leave.LeaveStatus.PENDING);
    }

    // GET all leaves
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // GET leave balance (how many approved leaves used)
    public int getLeaveBalance(Long employeeId) {
        int totalAllowed = 20; // 20 leaves per year
        int used = leaveRepository.countByEmployeeIdAndStatus(
                        employeeId, Leave.LeaveStatus.APPROVED);
        return totalAllowed - used;
    }
}
