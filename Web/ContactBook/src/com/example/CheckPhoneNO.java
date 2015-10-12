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

public class CheckPhoneNO extends HttpServlet {

	static public String SQL = "SELECT * FROM CONTACTBOOK WHERE  MOBILE_NO LIKE ? ";

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String phone = request.getParameter("pNo");
		Connection connection = DataSource.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(SQL)) {
			preparedStatement.setString(1, "%" + phone + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			JSONObject jsonResponce = new JSONObject();
			if (resultSet.next()) {
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
				jsonResponce.put("data",jsonObject);
				jsonResponce.put("success","true");
			}else{
				jsonResponce.put("success","false");
			}
			out.println(jsonResponce);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("Error_Message",
					PublicStaticContent.stackTraceToString(e));
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
