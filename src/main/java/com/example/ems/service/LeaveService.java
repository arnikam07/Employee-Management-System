package com.example.ems.service;

import java.util.List;

import com.example.ems.model.LeaveRequest;
import com.example.ems.model.LeaveStatus;

public interface LeaveService {

    LeaveRequest applyLeave(Long empId, int days);

    void approveLeave(Long leaveId);

    void rejectLeave(Long leaveId);

    int getLeaveBalance(Long empId);

    LeaveStatus getLeaveStatus(Long leaveId);

    List<LeaveRequest> getAllLeaves();
}
