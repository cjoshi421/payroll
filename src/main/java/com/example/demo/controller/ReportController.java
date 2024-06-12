package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeCountResponse;
import com.example.demo.dto.EmployeeFinancialReportResponse;
import com.example.demo.dto.MonthlyFinancialReportResponse;
import com.example.demo.dto.MonthlyJoinExitReportResponse;
import com.example.demo.dto.MonthlySalaryReportResponse;
import com.example.demo.dto.OrganizationListResponse;
import com.example.demo.dto.YearlyFinancialReportResponse;
import com.example.demo.service.ReportService;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/organizations")
    public List<OrganizationListResponse> getAllOrganization() {
        return reportService.getAllOrganization();
    }
    
    @GetMapping("/report/organization/{orgId}/employee-count")
    public EmployeeCountResponse getEmployeeCount(@PathVariable Long orgId) {
        return reportService.getEmployeeCount(orgId);
    }

    @GetMapping("/report/organization/{orgId}/monthly-join-exit")
    public List<MonthlyJoinExitReportResponse> getMonthlyJoinExitReport(@PathVariable Long orgId) {
        return reportService.getMonthlyJoinExitReport(orgId);
    }

    @GetMapping("/report/organization/{orgId}/monthly-salary")
    public List<MonthlySalaryReportResponse> getMonthlySalaryReport(@PathVariable Long orgId) {
        return reportService.getMonthlySalaryReport(orgId);
    }

    @GetMapping("/report/organization/{orgId}/employee-financial")
    public List<EmployeeFinancialReportResponse> getEmployeeFinancialReport(@PathVariable Long orgId) {
        return reportService.getEmployeeFinancialReport(orgId);
    }

    @GetMapping("/report/organization/{orgId}/monthly-financial")
    public List<MonthlyFinancialReportResponse> getMonthlyFinancialReport(@PathVariable Long orgId) {
        return reportService.getMonthlyFinancialReport(orgId);
    }

    @GetMapping("/report/organization/{orgId}/yearly-financial")
    public List<YearlyFinancialReportResponse> getYearlyFinancialReport(@PathVariable Long orgId) {
        return reportService.getYearlyFinancialReport(orgId);
    }
}
