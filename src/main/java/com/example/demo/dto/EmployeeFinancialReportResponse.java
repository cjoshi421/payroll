package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFinancialReportResponse {
    
	private String empId;
    private String firstName;
    private String lastName;
    private double totalAmountPaid;
    
    public EmployeeFinancialReportResponse(String firstName, double totalAmountPaid) {
    	this.firstName = firstName;
    	this.totalAmountPaid = totalAmountPaid;
	}
}
