package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Add a new employee
    @PostMapping
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(employee -> ResponseEntity.ok(convertToDTO(employee)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Update employee by ID
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    Employee updatedEmployee = convertToEntity(employeeDTO);
                    // preserve the original ID
                    updatedEmployee.setId(employee.getId()); 
                    Employee savedEmployee = employeeRepository.save(updatedEmployee);
                    return ResponseEntity.ok(convertToDTO(savedEmployee));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Conversion methods
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getName(), employee.getSalary());
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        // Assuming department is optional or can be null
        return new Employee(dto.getName(), dto.getSalary(), null);
    }
}