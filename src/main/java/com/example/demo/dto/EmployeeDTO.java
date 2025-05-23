package com.example.demo.dto;

public class EmployeeDTO {
    private String name;
    private double salary;
	public EmployeeDTO(String name, double salary) {
		super();
		this.name = name;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}

    // constructors, getters, setters
    
}
