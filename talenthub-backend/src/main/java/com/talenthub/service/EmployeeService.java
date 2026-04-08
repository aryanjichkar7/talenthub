package com.talenthub.service;

import com.talenthub.entity.Employee;
import com.talenthub.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service  // Tells Spring: "This class contains business logic"
public class EmployeeService {

    @Autowired  // Spring automatically injects the repository here
    private EmployeeRepository employeeRepository;

    // GET ALL employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // GET ONE employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    // CREATE new employee
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email already exists: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    // UPDATE existing employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);
        existing.setFirstName(updatedEmployee.getFirstName());
        existing.setLastName(updatedEmployee.getLastName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setRole(updatedEmployee.getRole());
        existing.setDateOfJoining(updatedEmployee.getDateOfJoining());
        existing.setSalary(updatedEmployee.getSalary());
        existing.setIsActive(updatedEmployee.getIsActive());
        return employeeRepository.save(existing);
    }

    // DELETE employee
    public void deleteEmployee(Long id) {
        getEmployeeById(id); // Will throw error if not found
        employeeRepository.deleteById(id);
    }
}