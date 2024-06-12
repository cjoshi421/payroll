package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyJoinExitReportResponse {

	private String month;
    private List<EmployeeDetail> joinedEmployees;
    private List<EmployeeDetail> exitedEmployees;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeDetail {
        private String empId;
        private String firstName;
        private String lastName;
        private String designation;
    }
}
