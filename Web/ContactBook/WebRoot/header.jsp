
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navbar navbar-default ">

	<ul class="nav nav-pills" style="margin-top:0.5%">
		<li style="width: 40%;"><a href="index.jsp"><img
				src="resources/images/logo.png" height="12%" width="24%" alt="logo"><span
				style="color:#0C96C8;font-weight:bold;font-size:2em;text-decoration:none;">Contact
					Book</span></a></li>

		<c:choose>
			<c:when test="${sessionScope.username == null }">
				<ul class="nav navbar-right nav-pills"
					style="margin-right:5%;margin-top: 2% ">
					<li role="presentation" class="active" style="align:right;"><a
						href="index.jsp">SignUp</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<li role="presentation"
					<%if (request.getServletPath().equals("/pendingRequest.jsp")) {%>
					class="active" <%}%>><a href="pendingRequest.jsp">Pending
						Request</a></li>
				<li role="presentation"
					<%if (request.getServletPath().equals("/upload.jsp")) {%>
					class="active" <%}%>><a href="upload.jsp">Upload Excel</a></li>
				<li role="presentation"
					<%if (request.getServletPath().equals("/deleteContact.jsp")) {%>
					class="active" <%}%>><a href="deleteContact.jsp">Delete
						Contacts</a></li>
				<li role="presentation"
					<%if (request.getServletPath().equals("/newContact.jsp")) {%>
					class="active" <%}%>><a href="newContact.jsp">New Contact</a></li>
				<li role="presentation"
					<%if (request.getServletPath().equals("/index.jsp")) {%>
					class="active" <%}%>><a href="index.jsp">Edit Contact</a></li>

				<li role="presentation"><a href="logout.jsp">Logout</a></li>
			</c:otherwise>
		</c:choose>


	</ul>


	<!-- 
	
			</ul>


 -->
</div>


