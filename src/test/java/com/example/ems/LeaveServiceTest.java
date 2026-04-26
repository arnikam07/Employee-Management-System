package com.example.ems;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import com.example.ems.model.Employee;
import com.example.ems.model.LeaveRequest;
import com.example.ems.model.LeaveStatus;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.LeaveRequestRepository;
import com.example.ems.service.LeaveServiceImpl;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveRequestRepository leaveRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    // 1. Apply Leave - Success
    @Test
    void testApplyLeave() {
        Employee emp = new Employee(1L, "John", "IT", 10);

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));
        when(leaveRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        LeaveRequest request = leaveService.applyLeave(1L, 3);

        assertNotNull(request);
        assertEquals(LeaveStatus.PENDING, request.getStatus());
        assertEquals(3, request.getNumberOfDays());
    }

    // 2. Approve Leave
    @Test
    void testApproveLeave() {
        Employee emp = new Employee(1L, "John", "IT", 10);
        LeaveRequest req = new LeaveRequest(1L, emp, 2, LeaveStatus.PENDING);

        when(leaveRepo.findById(1L)).thenReturn(Optional.of(req));

        leaveService.approveLeave(1L);

        assertEquals(8, emp.getLeaveBalance());
        assertEquals(LeaveStatus.APPROVED, req.getStatus());
    }

    // 3. Reject Leave
    @Test
    void testRejectLeave() {
        Employee emp = new Employee(1L, "John", "IT", 10);
        LeaveRequest req = new LeaveRequest(1L, emp, 2, LeaveStatus.PENDING);

        when(leaveRepo.findById(1L)).thenReturn(Optional.of(req));

        leaveService.rejectLeave(1L);

        assertEquals(LeaveStatus.REJECTED, req.getStatus());
    }

    // 4. Insufficient Balance
    @Test
    void testInsufficientLeaveBalance() {
        Employee emp = new Employee(1L, "John", "IT", 2);

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));

        assertThrows(RuntimeException.class, () ->
                leaveService.applyLeave(1L, 5));
    }

    // 5. Non-existent Employee
    @Test
    void testApplyLeaveForNonExistentEmployee() {
        when(employeeRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                leaveService.applyLeave(999L, 3));
    }

    // 6. Apply zero days
    @Test
    void testApplyLeaveZeroDays() {
        assertThrows(RuntimeException.class, () ->
                leaveService.applyLeave(1L, 0));
    }

    // 7. Apply negative days
    @Test
    void testApplyLeaveNegativeDays() {
        assertThrows(RuntimeException.class, () ->
                leaveService.applyLeave(1L, -3));
    }

    // 8. Apply with zero balance
    @Test
    void testApplyLeaveWithZeroBalance() {
        Employee emp = new Employee(1L, "John", "IT", 0);

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));

        assertThrows(RuntimeException.class, () ->
                leaveService.applyLeave(1L, 1));
    }

    // 9. Approve already approved
    @Test
    void testApproveAlreadyApprovedLeave() {
        Employee emp = new Employee(1L, "John", "IT", 10);
        LeaveRequest req = new LeaveRequest(1L, emp, 2, LeaveStatus.APPROVED);

        when(leaveRepo.findById(1L)).thenReturn(Optional.of(req));

        assertThrows(RuntimeException.class, () ->
                leaveService.approveLeave(1L));
    }

    // 10. Approve already rejected
    @Test
    void testApproveAlreadyRejectedLeave() {
        Employee emp = new Employee(1L, "John", "IT", 10);
        LeaveRequest req = new LeaveRequest(1L, emp, 2, LeaveStatus.REJECTED);

        when(leaveRepo.findById(1L)).thenReturn(Optional.of(req));

        assertThrows(RuntimeException.class, () ->
                leaveService.approveLeave(1L));
    }

    // 11. Reject non-pending leave
    @Test
    void testRejectNonPendingLeave() {
        Employee emp = new Employee(1L, "John", "IT", 10);
        LeaveRequest req = new LeaveRequest(1L, emp, 2, LeaveStatus.APPROVED);

        when(leaveRepo.findById(1L)).thenReturn(Optional.of(req));

        assertThrows(RuntimeException.class, () ->
                leaveService.rejectLeave(1L));
    }
}
