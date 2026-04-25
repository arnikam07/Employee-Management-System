package com.example.ems.controller;

import com.example.ems.service.PayrollService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PayrollService payrollService;

    public AdminController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/generate-payroll")
    public String generatePayroll() {
        return payrollService.generatePayroll();
    }
}

