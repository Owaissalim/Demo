package com.sample.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.demo.bean.Employee;
import com.sample.demo.repository.EmployeeRepository;
import com.sample.demo.repository.EmployeeRepositoryJPA;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeRepositoryJPA employeeRepositoryJPA;
	
	//without JPA
//	public List<Employee> fetchAllEmployees() {
//		
//		List<Employee> employee = employeeRepository.fetchAllEmployees(); // other
//		bussinnes logic // filter out password // make entry in logger return
//		employee;
//		 
//		return employees;
//	}
	//without JPA
//	public Employee findEmployeeByUserId(String userId) {
//		Employee employee = employeeRepository.findEmployeeByUserId(userId);
//		return employee;
//	}

	// without JPA
//	public List<Employee> findEmployeeByFirstName(String firstName) {
//		List<Employee> employeeList= employeeRepository.findEmployeeByFirstName(firstName);
//		return employeeList;
//	}

	//without JPA
//	public List<Employee> findEmployeeBylastName(String lastName) {
//		List<Employee> employeeList = employeeRepository.findEmployeeByLastName(lastName);
//		return employeeList;
//	}
	
	public List<Employee> findAllEmployees() throws Exception {
		List<Employee> employeeList = employeeRepositoryJPA.findAll();
		if(employeeList.isEmpty()) {
			throw new Exception("The database is Empty");
		}
		return employeeList;
	}
	
	public Employee findEmployeeByUserId(String userId) throws Exception {
		if(!employeeRepositoryJPA.existsById(userId))
			throw new Exception("User Id " + userId + " does not exist");
		Employee employee = employeeRepositoryJPA.findEmployeeByUserId(userId);
		return employee;
	}
	
	public List<Employee> findEmployeeByFirstName(String firstName) throws Exception {
		List<Employee> employeeList = employeeRepositoryJPA.findEmployeeByFirstName(firstName);
		if(employeeList.isEmpty()){
			throw new Exception("First Name " + firstName + " does not exist");
		}
		return employeeList;
	}
	
	public List<Employee> findEmployeeByLastName(String lastName) throws Exception{
		List<Employee> employeeList = employeeRepositoryJPA.findEmployeeByLastName(lastName);
		if(employeeList.isEmpty()){
			throw new Exception("Last Name " + lastName + " does not exist");
		}
		return employeeList;
	}

	@Transactional
	public boolean insertEmployee(Employee employee) throws Exception {	
		if(employeeRepositoryJPA.existsById(employee.getUserId()))
//			System.out.println("ID ALREADY EXIST");
			throw new Exception("User Id " + employee.getUserId() + " already exist");
//		throw new Exception();
		
		Employee Obj = employeeRepositoryJPA.save(employee);
	//	boolean isAdded = employeeRepository.insertEmployee(employee);
		return true;
	}
	
	@Transactional
	public boolean updateEmployee(Employee employee) throws Exception {	
		if(!employeeRepositoryJPA.existsById(employee.getUserId()))
			throw new Exception("User Id " + employee.getUserId() + " does not exist");
		
		Employee Obj = employeeRepositoryJPA.save(employee);
	//	boolean isAdded = employeeRepository.insertEmployee(employee);
		return true;
	}

	public boolean deleteEmployee(String userId) throws Exception {
		if(!employeeRepositoryJPA.existsById(userId)) {
			throw new Exception("User Id " + userId + " does not exist");
		}
		Employee employee = employeeRepositoryJPA.findEmployeeByUserId(userId);

		employeeRepositoryJPA.delete(employee);
		return true;
	}
}
