package com.example.ems.service;

import com.example.ems.model.*;
import com.example.ems.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRequestRepository leaveRepo;
    private final EmployeeRepository employeeRepo;  // Saumya's repo

    @Override
    public LeaveRequest applyLeave(Long empId, int days) {
        if (days <= 0)
            throw new RuntimeException("Days must be greater than zero");

        // was: employees.get(empId)
        Employee emp = employeeRepo.findById(empId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (emp.getLeaveBalance() < days)   // need leaveBalance on Employee
            throw new RuntimeException("Insufficient leave balance");

        LeaveRequest request = new LeaveRequest(null, emp, days, LeaveStatus.PENDING);
        return leaveRepo.save(request);
    }

    @Override
    public void approveLeave(Long leaveId) {
        LeaveRequest request = leaveRepo.findById(leaveId)
            .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (request.getStatus() == LeaveStatus.APPROVED)
            throw new RuntimeException("Already approved");
        if (request.getStatus() == LeaveStatus.REJECTED)
            throw new RuntimeException("Already rejected");

        Employee emp = request.getEmployee();
        emp.setLeaveBalance(emp.getLeaveBalance() - request.getNumberOfDays());
        employeeRepo.save(emp);

        request.setStatus(LeaveStatus.APPROVED);
        leaveRepo.save(request);
    }

    @Override
    public void rejectLeave(Long leaveId) {
        LeaveRequest request = leaveRepo.findById(leaveId)
            .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (request.getStatus() != LeaveStatus.PENDING)
            throw new RuntimeException("Only PENDING leaves can be rejected");

        request.setStatus(LeaveStatus.REJECTED);
        leaveRepo.save(request);
    }

    @Override
    public int getLeaveBalance(Long empId) {
        return employeeRepo.findById(empId)
            .orElseThrow(() -> new RuntimeException("Employee not found"))
            .getLeaveBalance();
    }

    @Override
    public LeaveStatus getLeaveStatus(Long leaveId) {
        return leaveRepo.findById(leaveId)
            .orElseThrow(() -> new RuntimeException("Leave not found"))
            .getStatus();
    }

    @Override
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepo.findAll();
    }
}
