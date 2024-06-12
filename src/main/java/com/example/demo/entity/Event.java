package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {
	
    public Event() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;
    
    @Column(name = "event_type")
    private String eventType;
    
    @Column(name = "event_date")
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    
    @Column(name = "value")
    private Double value;
    
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private EmployeeDetails employeeDetails;
}
