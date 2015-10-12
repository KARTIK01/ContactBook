package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.oreilly.servlet.MultipartRequest;

public class UploadServletNew extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static public String SQL = "INSERT INTO CONTACTBOOK (EMP_ID,NAME,MOBILE_NO,SPEED_DIAL_NO,EMAIL,DEPT) VALUES (?,?,?,?,?,?)";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		// Connection connection = (Connection)
		// getServletContext().getAttribute(
		// MyListener.CONNECTION);

		Connection connection = DataSource.getInstance().getConnection();

		try {
			UploadServletRefresh.saveRecords(req, connection);
		} catch (Exception ioe) {
			ioe.printStackTrace();
			req.setAttribute(PublicStaticContent.RECORD_INSERTED, false);
			req.setAttribute(PublicStaticContent.RECORD_NOT_INSERTED_MESSAGE,
					ioe.getMessage());
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