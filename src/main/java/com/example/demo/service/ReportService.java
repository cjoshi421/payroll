package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.EmployeeDetails;
import com.example.demo.entity.Event;
import com.example.demo.repository.EmployeeDetailsRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.demo.dto.EventType.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private OrganizationRepository organizationRepository;
    
    public List<OrganizationListResponse> getAllOrganization() {
    	return organizationRepository.findAll()
                .stream()
                .map(org -> {
                    OrganizationListResponse orgResponse = new OrganizationListResponse();
                    orgResponse.setOrgId(org.getOrgId());
                    orgResponse.setOrganizationName(org.getOrganizationName());
                    return orgResponse;
                })
                .collect(Collectors.toList());
    }

    public EmployeeCountResponse getEmployeeCount(Long orgId) {
        int count = employeeDetailsRepository.countByOrganizationOrgId(orgId);
        return new EmployeeCountResponse(orgId, count);
    }

    public List<MonthlyJoinExitReportResponse> getMonthlyJoinExitReport(Long orgId) {
        List<EmployeeDetails> employees = employeeDetailsRepository.findByOrganizationOrgId(orgId);
        Map<String, List<EmployeeDetails>> joinMap = employees.stream()
        	    .filter(emp -> emp.getEvents().stream()
        	        .anyMatch(event -> event.getEventType().equalsIgnoreCase(ONBOARD.name())))
        	    .collect(Collectors.groupingBy(emp -> {
        	        Event onboardEvent = emp.getEvents().stream()
        	            .filter(event -> event.getEventType().equalsIgnoreCase(ONBOARD.name()))
        	            .findFirst()
        	            .orElse(null);
        	        return onboardEvent != null ? getMonthYear(onboardEvent.getEventDate()) : null;
        	    }));

        Map<String, List<EmployeeDetails>> exitMap = employees.stream()
        	    .filter(emp -> emp.getEvents().stream()
        	        .anyMatch(event -> event.getEventType().equalsIgnoreCase(EXIT.name())))
        	    .collect(Collectors.groupingBy(emp -> {
        	        Event exitEvent = emp.getEvents().stream()
        	            .filter(event -> event.getEventType().equalsIgnoreCase(EXIT.name()))
        	            .findFirst()
        	            .orElse(null);
        	        return exitEvent != null ? getMonthYear(exitEvent.getEventDate()) : null;
        	    }));

        Set<String> months = new HashSet<>(joinMap.keySet());
        months.addAll(exitMap.keySet());

        return months.stream()
                .sorted()
                .map(month -> new MonthlyJoinExitReportResponse(
                        month,
                        joinMap.getOrDefault(month, Collections.emptyList()).stream()
                                .map(emp -> new MonthlyJoinExitReportResponse.EmployeeDetail(emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getDesignation()))
                                .collect(Collectors.toList()),
                        exitMap.getOrDefault(month, Collections.emptyList()).stream()
                                .map(emp -> new MonthlyJoinExitReportResponse.EmployeeDetail(emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getDesignation()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public List<MonthlySalaryReportResponse> getMonthlySalaryReport(Long orgId) {
        List<Event> salaryEvents = eventRepository.findAll().stream()
                .filter(event -> event.getEventType().equalsIgnoreCase(SALARY.name()))
                .filter(event -> event.getEmployeeDetails().getOrganization().getOrgId().equals(orgId))
                .collect(Collectors.toList());

        Map<String, List<Event>> salaryMap = salaryEvents.stream()
                .collect(Collectors.groupingBy(event -> getMonthYear(event.getEventDate())));

        return salaryMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new MonthlySalaryReportResponse(
                        entry.getKey(),
                        entry.getValue().stream().mapToDouble(Event::getValue).sum(),
                        entry.getValue().size()))
                .collect(Collectors.toList());
    }

    public List<EmployeeFinancialReportResponse> getEmployeeFinancialReport(Long orgId) {
        List<EmployeeDetails> employees = employeeDetailsRepository.findByOrganizationOrgId(orgId);

        return employees.stream()
                .map(emp -> new EmployeeFinancialReportResponse(
                        emp.getEmpId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getEvents().stream().mapToDouble(Event::getValue).sum()))
                .collect(Collectors.toList());
    }

    public List<MonthlyFinancialReportResponse> getMonthlyFinancialReport(Long orgId) {
        List<Event> financialEvents = eventRepository.findAll().stream()
                .filter(event -> Arrays.asList(SALARY.name(), BONUS.name(), REIMBURSEMENT.name()).contains(event.getEventType().toUpperCase()))
                .filter(event -> event.getEmployeeDetails().getOrganization().getOrgId().equals(orgId))
                .collect(Collectors.toList());

        Map<String, List<Event>> financialMap = financialEvents.stream()
                .collect(Collectors.groupingBy(event -> getMonthYear(event.getEventDate())));

        return financialMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new MonthlyFinancialReportResponse(
                        entry.getKey(),
                        entry.getValue().stream().mapToDouble(Event::getValue).sum(),
                        entry.getValue().size()))
                .collect(Collectors.toList());
    }

    public List<YearlyFinancialReportResponse> getYearlyFinancialReport(Long orgId) {
        List<Event> financialEvents = eventRepository.findAll().stream()
                .filter(event -> Arrays.asList(SALARY.name(), BONUS.name(), REIMBURSEMENT.name()).contains(event.getEventType().toUpperCase()))
                .filter(event -> event.getEmployeeDetails().getOrganization().getOrgId().equals(orgId))
                .collect(Collectors.toList());

        return financialEvents.stream()
                .sorted(Comparator.comparing(Event::getEventDate))
                .map(event -> new YearlyFinancialReportResponse(
                        event.getEventType(),
                        event.getEmployeeDetails().getEmpId(),
                        event.getEventDate(),
                        event.getValue()))
                .collect(Collectors.toList());
    }

    private String getMonthYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return String.format("%02d-%d", month, year);
    }
}
