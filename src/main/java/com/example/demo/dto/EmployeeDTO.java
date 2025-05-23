package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {

    private Long id; // Use Long for IDs

    @NotBlank(message = "Employee name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(
        regexp = "^[\\p{L} .'-]+$",
        message = "Name can only contain letters, spaces, apostrophes, hyphens, and dots"
    )
    private String name;

    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    // Default constructor
    public EmployeeDTO() {
        // no-args constructor
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}