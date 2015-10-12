package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class Search extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static public String SQL = "SELECT * FROM CONTACTBOOK WHERE EMP_ID LIKE ? OR NAME LIKE ? OR MOBILE_NO LIKE ? OR SPEED_DIAL_NO LIKE ? OR EMAIL LIKE ? OR DEPT LIKE ?";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		res.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = res.getWriter();
		String name = req.getParameter("term");
		// Connection connection = (Connection)
		// getServletContext().getAttribute(
		// MyListener.CONNECTION);

		Connection connection = DataSource.getInstance().getConnection();

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(SQL)) {
			preparedStatement.setString(1, "%" + name + "%");
			preparedStatement.setString(2, "%" + name + "%");
			preparedStatement.setString(3, "%" + name + "%");
			preparedStatement.setString(4, "%" + name + "%");
			preparedStatement.setString(5, "%" + name + "%");
			preparedStatement.setString(6, "%" + name + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			JSONArray jsonArray = new JSONArray();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("empId", resultSet.getString(1));
				jsonObject.put("name", resultSet.getString(2));
				jsonObject.put("mobileNo", resultSet.getString(3));
				jsonObject.put("speedDialNo", resultSet.getString(4));
				if (resultSet.getString(5) != null) {
					jsonObject.put("email", resultSet.getString(5));
				}
				if (resultSet.getString(6) != null) {
					jsonObject.put("dept", resultSet.getString(6));
				}
				jsonArray.put(jsonObject);
				if (jsonArray.length() > 20) {
					break;
				}
			}
			out.println(jsonArray);
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