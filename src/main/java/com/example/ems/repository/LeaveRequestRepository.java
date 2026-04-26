package com.example.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ems.model.LeaveRequest;
import com.example.ems.model.LeaveStatus;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee_Id(Long employeeId);
    List<LeaveRequest> findByStatus(LeaveStatus status);
}
