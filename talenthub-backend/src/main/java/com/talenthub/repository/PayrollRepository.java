package com.talenthub.repository;

import com.talenthub.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    // Get all payroll records for one employee
    List<Payroll> findByEmployeeId(Long employeeId);

    // Get payroll for specific employee and month
    Optional<Payroll> findByEmployeeIdAndMonthYear(Long employeeId, String monthYear);

    // Check if payroll already generated for this month
    boolean existsByEmployeeIdAndMonthYear(Long employeeId, String monthYear);
}
