package com.sample.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sample.demo.bean.Employee;

public interface EmployeeRepositoryJPA extends CrudRepository<Employee, String> {
	
	
	public List<Employee> findAll();
	
	//public Employee findById(String id);

	public Employee findByUserIdAndFirstName(String id, String firstName);
	
	public Employee findEmployeeByUserId(String id);
	
	public List<Employee> findEmployeeByFirstName(String firstName);
	
	public List<Employee> findEmployeeByLastName(String lastName);
		
}
