
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
			<div style="margin:0% 0% 0% 20%;">
				<jsp:include page="info.jsp"></jsp:include>
			</div>
		</c:otherwise>
	</c:choose>


</body>
</html>