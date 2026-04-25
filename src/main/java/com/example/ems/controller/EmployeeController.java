package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public Employee addEmployee(@RequestBody Employee emp) {
        return service.addEmployee(emp);
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAllEmployees();
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee emp) {
    return service.updateEmployee(id, emp);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}