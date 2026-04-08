package com.talenthub.repository;

import com.talenthub.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository  // Tells Spring: "This is a database access class"
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Check if an employee with this email already exists
    boolean existsByEmail(String email);

    // Find employee by email (useful later for login)
    Optional<Employee> findByEmail(String email);
}