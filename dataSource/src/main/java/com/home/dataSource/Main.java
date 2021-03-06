package com.home.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.home.dataSource.dao.userDao;

public class Main {
	
	//1. A connection (session) with a specific database.
	static Connection conn = null;
	//2. An object that represents a precompiled SQL statement.
	// A SQL statement is precompiled and stored in a PreparedStatement object.
	// This object can then be used to efficiently execute this statement multiple times.
	static PreparedStatement stm = null;

	public static void main(String[] args) {
		
		makeJDBCConnection();
		
		userDao user = new userDao("Raúl", "González", "696654841");
		try {
			addDataToDB(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*List<userDao> firstIndex = getDataFromDB();
		if (firstIndex!=null){
			for (userDao item : firstIndex) {
				System.out.println(item.toString());
			}
		}*/

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
			// BBDD at home: conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testingmaven", "root", "root");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/secondserver", "root", "root");
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
	
	private static void addDataToDB(userDao userdao) throws SQLException {

		String insertQueryStatement = "INSERT INTO `serverusers`(`firstname`, `lastname`, `phonenumber`) VALUES (?,?,?)";

		stm = conn.prepareStatement(insertQueryStatement);
		stm.setString(1, userdao.getFirstname());
		stm.setString(2, userdao.getLastname());
		stm.setString(3, userdao.getPhonenumber());
		stm.executeUpdate();
		log(userdao.toString() + " added successfully!");

	}

	/*private static List<userDao> getDataFromDB() {
		List<userDao> upcomingUsers = new ArrayList<userDao>();
		try {
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM users";

			stm = conn.prepareStatement(getQueryStatement);
			// A table of data representing a database result set, which is usually generated by executing a statement that queries the database.
			// A ResultSet object maintains a cursor pointing to its current row of data. Initially the cursor is positioned before the first row.
			// The next method moves the cursor to the next row, and because it returns false when there are no more rows in the ResultSet object, it can be used in a while loop to iterate through the result set.
			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				int id = rs.getInt("id");
				String phonenumber = rs.getString("phonenumber");
				userDao registeredUser = new userDao(id, firstname, lastname, phonenumber);
				upcomingUsers.add(registeredUser);

			}

		} catch (

		SQLException e) {
			upcomingUsers = null;
			e.printStackTrace();
		}

		return upcomingUsers;

	}*/
	
	private static void log(String string) {
		System.out.println(string);

	}

}
