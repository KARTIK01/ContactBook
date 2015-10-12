package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Delete extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static public String SQL_DELETE = "DELETE FROM CONTACTBOOK WHERE EMP_ID = ?";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String[] empId = req.getParameterValues("empId");
		// Connection connection = (Connection)
		// getServletContext().getAttribute(
		// MyListener.CONNECTION);
		Connection connection = DataSource.getInstance().getConnection();
		try {
			connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection
					.prepareStatement(SQL_DELETE)) {
				for (int i = 0; i < empId.length; i++) {
					preparedStatement.setString(1, empId[i]);
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
				connection.commit();
				req.setAttribute(PublicStaticContent.RECORD_DELETED, true);
			} catch (SQLException e) {
				req.setAttribute(PublicStaticContent.RECORD_DELETED, false);
				req.setAttribute(
						PublicStaticContent.RECORD_NOT_DELETED_MESSAGE,
						e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute(PublicStaticContent.RECORD_DELETED, false);
			req.setAttribute(PublicStaticContent.RECORD_NOT_DELETED_MESSAGE,
					e.getMessage());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.getRequestDispatcher("/index.jsp").forward(req, res);
	}
}