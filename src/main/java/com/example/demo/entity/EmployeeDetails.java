package com.example.demo.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "employee_details")
public class EmployeeDetails {
	
    public EmployeeDetails() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name="emp_id")
    private String empId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "designation")
    private String designation;

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;
    
    @OneToMany(mappedBy = "employeeDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;
}
