package com.example.ems.controller;

import com.example.ems.model.*;
import com.example.ems.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    // Apply for leave
    @PostMapping("/{empId}")
    public ResponseEntity<LeaveRequest> applyLeave(
            @PathVariable Long empId,
            @RequestParam int days) {
        return ResponseEntity.ok(leaveService.applyLeave(empId, days));
    }

    // Approve leave
    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<String> approve(@PathVariable Long leaveId) {
        leaveService.approveLeave(leaveId);
        return ResponseEntity.ok("Leave approved");
    }

    // Reject leave
    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<String> reject(@PathVariable Long leaveId) {
        leaveService.rejectLeave(leaveId);
        return ResponseEntity.ok("Leave rejected");
    }

    // Get leave balance
    @GetMapping("/balance/{empId}")
    public ResponseEntity<Integer> getBalance(@PathVariable Long empId) {
        return ResponseEntity.ok(leaveService.getLeaveBalance(empId));
    }

    // Get leave status
    @GetMapping("/{leaveId}/status")
    public ResponseEntity<LeaveStatus> getStatus(@PathVariable Long leaveId) {
        return ResponseEntity.ok(leaveService.getLeaveStatus(leaveId));
    }

    // Get all leaves
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAll() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }
}
