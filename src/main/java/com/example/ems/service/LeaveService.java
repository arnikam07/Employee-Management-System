package com.example.ems.service;

import com.example.ems.model.LeaveRequest;
import com.example.ems.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    private final LeaveRepository repo;

    public LeaveService(LeaveRepository repo) {
        this.repo = repo;
    }

    // Employee applies leave
    public LeaveRequest applyLeave(LeaveRequest leave) {
        leave.setStatus("PENDING");
        return repo.save(leave);
    }

    // Admin approves leave
    public LeaveRequest approveLeave(Long id) {
        LeaveRequest leave = repo.findById(id).orElseThrow();
        leave.setStatus("APPROVED");
        return repo.save(leave);
    }

    // Admin rejects leave
    public LeaveRequest rejectLeave(Long id) {
        LeaveRequest leave = repo.findById(id).orElseThrow();
        leave.setStatus("REJECTED");
        return repo.save(leave);
    }

    public List<LeaveRequest> getAllLeaves() {
        return repo.findAll();
    }
}