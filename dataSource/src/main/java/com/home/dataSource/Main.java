package com.home.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
	
	//1. A connection (session) with a specific database.
	static Connection conn = null;
	//2. An object that represents a precompiled SQL statement.
	// A SQL statement is precompiled and stored in a PreparedStatement object.
	// This object can then be used to efficiently execute this statement multiple times.
	static PreparedStatement stm = null;

	public static void main(String[] args) {
		
		makeJDBCConnection();
		

	}
	
	private static void makeJDBCConnection() {

		try {
			// Este string es lo que hay dentro del driver en el pom.xml
			Class.forName("com.mysql.jdbc.Driver");
			log("MySQL JDBC Driver Registered");
		} catch (ClassNotFoundException e) {
			log("JDBC driver not found");
			e.printStackTrace();
			return;
		}

		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			// Creamos la conexión (root1 = usuario, root2 = password):
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testingmaven", "root", "root");
			if (conn != null) {
				log("Connection Successfully created!");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			log("MySQL Connection crashed");
			e.printStackTrace();
			return;
		}

	}
	
	private static void log(String string) {
		System.out.println(string);

	}

}
