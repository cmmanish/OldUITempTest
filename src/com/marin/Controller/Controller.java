
package com.marin.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class Controller {

	static String host = "192.168.171.136";
	static String Database = "qa_automation";
	static String userName = "root";
	static String password = "root";

	static Logger log = Logger.getLogger(Controller.class);

	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

	public static void readDatabase() {

		Connection conn = null;

		try {

			// String url = "jdbc:mysql://qa7-ubuntu/marin_qa_automation";
			String url = "jdbc:mysql://" + host + "/" + Database + "";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);

			log.info("Database connection established");
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM test_execution_record");
			ResultSet rs = s.getResultSet();
			int count = 0;
			while (rs.next()) {

				String testSuite = rs.getString("test_suite");
				String testCaseId = rs.getString("test_case_id");
				String result = rs.getString("result");
				String user_alias = rs.getString("user_alias");
				String user_agent = rs.getString("user_agent");

				count++;
				log.info("-------------------------------");
				System.out.print(" testSuite " + testSuite);
				System.out.print(" testCaseId " + testCaseId);
				System.out.print(" result " + result);
				System.out.print(" user_alias " + user_alias);
				log.info(" user_agent " + user_agent);

			}
			rs.close();
			s.close();

			s.close();
		}
		catch (Exception e) {
			System.err.println("Cannot connect to database server");
			log.info(e);
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
					log.info("Database connection terminated");
				}
				catch (Exception e) { /* ignore close errors */
				}

			}
		}
	}

	public static void insertRecordIntoTable(
		String testSuite, String testCaseId, String result, String userAlias,
		String userAgent) {

		Connection dbConnection = null;

		try {

			// String url = "jdbc:mysql://qa7-ubuntu/marin_qa_automation";
			String url = "jdbc:mysql://" + host + "/" + Database + "";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbConnection = DriverManager.getConnection(url, userName, password);

			log.info("Database connection established  ");

			PreparedStatement preparedStatement = null;

			String insertTableSQL =
				"INSERT INTO test_execution_record"
					+ "(start_time, end_time, test_suite, test_case_id,result ,user_alias, user_agent ) VALUES"
					+ "(?,?,?,?,?,?,?)";

			log.info(insertTableSQL);
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setTimestamp(1, getCurrentTimeStamp());
			preparedStatement.setTimestamp(2, getCurrentTimeStamp());

			preparedStatement.setString(3, testSuite);
			preparedStatement.setString(4, testCaseId);
			preparedStatement.setString(5, result);
			preparedStatement.setString(6, userAlias);
			preparedStatement.setString(7, userAgent);

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			log.info("Record is inserted into test_execution_record table!");

		}
		catch (SQLException e) {

			log.info(e.getMessage());

		}
		catch (Exception e) {
			System.err.println("Cannot connect to database server");
			log.info(e);
		}
		finally {
			if (dbConnection != null) {
				try {
					dbConnection.close();
					log.info("Database " + " connection terminated");
				}
				catch (Exception e) { /* ignore close errors */
				}

			}
		}
	}

	public static void main(String[] args)
		throws InterruptedException {

		Controller.insertRecordIntoTable("3", "3", "FAIL", "manish", "firefox");
		Controller.readDatabase();

	}
}
