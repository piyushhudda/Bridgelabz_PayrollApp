package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EmployeeDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }

    // Add a new employee
    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = convertToEntity(employeeDTO);
            Employee savedEmployee = employeeRepository.save(employee);
            return new ResponseEntity<>(convertToDTO(savedEmployee), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(employee));
    }

    // Update an employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Employee updatedEmployee = convertToEntity(employeeDTO);
            updatedEmployee.setId(id); // ensure the entity has the correct ID
            Employee savedEmployee = employeeRepository.save(updatedEmployee);
            return ResponseEntity.ok(convertToDTO(savedEmployee));
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Convert EmployeeDTO to Employee entity
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) return null;

        Employee employee = new Employee();
        // Map fields from DTO to entity
        employee.setId(employeeDTO.getId()); // For creation, ID can be null
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        // Add other fields if present
        return employee;
    }

    // Convert Employee entity to EmployeeDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        if (employee == null) return null;

        EmployeeDTO employeeDTO = new EmployeeDTO();
        // Map fields from entity to DTO
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        // Add other fields if present
        return employeeDTO;
    }
}