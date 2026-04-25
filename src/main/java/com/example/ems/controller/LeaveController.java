package com.example.ems.controller;

import com.example.ems.model.LeaveRequest;
import com.example.ems.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    // Apply leave
    @PostMapping
    public LeaveRequest applyLeave(@RequestBody LeaveRequest leave) {
        return service.applyLeave(leave);
    }

    // Get all leaves
    @GetMapping
    public List<LeaveRequest> getAllLeaves() {
        return service.getAllLeaves();
    }

    // Approve leave
    @PutMapping("/{id}/approve")
    public LeaveRequest approve(@PathVariable Long id) {
        return service.approveLeave(id);
    }

    // Reject leave
    @PutMapping("/{id}/reject")
    public LeaveRequest reject(@PathVariable Long id) {
        return service.rejectLeave(id);
    }
}