
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col col-lg-4">

	<%
		if (request.getServletPath().equals("/newContact.jsp")) {
	%>
	<form action="insert" method="post">
		<%
			} else {
		%>
		<form action="update" method="post">

			<%
				}
			%>
			<div class="form-group">
				<label for="exampleInputEmail1">Emp ID</label> <input type="text"
					class="form-control" name="empID" id="infoEmpID">
			</div>

			<div class="form-group">
				<label for="exampleInputEmail1">Name</label> <input type="text"
					class="form-control" name="name" id="infoName">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Mobile NO</label> <input type="text"
					class="form-control" name="mobileNo" id="infoMobileNo">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">SPEED DIAL NO</label> <input
					type="text" class="form-control" name="speedDialNo"
					id="infoSpeedDialNo">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Email</label> <input type="text"
					class="form-control" name="email" id="infoEmail">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Department</label> <input
					type="text" class="form-control" name="dept" id="infoDept">
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
</div>