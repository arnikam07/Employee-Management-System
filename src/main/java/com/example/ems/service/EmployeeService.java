package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee addEmployee(Employee emp) {
        return repo.save(emp);
    }

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public Employee updateEmployee(Long id, Employee updated) {
    Employee emp = repo.findById(id).orElseThrow();

    emp.setName(updated.getName());
    emp.setDepartment(updated.getDepartment());
    emp.setSalary(updated.getSalary());

    return repo.save(emp);
}

    public void deleteEmployee(Long id) {
        repo.deleteById(id);
    }
}