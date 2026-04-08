package com.talenthub.repository;

import com.talenthub.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    // Get all leaves for one employee
    List<Leave> findByEmployeeId(Long employeeId);

    // Get leaves by status (e.g., all PENDING leaves)
    List<Leave> findByStatus(Leave.LeaveStatus status);

    // Count approved leaves for an employee
    int countByEmployeeIdAndStatus(Long employeeId, Leave.LeaveStatus status);
}
