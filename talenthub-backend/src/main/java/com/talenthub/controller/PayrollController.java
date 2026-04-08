package com.talenthub.controller;

import com.talenthub.entity.Payroll;
import com.talenthub.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "http://localhost:3000")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    // POST generate payroll for employee
    @PostMapping("/generate/{employeeId}")
    public ResponseEntity<Payroll> generatePayroll(
            @PathVariable Long employeeId) {
        Payroll payroll = payrollService.generatePayroll(employeeId);
        return ResponseEntity.ok(payroll);
    }

    // GET all payroll for one employee
    @GetMapping("/{employeeId}")
    public List<Payroll> getPayrollByEmployee(
            @PathVariable Long employeeId) {
        return payrollService.getPayrollByEmployee(employeeId);
    }

    // GET all payroll records
    @GetMapping
    public List<Payroll> getAllPayroll() {
        return payrollService.getAllPayroll();
    }

    // GET payroll by employee and month
    // e.g. /api/payroll/1/APRIL-2026
    @GetMapping("/{employeeId}/{monthYear}")
    public ResponseEntity<Payroll> getPayrollByMonth(
            @PathVariable Long employeeId,
            @PathVariable String monthYear) {
        Payroll payroll = payrollService
                .getPayrollByEmployeeAndMonth(employeeId, monthYear);
        return ResponseEntity.ok(payroll);
    }
}
