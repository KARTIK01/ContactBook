package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static public String SQL = "SELECT * FROM  USER WHERE USERNAME = ? and PASSWORD = ?";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		// Connection connection = (Connection)
		// getServletContext().getAttribute(
		// MyListener.CONNECTION);

		Connection connection = DataSource.getInstance().getConnection();

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(SQL)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				// System.out.println(rs.getString(2));
				req.getSession().setAttribute(PublicStaticContent.USER_NAME,
						rs.getString(2));
				res.sendRedirect("index.jsp");
			} else {
				req.setAttribute(PublicStaticContent.LOGIN, false);
				req.setAttribute(PublicStaticContent.LOGIN_FALSE_MESSAGE,
						"Incorrect UserName or Password");
				req.getRequestDispatcher("/index.jsp").forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("Error_Message",
					PublicStaticContent.stackTraceToString(e));
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}