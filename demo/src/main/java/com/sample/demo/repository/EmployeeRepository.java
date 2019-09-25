package com.sample.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sample.demo.bean.Employee;
import com.sample.demo.utils.ConnectionFactory;

@Repository
public class EmployeeRepository {
	@Autowired
	ConnectionFactory connectionFactory;

	public List<Employee> findAllEmployees() {
		Connection myConnection = connectionFactory.getConnection();
		Statement myStatement = null;
		ResultSet myResult = null;
		List<Employee> employeeList = new ArrayList<>();
		Employee employee = null;

		try {
			myStatement = myConnection.createStatement();
			myResult = myStatement.executeQuery("select * from employee");
			while (myResult.next()) {
				employee = new Employee(myResult.getString(1), myResult.getString(2), myResult.getString(3));
				employeeList.add(employee);
			}
		} catch (SQLException e) {
			System.out.println("Database is empty");
		} finally {
			try {
				if (myResult != null) {
					myResult.close();
					myResult = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				if (myStatement != null) {
					myStatement.close();
					myStatement = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		connectionFactory.closeConnection(myConnection);
		return employeeList;

	}
	
	public Employee findEmployeeByUserId(String userId) {
		Connection myConnection = connectionFactory.getConnection();
		PreparedStatement myStatement = null;
		ResultSet myResult = null;
		boolean hasRecords = false;
		Employee employee = null;
		try {
			myStatement = myConnection.prepareStatement("select * FROM employee WHERE Id = ?");
			myStatement.setString(1, userId);
			myResult = myStatement.executeQuery();
			if (myResult.next()) {
				employee = new Employee(myResult.getString(1), myResult.getString(2), myResult.getString(3));
				hasRecords = true;
			}
		} catch (SQLException e) {
			System.out.println("User id does not exist.");
		} finally {
			try {
				if (myResult != null) {
					myResult.close();
					myResult = null;
				}
			} catch (Exception e) {
				System.out.println("User id does not exist.");
			}
			try {
				if (myStatement != null) {
					myStatement.close();
					myStatement = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (!hasRecords) {
			System.out.println("The database is empty.\n");
		}
		connectionFactory.closeConnection(myConnection);
		return employee;
	}
	
	public List<Employee> findEmployeeByFirstName(String firstName) {
		Connection myConnection = connectionFactory.getConnection();
		PreparedStatement myStatement = null;
		ResultSet myResult = null;
		boolean hasRecords = false;
		Employee employee = null;
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			myStatement = myConnection.prepareStatement("select * FROM employee WHERE FIRST_NAME = ?");
			myStatement.setString(1, firstName);
			myResult = myStatement.executeQuery();
			while (myResult.next()) {
				employee = new Employee(myResult.getString(1), myResult.getString(2), myResult.getString(3));
				employeeList.add(employee);
				hasRecords = true;
			}
		} catch (SQLException e) {
			System.out.println("User with this First Name does not exist.");
		} finally {
			try {
				if (myResult != null) {
					myResult.close();
					myResult = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				if (myStatement != null) {
					myStatement.close();
					myStatement = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (!hasRecords) {
			System.out.println("The database is empty.\n");
		}
		connectionFactory.closeConnection(myConnection);
		return employeeList;
	}
	
	public List<Employee> findEmployeeByLastName(String lastName) {
		Connection myConnection = connectionFactory.getConnection();
		PreparedStatement myStatement = null;
		ResultSet myResult = null;
		boolean hasRecords = false;
		Employee employee = null;
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			myStatement = myConnection.prepareStatement("select * FROM employee WHERE LAST_NAME = ?");
			myStatement.setString(1, lastName);
			myResult = myStatement.executeQuery();
			while (myResult.next()) {
				employee = new Employee(myResult.getString(1), myResult.getString(2), myResult.getString(3));
				employeeList.add(employee);
				hasRecords = true;
			}
		} catch (SQLException e) {
			System.out.println("User with the Last Name does not exist.");
		} finally {
			try {
				if (myResult != null) {
					myResult.close();
					myResult = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				if (myStatement != null) {
					myStatement.close();
					myStatement = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (!hasRecords) {
			System.out.println("The database is empty.\n");
		}
		connectionFactory.closeConnection(myConnection);
		return employeeList;
	}

	public boolean findEmployee(Employee employee) {
		boolean isAdded = false;
		Connection myConnection = connectionFactory.getConnection();
		PreparedStatement myPreparedStatement = null;
		try {
			myPreparedStatement = myConnection.prepareStatement("insert into employee values (?,?,?)");
			myPreparedStatement.setInt(1, Integer.parseInt(employee.getUserId()));
			myPreparedStatement.setString(2, employee.getFirstName());
			myPreparedStatement.setString(3, employee.getLastName());
			int noOfRecordsInserted =  myPreparedStatement.executeUpdate();
			isAdded = true;
			System.out.println("\nThe employee with the '" + employee.getUserId() + "' has been added into the databases.\n");
		} catch (SQLException e) {
			isAdded = false;
			System.out.println("Employee with user id already exist.");
		} finally {

			try {
				if (myPreparedStatement != null) {
					myPreparedStatement.close();
					myPreparedStatement = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		connectionFactory.closeConnection(myConnection);
		return isAdded;
	}
	
}
