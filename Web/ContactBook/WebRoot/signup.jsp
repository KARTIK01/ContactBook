<%@page session="false"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="margin:5% 35% 5% 35%;">
	<form action="login" method="post">
		<div class="form-group">
			<label for="userid">UserID</label> <input type="text"
				class="form-control" id="text" name="username"
				placeholder="Enter ID">
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				class="form-control" name="password" id="exampleInputPassword1"
				placeholder="Password">
		</div>
		<c:if test="${login == false }">
			<div style="color: red;">
				<c:out value="${loginmessage}"></c:out>
			</div>
		</c:if>
		<button type="submit" class="btn btn-primary btn-lg  btn-block">Submit</button>
	</form>
</div>

