package com.sample.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Component;

@Component
public class ConnectionFactory {

	public Connection getConnection() {

		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test_application";
		String username = "root";
		String password = "admin";
		try {
			/*
			 * you don't need this Class.forName(), because its automatically added in the
			 * new versions
			 **/
			Class.forName(driver);

			// 1. Get a connection to database
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public void closeConnection(Connection conn) {

		try {
			if (conn != null)
				conn.close();
			conn = null;
		} catch (Exception e) {
		}
	}

}
