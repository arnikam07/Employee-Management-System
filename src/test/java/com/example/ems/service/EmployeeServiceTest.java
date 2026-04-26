package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    @Test
    void testAddEmployee() {
        // mock repository
        EmployeeRepository repo = Mockito.mock(EmployeeRepository.class);

        Employee emp = new Employee();
        emp.setName("Anagha");
        emp.setDepartment("IT");
        emp.setSalary(50000);

        Mockito.when(repo.save(emp)).thenReturn(emp);

        EmployeeService service = new EmployeeService(repo);

        Employee saved = service.addEmployee(emp);

        assertEquals("Anagha", saved.getName());
    }

    @Test
    void testGetAllEmployees() {
        EmployeeRepository repo = Mockito.mock(EmployeeRepository.class);

        List<Employee> list = Arrays.asList(new Employee(), new Employee());

        Mockito.when(repo.findAll()).thenReturn(list);

        EmployeeService service = new EmployeeService(repo);

        List<Employee> result = service.getAllEmployees();

        assertEquals(2, result.size());
    }
}
