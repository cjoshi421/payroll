package com.example.demo.repository;

import com.example.demo.entity.EmployeeDetails;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

	List<EmployeeDetails> findByOrganizationOrgId(Long orgId);

	int countByOrganizationOrgId(Long orgId);

	EmployeeDetails findByEmpId(String empId);
}
