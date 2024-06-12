package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.EventType;
import com.example.demo.entity.EmployeeDetails;
import com.example.demo.entity.Event;
import com.example.demo.entity.Organization;
import com.example.demo.repository.EmployeeDetailsRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganizationRepository;

@Service
public class FileProcessingService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private EventRepository eventRepository;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d-MM-yyyy");

    public void processFile(String fileName, String orgName) throws IOException {
    	
    	//first try to get organization is exit or not.
    	Organization orgFromDB = organizationRepository.findByOrganizationName(orgName);
    	if(orgFromDB == null) {
    		orgFromDB = new Organization();
    		orgFromDB.setOrganizationName(orgName);
    		orgFromDB = organizationRepository.save(orgFromDB);
    	} 
        Path path = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                //String eventType = fields[4];

                if (line.contains(EventType.ONBOARD.name())) {
                    processOnboardEvent(fields, orgFromDB);
                } else {
                    processOtherEvent(fields);
                }
            }
        }
    }

    @Transactional
    private void processOnboardEvent(String[] fields, Organization organization) {
        String empId = fields[1];
        String firstName = fields[2];
        String lastName = fields[3];
        String designation = fields[4];
        String onboardDateStr = fields[5];
        String eventDateStr = fields[6];
        String notes = fields[8];

        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmpId(empId);
        employeeDetails.setFirstName(firstName);
        employeeDetails.setLastName(lastName);
        employeeDetails.setDesignation(designation);
        employeeDetails.setOrganization(organization);
        EmployeeDetails employeeDetailsFromDB = employeeDetailsRepository.save(employeeDetails);

        if(employeeDetailsFromDB != null) {
        	Double value = new Double(0.0d);
	        Event event = new Event();
	        event.setEventType(EventType.ONBOARD.name());
	        event.setEventDate(parseDate(eventDateStr));
	        event.setNotes(notes);
	        event.setValue(value);
	        event.setEmployeeDetails(employeeDetailsFromDB);
	        eventRepository.save(event);
        }
    }

    @Transactional
    private void processOtherEvent(String[] fields) {
    	String empId = null;
    	String eventType = null;
    	Double value = new Double(0.0d);
    	String eventDateStr = null;
    	String notes = null;
        if(fields[2].contains(EventType.EXIT.name())) {
        	empId = fields[1];
        	eventType = fields[2];
        	eventDateStr = fields[3];
            notes = fields[5];
        }else {
        	empId = fields[1];
            eventType = fields[2];
            value = Double.parseDouble(fields[3]);
            eventDateStr = fields[4];
            notes = fields[5];
        }
    	

        EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmpId(empId);

        Event event = new Event();
        event.setEventType(eventType);
        event.setEventDate(parseDate(eventDateStr));
        event.setValue(value);
        event.setNotes(notes);
        event.setEmployeeDetails(employeeDetails);
        eventRepository.save(event);
    }

    public Date parseDate(String dateStr) {
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dateStr, e);
        }
    }
}
