package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalaryReportResponse {
   
	private String month;
    private double totalSalary;
    private int totalEmployees;
}
