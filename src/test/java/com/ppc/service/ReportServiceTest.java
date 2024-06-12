package com.ppc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.EmployeeCountResponse;
import com.example.demo.dto.MonthlyJoinExitReportResponse;
import com.example.demo.entity.EmployeeDetails;
import com.example.demo.entity.Event;
import com.example.demo.entity.Organization;
import com.example.demo.repository.EmployeeDetailsRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.service.ReportService;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

	@Mock
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Mock
	private EventRepository eventRepository;

	@Mock
	private OrganizationRepository organizationRepository;

	@InjectMocks
	private ReportService reportService;

	@BeforeEach
	void setUp() {
	}

	@Test
	void testGetEmployeeCount() {
		Long orgId = 1L;
		when(employeeDetailsRepository.countByOrganizationOrgId(orgId)).thenReturn(5);
		EmployeeCountResponse result = reportService.getEmployeeCount(orgId);
		assertEquals(1, result.getOrganizationId());
		assertEquals(5L, result.getEmployeeCount());
	}

	//@Test
	void testGetMonthlyJoinExitReport() {
		Long orgId = 1L;
		List<EmployeeDetails> employees = Arrays.asList(
				new EmployeeDetails(1L, "Emp1", "John", "Doe", "Manager", new Organization(1L, "Org1"), null),
				new EmployeeDetails(2L, "Emp2", "Jane", "Smith", "Developer", new Organization(1L, "Org1"), null));
		List<Event> events = Arrays.asList(new Event(1L, "ONBOARD", new Date(), 0.0, "Notes1", employees.get(0)),
				new Event(2L, "EXIT", new Date(), 0.0, "Notes2", employees.get(0)),
				new Event(3L, "ONBOARD", new Date(), 0.0, "Notes3", employees.get(1)));
		when(employeeDetailsRepository.findByOrganizationOrgId(orgId)).thenReturn(employees);
		when(eventRepository.findAll()).thenReturn(events);

		List<MonthlyJoinExitReportResponse> result = reportService.getMonthlyJoinExitReport(orgId);

		assertEquals(2, result.size());
		assertEquals("01-1970", result.get(0).getMonth());
		assertEquals(1, result.get(0).getJoinedEmployees().size());
		assertEquals(1, result.get(0).getExitedEmployees().size());
		assertEquals("Emp1", result.get(0).getJoinedEmployees().get(0).getEmpId());
		assertEquals("Emp1", result.get(0).getExitedEmployees().get(0).getEmpId());
	}

}
