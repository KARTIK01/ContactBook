
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

	<c:if test="${recordupdated == true }">
		<div style="color: green;">
			<h2>RECORD UPDATED</h2>
		</div>
	</c:if>
	<c:if test="${recordupdated == false }">
		<div style="color: red;">
			<h2>RECORD NOT UPDATED</h2>
			<c:out value="${recordinsertedmessage}"></c:out>
		</div>
	</c:if>
	<c:if test="${recordinserted == true }">
		<div style="color: green;">
			<h2>RECORD INSERTED</h2>
		</div>
	</c:if>
	<c:if test="${recordinserted == false }">
		<div style="color: red;">
			<h2>RECORD NOT INSERTED</h2>
			<c:out value="${recordinsertedmessage}"></c:out>
		</div>
	</c:if>


	<c:if test="${recorddeleted == true }">
		<div style="color: green;">
			<h2>RECORD DELETED</h2>
		</div>
	</c:if>
	<c:if test="${recorddeleted == false }">
		<div style="color: red;">
			<h2>RECORD NOT DELETED</h2>
			<c:out value="${deletedmessage}"></c:out>
		</div>
	</c:if>


	<c:choose>
		<c:when test="${sessionScope.username ==null }">
			<jsp:include page="signup.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="searchBar.jsp"></jsp:include>
			<div class="container fluid">
				<div class="row">
					<jsp:include page="displayResult.jsp"></jsp:include>
					<jsp:include page="info.jsp"></jsp:include>
				</div>
			</div>
		</c:otherwise>
	</c:choose>


</body>
</html>