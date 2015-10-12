package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PendingRequestCompleting extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static String INSERT = "insert into contactbook select * from newcontactbook where newcontactbook.EMP_ID = ?";
	public static String DELETE = "DELETE FROM NEWCONTACTBOOK WHERE EMP_ID = ?";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] empId = req.getParameterValues("empId");
		insert(req, resp, empId);
		delete(req, resp, empId);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		// RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
		// rd.forward(req, resp);
		// return;
	}

	public static void delete(HttpServletRequest req, HttpServletResponse res,
			String empId[]) {
		Connection connection = DataSource.getInstance().getConnection();

		try {
			connection.setAutoCommit(false);
			try (PreparedStatement preparedStatement = connection
					.prepareStatement(DELETE)) {
				for (int i = 0; i < empId.length; i++) {
					preparedStatement.setString(1, empId[i]);
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
				connection.commit();
				req.setAttribute(PublicStaticContent.RECORD_UPDATED, true);
			} catch (SQLException e) {
				e.printStackTrace();
				req.setAttribute(PublicStaticContent.RECORD_UPDATED, false);
				req.setAttribute(PublicStaticContent.RECORD_UPDATED,
						e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute(PublicStaticContent.RECORD_UPDATED, false);
			req.setAttribute(PublicStaticContent.RECORD_UPDATED, e.getMessage());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insert(HttpServletRequest req, HttpServletResponse res,
			String empId[]) {
		Connection connection = DataSource.getInstance().getConnection();
		try {
			connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection
					.prepareStatement(INSERT)) {
				for (int i = 0; i < empId.length; i++) {
					preparedStatement.setString(1, empId[i]);
					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
