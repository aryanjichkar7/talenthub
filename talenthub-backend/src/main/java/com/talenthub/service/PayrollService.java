package com.talenthub.service;

import com.talenthub.entity.Employee;
import com.talenthub.entity.Payroll;
import com.talenthub.repository.EmployeeRepository;
import com.talenthub.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // GENERATE payroll for an employee
    public Payroll generatePayroll(Long employeeId) {

        // Step 1: Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException(
                    "Employee not found with id: " + employeeId));

        // Step 2: Get current month-year string e.g. "APRIL-2026"
        String monthYear = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MMMM-yyyy"))
                .toUpperCase();

        // Step 3: Prevent duplicate payroll for same month
        if (payrollRepository.existsByEmployeeIdAndMonthYear(employeeId, monthYear)) {
            throw new RuntimeException(
                "Payroll already generated for " + monthYear);
        }

        // Step 4: Calculate salary components
        Double ctc = employee.getSalary();           // Annual CTC

        Double basicSalary  = ctc * 0.50;            // 50% of CTC
        Double hra          = basicSalary * 0.20;    // 20% of Basic
        Double allowances   = basicSalary * 0.30;    // 30% of Basic
        Double grossSalary  = basicSalary + hra + allowances;
        Double pfDeduction  = basicSalary * 0.12;    // 12% of Basic
        Double esiDeduction = grossSalary * 0.0075;  // 0.75% of Gross
        Double netSalary    = grossSalary - pfDeduction - esiDeduction;

        // Step 5: Build and save payroll record
        Payroll payroll = Payroll.builder()
                .employee(employee)
                .monthYear(monthYear)
                .basicSalary(Math.round(basicSalary * 100.0) / 100.0)
                .hra(Math.round(hra * 100.0) / 100.0)
                .allowances(Math.round(allowances * 100.0) / 100.0)
                .grossSalary(Math.round(grossSalary * 100.0) / 100.0)
                .pfDeduction(Math.round(pfDeduction * 100.0) / 100.0)
                .esiDeduction(Math.round(esiDeduction * 100.0) / 100.0)
                .netSalary(Math.round(netSalary * 100.0) / 100.0)
                .generatedDate(LocalDate.now())
                .build();

        return payrollRepository.save(payroll);
    }

    // GET all payroll for one employee
    public List<Payroll> getPayrollByEmployee(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }

    // GET all payroll records
    public List<Payroll> getAllPayroll() {
        return payrollRepository.findAll();
    }

    // GET payroll by specific month
    public Payroll getPayrollByEmployeeAndMonth(Long employeeId, String monthYear) {
        return payrollRepository.findByEmployeeIdAndMonthYear(employeeId, monthYear)
                .orElseThrow(() -> new RuntimeException(
                    "Payroll not found for " + monthYear));
    }
}
