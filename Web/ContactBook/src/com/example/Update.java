package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Update extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static public String SQL = "UPDATE CONTACTBOOK SET MOBILE_NO = ? , SPEED_DIAL_NO = ? ,EMAIL = ? , DEPT = ? WHERE EMP_ID = ? ";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String id = req.getParameter("empID");
		String mobileNo = req.getParameter("mobileNo");
		String speedDialNo = req.getParameter("speedDialNo");
		String email = req.getParameter("email");
		String dept = req.getParameter("dept");
		// Connection connection = (Connection)
		// getServletContext().getAttribute(
		// MyListener.CONNECTION);

		Connection connection = DataSource.getInstance().getConnection();

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(SQL)) {
			preparedStatement.setString(1, mobileNo);
			preparedStatement.setString(2, speedDialNo);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, dept);
			preparedStatement.setString(5, id);
			int result = preparedStatement.executeUpdate();
			if (result != 0) {
				req.setAttribute(PublicStaticContent.RECORD_UPDATED, true);
			} else {
				req.setAttribute(PublicStaticContent.RECORD_UPDATED, false);
			}
		} catch (SQLException e) {
			req.setAttribute(PublicStaticContent.RECORD_UPDATED, false);
			req.setAttribute(PublicStaticContent.RECORD_NOT_INSERTED_MESSAGE,
					e.getMessage());
			e.printStackTrace();
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