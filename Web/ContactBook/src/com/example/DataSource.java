package com.example;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {

	private static DataSource datasource;
	private ComboPooledDataSource cpds;

	private DataSource() throws IOException, SQLException,
			PropertyVetoException {
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver"); // loads the jdbc driver
		cpds.setJdbcUrl("jdbc:mysql://localhost/contactbook");
		cpds.setUser("root");
		cpds.setPassword("kartik");
		// for clg server
		// cpds.setPassword("mysql");

		// // the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(180);

	}

	public static DataSource getInstance() {
		if (datasource == null) {
			try {
				datasource = new DataSource();
			} catch (IOException | SQLException | PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return datasource;
		} else {
			return datasource;
		}
	}

	public Connection getConnection() {
		try {
			return this.cpds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}