
package com.marin.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBAccess {

	public static void main(String[] args) {

		Connection conn = null;

		try {
			String userName = "root";
			String password = "testing";
			String url = "jdbc:mysql://192.168.171.136:3306/test";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);

			System.out.println("Database connection established");
			Statement s = conn.createStatement();
			int count;
			// s.executeUpdate
			// ("Update student set email='abcded@gmail.com' where id=3");
			s.executeQuery("SELECT * FROM student");
			ResultSet rs = s.getResultSet();
			while (rs.next()) {
				int idVal = rs.getInt("id");
				String nameVal = rs.getString("name");
				String catVal = rs.getString("email");
				System.out.println("id = " + idVal + ", name = " + nameVal +
					", email = " + catVal);
				// ++count;
			}
			rs.close();
			s.close();

			s.close();
			// System.out.println (count + " rows were inserted");

		}
		catch (Exception e) {
			System.err.println("Cannot connect to database server");
			System.out.println(e);
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
					System.out.println("Database connection terminated");
				}
				catch (Exception e) { /* ignore close errors */
				}

			}
		}
	}
}
