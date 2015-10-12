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

public class UploadServletRefresh extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static public String SQL = "INSERT INTO CONTACTBOOK (EMP_ID,NAME,MOBILE_NO,SPEED_DIAL_NO,EMAIL,DEPT) VALUES (?,?,?,?,?,?)";
	static public String SQL_DELETE = "DELETE FROM CONTACTBOOK";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		// Connection connection = (Connection) getServletContext()
		// .getAttribute(MyListener.CONNECTION);

		Connection connection = DataSource.getInstance().getConnection();
		try {
			try (PreparedStatement preparedStatemen = (PreparedStatement) connection
					.prepareStatement(SQL_DELETE)) {
				int delete = preparedStatemen.executeUpdate();
				// System.out.println(delete);
			} catch (Exception e) {
				req.setAttribute(PublicStaticContent.RECORD_INSERTED, false);
				req.setAttribute(
						PublicStaticContent.RECORD_NOT_INSERTED_MESSAGE,
						e.getMessage());
			}
			saveRecords(req, connection);
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

	public static void saveRecords(HttpServletRequest req, Connection connection)
			throws Exception {
		MultipartRequest mpr = new MultipartRequest(req, req.getSession()
				.getServletContext().getRealPath("file"), 500 * 1024 * 1024);
		File file = new File(req.getSession().getServletContext()
				.getRealPath("/")
				+ "file/" + mpr.getOriginalFileName("file"));
		FileInputStream input = new FileInputStream(file);
		POIFSFileSystem fs = new POIFSFileSystem(input);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		Row row;

		try {
			connection.setAutoCommit(false);
			try (PreparedStatement preparedStatement = (PreparedStatement) connection
					.prepareStatement(SQL)) {
				// TODO making according to requirement
				for (int i = 1; i < sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);

					// mandatory
					try {
						// preparedStatement.setInt(1, i);
						preparedStatement.setString(1, row.getCell(0)
								.getStringCellValue());
					} catch (Exception e) {
						preparedStatement.setDouble(1, row.getCell(0)
								.getNumericCellValue());
					}

					// mandatory
					preparedStatement.setString(2, row.getCell(1)
							.getStringCellValue());

					// not mandatory
					if (row.getCell(2) != null) {
						try {
							preparedStatement.setString(3, row.getCell(2)
									.getStringCellValue());
						} catch (Exception e) {
							preparedStatement.setDouble(3, row.getCell(2)
									.getNumericCellValue() + i);
						}
					} else {
						preparedStatement.setString(3, null);
					}

					Cell starNo = row.getCell(3);
					if (starNo != null) {
						preparedStatement.setString(4,
								starNo.getStringCellValue());
					} else {
						preparedStatement.setString(5, null);
					}

					Cell emailRow = row.getCell(4);
					if (emailRow != null) {
						preparedStatement.setString(5,
								emailRow.getStringCellValue());
					} else {
						preparedStatement.setString(5, null);
					}

					// Cell deptRow = row.getCell(5);
					// if (deptRow != null) {
					// preparedStatement.setString(6,
					// deptRow.getStringCellValue());
					// } else {
					// preparedStatement.setString(6, null);
					// }

					Cell deptRow = row.getCell(5);
					if (deptRow != null) {
						String dept = deptRow.getStringCellValue();
						if (dept.contains("(")) {
							dept = dept.substring(dept.indexOf("(") + 1,
									dept.indexOf(")"));
						}
						// System.out.println(dept);
						preparedStatement.setString(6, dept);
					} else {
						preparedStatement.setString(6, null);
					}

					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
				connection.commit();
				input.close();
				req.setAttribute(PublicStaticContent.RECORD_INSERTED, true);
			} catch (SQLException ex) {
				ex.printStackTrace();
				req.setAttribute(PublicStaticContent.RECORD_INSERTED, false);
				req.setAttribute(
						PublicStaticContent.RECORD_NOT_INSERTED_MESSAGE,
						ex.getMessage());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			req.setAttribute(PublicStaticContent.RECORD_INSERTED, false);
			req.setAttribute(PublicStaticContent.RECORD_NOT_INSERTED_MESSAGE,
					ex.getMessage());
		}

	}
}