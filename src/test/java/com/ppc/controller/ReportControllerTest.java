package com.ppc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.controller.ReportController;
import com.example.demo.dto.EmployeeFinancialReportResponse;
import com.example.demo.dto.MonthlyFinancialReportResponse;
import com.example.demo.dto.MonthlyJoinExitReportResponse;
import com.example.demo.dto.MonthlyJoinExitReportResponse.EmployeeDetail;
import com.example.demo.dto.MonthlySalaryReportResponse;
import com.example.demo.dto.OrganizationListResponse;
import com.example.demo.dto.YearlyFinancialReportResponse;
import com.example.demo.service.ReportService;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testGetAllOrganization() {
        List<OrganizationListResponse> organizationList = new ArrayList<>();
        organizationList.add(new OrganizationListResponse(1L, "Org1"));
        organizationList.add(new OrganizationListResponse(2L, "Org2"));
        when(reportService.getAllOrganization()).thenReturn(organizationList);
        List<OrganizationListResponse> result = reportController.getAllOrganization();
        assertEquals(organizationList.size(), result.size());
        assertEquals(organizationList.get(0).getOrgId(), result.get(0).getOrgId());
        assertEquals(organizationList.get(0).getOrganizationName(), result.get(0).getOrganizationName());
        assertEquals(organizationList.get(1).getOrgId(), result.get(1).getOrgId());
        assertEquals(organizationList.get(1).getOrganizationName(), result.get(1).getOrganizationName());
    }

    @Test
    public void testGetMonthlyJoinExitReport() {
        Long orgId = 1L;
        List<MonthlyJoinExitReportResponse> reportList = new ArrayList<>();
        MonthlyJoinExitReportResponse monthlyJoinExitReportResponse = new MonthlyJoinExitReportResponse();
        List<EmployeeDetail> empDetailsList = new ArrayList<>();
        EmployeeDetail employee = new EmployeeDetail();
        employee.setDesignation("SE");
        employee.setEmpId("emp101");
        employee.setFirstName("Test Fname");
        employee.setLastName("Test Lname");
        empDetailsList.add(employee);
        monthlyJoinExitReportResponse.setExitedEmployees(empDetailsList);
        monthlyJoinExitReportResponse.setJoinedEmployees(empDetailsList);
        monthlyJoinExitReportResponse.setMonth("2022-01");
        reportList.add(monthlyJoinExitReportResponse);
        when(reportService.getMonthlyJoinExitReport(orgId)).thenReturn(reportList);
        List<MonthlyJoinExitReportResponse> result = reportController.getMonthlyJoinExitReport(orgId);

        assertEquals(reportList.size(), result.size());
        assertEquals(reportList.get(0).getMonth(), result.get(0).getMonth());
    }
    
    @Test
    public void testGetMonthlySalaryReport() {
        Long orgId = 1L;
        List<MonthlySalaryReportResponse> reportList = new ArrayList<>();
        reportList.add(new MonthlySalaryReportResponse("2022-01", 10000.00, 3));
        reportList.add(new MonthlySalaryReportResponse("2022-02", 12000.00, 3));
        when(reportService.getMonthlySalaryReport(orgId)).thenReturn(reportList);
        List<MonthlySalaryReportResponse> result = reportController.getMonthlySalaryReport(orgId);
        
        assertEquals(reportList.size(), result.size());
        assertEquals(reportList.get(0).getMonth(), result.get(0).getMonth());
        assertEquals(reportList.get(0).getTotalSalary(), result.get(0).getTotalSalary());
        assertEquals(reportList.get(0).getTotalEmployees(), result.get(0).getTotalEmployees());
        assertEquals(reportList.get(1).getMonth(), result.get(1).getMonth());
        assertEquals(reportList.get(1).getTotalSalary(), result.get(1).getTotalSalary());
        assertEquals(reportList.get(0).getTotalEmployees(), result.get(0).getTotalEmployees());
    }

    @Test
    public void testGetEmployeeFinancialReport() {
        Long orgId = 1L;
        List<EmployeeFinancialReportResponse> reportList = new ArrayList<>();
        reportList.add(new EmployeeFinancialReportResponse("John Doe", 50000.00));
        reportList.add(new EmployeeFinancialReportResponse("Jane Smith", 60000.00));
        when(reportService.getEmployeeFinancialReport(orgId)).thenReturn(reportList);
        List<EmployeeFinancialReportResponse> result = reportController.getEmployeeFinancialReport(orgId);

        assertEquals(reportList.size(), result.size());
        assertEquals(reportList.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(reportList.get(0).getTotalAmountPaid(), result.get(0).getTotalAmountPaid());
        assertEquals(reportList.get(1).getFirstName(), result.get(1).getFirstName());
        assertEquals(reportList.get(1).getTotalAmountPaid(), result.get(1).getTotalAmountPaid());
    }
    
    @Test
    public void testGetMonthlyFinancialReport() {
        Long orgId = 1L;
        List<MonthlyFinancialReportResponse> reportList = new ArrayList<>();
        reportList.add(new MonthlyFinancialReportResponse("2022-01", 10000.00, 3));
        reportList.add(new MonthlyFinancialReportResponse("2022-01", 12000.00, 4));
        when(reportService.getMonthlyFinancialReport(orgId)).thenReturn(reportList);
        List<MonthlyFinancialReportResponse> result = reportController.getMonthlyFinancialReport(orgId);
        
        assertEquals(reportList.size(), result.size());
        assertEquals(reportList.get(0).getMonth(), result.get(0).getMonth());
        assertEquals(reportList.get(0).getTotalAmount(), result.get(0).getTotalAmount());
        assertEquals(reportList.get(0).getTotalEmployees(), result.get(0).getTotalEmployees());
        assertEquals(reportList.get(1).getMonth(), result.get(1).getMonth());
        assertEquals(reportList.get(1).getTotalAmount(), result.get(1).getTotalAmount());
        assertEquals(reportList.get(1).getTotalEmployees(), result.get(1).getTotalEmployees());
    }

    @Test
    public void testGetYearlyFinancialReport() {
        Long orgId = 1L;
        List<YearlyFinancialReportResponse> reportList = new ArrayList<>();
        reportList.add(new YearlyFinancialReportResponse("SALARY","emp101", new Date(), 80000.00));
        reportList.add(new YearlyFinancialReportResponse("SALARY","emp102", new Date(), 80000.00));
        when(reportService.getYearlyFinancialReport(orgId)).thenReturn(reportList);
        List<YearlyFinancialReportResponse> result = reportController.getYearlyFinancialReport(orgId);

        assertEquals(reportList.size(), result.size());
        assertEquals(reportList.get(0).getEventType(), result.get(0).getEventType());
        assertEquals(reportList.get(0).getEmpId(), result.get(0).getEmpId());
        assertEquals(reportList.get(0).getEventValue(), result.get(0).getEventValue());
        assertEquals(reportList.get(1).getEventType(), result.get(1).getEventType());
        assertEquals(reportList.get(1).getEmpId(), result.get(1).getEmpId());
        assertEquals(reportList.get(1).getEventValue(), result.get(1).getEventValue());
    }
    

}

