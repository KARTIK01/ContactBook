package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertNew extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static public String NEWSQL = "INSERT INTO NEWCONTACTBOOK (EMP_ID,NAME,MOBILE_NO,SPEED_DIAL_NO,EMAIL,DEPT) VALUES (?,?,?,?,?,?)";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String id = req.getParameter("empID");
		String name = req.getParameter("name");
		String mobileNo = req.getParameter("mobileNo");
		String speedDialNo = req.getParameter("speedDialNo");
		String email = req.getParameter("email");
		String dept = req.getParameter("dept");

		Connection connection = DataSource.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(NEWSQL)) {
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, mobileNo);
			preparedStatement.setString(4, speedDialNo);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, dept);
			int result = preparedStatement.executeUpdate();
			if (result != 0) {
				req.setAttribute(PublicStaticContent.RECORD_INSERTED, true);
			} else {
				req.setAttribute(PublicStaticContent.RECORD_INSERTED, false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			req.setAttribute(PublicStaticContent.RECORD_INSERTED, false);
			req.setAttribute(PublicStaticContent.RECORD_NOT_INSERTED_MESSAGE,
					e.getMessage());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}