package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyFinancialReportResponse {
    private String month;
    private double totalAmount;
    private int totalEmployees;
}
