package com.sample.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sample.demo.bean.Employee;
import com.sample.demo.service.EmployeeService;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;

	@GetMapping("/hello")
	public String hello() {
		return "hi this is spring boot applciation";
	}

	@GetMapping("/findAll")
	public List<Employee> findAllEmployees() {
		logger.info("Enter into findAllEmployees method");
		List<Employee> employeeList = null;
		try {
			employeeList = employeeService.findAllEmployees();
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Eception occured in findAllEmployees", e);
			// USER NOT FOUND
			// Null pointer
			// EMpty data
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
		}
		// exception -> send error message or valid data
		logger.info("Exit from findAllEmployees method " + employeeList.size());
		return employeeList;
	}

	//without JPA
//	// http://localhost:9090/employee/findEmployeeById?id=1
//	@GetMapping("/findEmployeeById/{userId}")
//	public Employee findEmployeeByUserId(@PathVariable(name = "userId") String userId) {
//		Employee employee = employeeService.findEmployeeByUserId(userId);
//		return employee;
//	}
//
	//without JPA
//	@GetMapping("/findEmployeeByFirstName/{firstName}")
//	public List<Employee> findEmployeeByFirstName(@PathVariable(name = "firstName") String firstName) {
//		List<Employee> employeeList = employeeService.findEmployeeByFirstName(firstName);
//		return employeeList;
//	}
//	
	//without JPA
//	@GetMapping("/findEmployeeByLastName/{lastName}")
//	public List<Employee> findEmployeeByLastName(@PathVariable(name = "lastName") String lastName) {
//		List<Employee> employeeList = employeeService.findEmployeeBylastName(lastName);
//		return employeeList;
//	}
	
	public List<Employee> isDatabaseEmpty() {
		List<Employee> employeeList = findAllEmployees();
		return employeeList;
	}
	
	// http://localhost:9090/employee/findEmployeeById?id=1
	@GetMapping("/findEmployeeByUserId/{userId}")
	public Employee findEmployeeByUserId(@PathVariable(name = "userId") String userId) {
		logger.info("Enter into findEmployeeByUserId method");
		Employee employee = null;
		List<Employee> employeeList = isDatabaseEmpty();
		if (employeeList != null) {
			try {
				employee = employeeService.findEmployeeByUserId(userId);
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("Eception occured in findEmployeeByUserId", e);
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserID Not Found");
			}
		}
		logger.info("Exit from findEmployeeByUserId method");
		return employee;
	}
	
	@GetMapping("/findEmployeeByFirstName/{firstName}")
	public List<Employee> findEmployeeByFirstName(@PathVariable(name = "firstName") String firstName) {
		logger.info("Enter into findEmployeeByFirstName method");
		List<Employee> employeeList = null;
		List<Employee> employeeList1 = isDatabaseEmpty();
		try {
			employeeList = employeeService.findEmployeeByFirstName(firstName);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Eception occured in findEmployeeByFirstName", e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "First Name Not Found");
		}
		logger.info("Exit from findEmployeeByFirstName method");
		return employeeList;
	}

	@GetMapping("/findEmployeeByLastName/{lastName}")
	public List<Employee> findEmployeeByLastName(@PathVariable(name = "lastName") String lastName) {
		logger.info("Enter into findEmployeeByLastName method");
		List<Employee> employeeList = null;
		List<Employee> employeeList1 = isDatabaseEmpty();
		try {
			employeeList = employeeService.findEmployeeByLastName(lastName);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Eception occured in findEmployeeByLastName", e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Last Name Not Found");
		}
		logger.info("Exit from findEmployeeByLastName method");
		return employeeList;
	}

	@RequestMapping(value = "/insertEmployee", method = RequestMethod.POST)
	public boolean insertEmployee(@RequestBody Employee employee) {
		logger.info("Enter into insertEmployee method");
		boolean isAdded = false;
		try {
			isAdded = employeeService.insertEmployee(employee);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			isAdded = false;
			logger.error("Eception occured in insertEmployee", e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, "UserID Already Exist");
			//System.out.println("Id already Exist");
		}
		logger.info("Exit from insertEmployee method");
		return isAdded;
	}
	
	@PutMapping("/updateEmployee/{userId}")
	public boolean updateEmployee(@PathVariable(value = "userId") String userId, @RequestBody Employee employee) {
		logger.info("Enter into updateEmployee method");
		boolean isAdded = false;
		try {
			isAdded = employeeService.updateEmployee(employee);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Eception occured in updateEmployee", e);
			isAdded = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserID Not Found");
		}
		logger.info("Exit from insertEmployee method");
		return isAdded;	
		}
	//AOP
	
	@RequestMapping(value = "/deleteEmployee/{userId}", method = RequestMethod.DELETE)
	public boolean deleteEmployee(@PathVariable(name = "userId") String userId) {
		logger.info("Enter into deleteEmployee method");
		boolean isRemoved = false;
		try {
			isRemoved = employeeService.deleteEmployee(userId);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println("Id already Exist");
			logger.error("Eception occured in deleteEmployee", e);
			isRemoved = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserID Not Found");
		}
		logger.info("Exit from deleteEmployee method");
		return isRemoved;
	}
	
}
