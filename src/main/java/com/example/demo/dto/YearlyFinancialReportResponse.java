package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyFinancialReportResponse {
    private String eventType;
    private String empId;
    private Date eventDate;
    private double eventValue;
}
