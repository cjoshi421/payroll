package com.ppc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.EmployeeDetails;
import com.example.demo.entity.Event;
import com.example.demo.entity.Organization;
import com.example.demo.repository.EmployeeDetailsRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.service.FileProcessingService;

@ExtendWith(MockitoExtension.class)
class FileProcessingServiceTest {

	@Mock
	private OrganizationRepository organizationRepository;

	@Mock
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Mock
	private EventRepository eventRepository;

	@InjectMocks
	private FileProcessingService fileProcessingService;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d-MM-yyyy");

	@BeforeEach
	void setUp() {
	}

	//@Test
	void testProcessFile() throws IOException {
		Organization organization = new Organization();
		organization.setOrganizationName("Org1");
		when(organizationRepository.findByOrganizationName("Org1")).thenReturn(organization);
		when(organizationRepository.save(any())).thenReturn(organization);
		String fileContent = "1,emp101,Bill,Gates,Software Engineer,ONBOARD,01-11-2022,10-10-2022,\"Bill Gates is going to join DataOrb on 1st November as a SE.\"\r\n"
				+ "\n"
				+ "6,emp103,EXIT,01-11-2022,10-10-2022,\"leaving for further study\"\r\n";
		BufferedReader bufferedReader = new BufferedReader(new StringReader(fileContent));
		Path path = mock(Path.class);
		when(path.toString()).thenReturn("test.txt");
		when(Files.newBufferedReader(path)).thenReturn(bufferedReader);

		fileProcessingService.processFile("test.txt", "Org1");
		
		verify(organizationRepository, times(1)).findByOrganizationName("Org1");
		verify(organizationRepository, times(1)).save(any());
		verify(employeeDetailsRepository, times(1)).save(any(EmployeeDetails.class));
		verify(eventRepository, times(2)).save(any(Event.class));
	}

	@Test
	void testParseDate() {
		String dateStr = "1-01-2022";
		Date date = fileProcessingService.parseDate(dateStr);
		String formattedDateStr = DATE_FORMAT.format(date);
		assertEquals(dateStr, formattedDateStr);
	}
}
