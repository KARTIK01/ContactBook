package com.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {

	public static String CONNECTION = "connection";

	public void contextInitialized(ServletContextEvent contextEvent) {

		Connection con = null;
		try {
			System.out
					.println("-------- MySQL JDBC Connection Teesting ------");

			ServletContext context = contextEvent.getServletContext();
			/*
			 * Class.forName("com.mysql.jdbc.Driver");
			 * System.out.println("-------- Class loaded......... ------");
			 * 
			 * // For my computer // con = DriverManager.getConnection( //
			 * "jdbc:mysql://localhost/contactbook", "root", "kartik");
			 * 
			 * // For clg server con = DriverManager.getConnection(
			 * "jdbc:mysql://localhost/contactbook", "root", "mysql");
			 * 
			 * if (con == null) { System.out.println("CONNECTION IS NOT MADE");
			 * } else { System.out.println("-------- Connection Made ------"); }
			 * 
			 * context.setAttribute(CONNECTION, con);
			 * 
			 * System.out.println("-------- Connection SET IN CONTEXT------");
			 */

			File f = new File(context.getRealPath("/"), "file");
			boolean b = f.mkdir();
			if (b) {
				System.out.println("-------- Directory created------");
			} else {
				System.out.println("-------- Directory NOT created!!!------");
			}
			/*
			 * CREATE TABLE `notifier`.`notifier` (
			 * 
			 * `id` INT NOT NULL ,
			 * 
			 * `TITTLE` TEXT NOT NULL ,
			 * 
			 * `DESCRIPTION` TEXT NOT NULL ,
			 * 
			 * `BY` VARCHAR(45) NOT NULL ,
			 * 
			 * `DATE` DATETIME NOT NULL ,
			 * 
			 * PRIMARY KEY (`id`) );
			 */
			// PreparedStatement ps = con
			// .prepareStatement("CREATE  TABLE NOTIFIER.NOTICE ( ID INT NOT NULL ,  tittle TEXT NOT NULL  ,  description TEXT NOT NULL  ,  from TEXT NOT NULL ,  date DATETIME NULL , PRIMARY KEY (id) )");
			// ps.executeUpdate();
			// System.out.println("NOTICE TABLES CREATED");
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("project undeployed");

	}
}
