<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<%@page import="com.example.DataSource"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<link rel="shortcut icon"
	href="<c:url value="resources/images/contacts1.png" />" />
<script src="resources/jquery.min.js">
	
</script>
<link href="<c:url value="resources/bootstrap.min.css" />"
	rel="stylesheet" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<c:choose>
		<c:when test="${sessionScope.username ==null }">
			<jsp:include page="signup.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<!-- Content of page-->

			<div class="col col-lg-12">
				<form role="form" action="updateDelete" method="post">
					<div class="panel panel-default">
						<table id="table" class="table">
							<tr>
								<th>Select</th>
								<th>ID</th>
								<th>Name</th>
								<th>Mobile No</th>
								<th>Speed Dial No</th>
								<th>Email</th>
								<th>Dept</th>
							</tr>

							<%
								Connection connection = DataSource.getInstance()
												.getConnection();
										PreparedStatement preparedStatement = connection
												.prepareStatement("select * from newcontactbook");
										try {
											ResultSet resultSet = preparedStatement.executeQuery();
							%>

							<%
								while (resultSet.next()) {
							%>
							<tr>
								<td><input type="checkbox" name="empId"
									value="<%=resultSet.getString(1)%>" /></td>
								<td><%=resultSet.getString(1)%></td>
								<td><%=resultSet.getString(2)%></td>
								<td><%=resultSet.getString(3)%></td>
								<td><%=resultSet.getString(4)%></td>
								<td><%=resultSet.getString(5)%></td>
								<td><%=resultSet.getString(6)%></td>
							</tr>
							<%
								}
										} catch (Exception e) {
											e.printStackTrace();
										}
							%>
						</table>
					</div>
					<button type="submit" class="btn btn-default">UPDATE</button>

				</form>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>