
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<html>
<head>
<link rel="shortcut icon"
	href="<c:url value="resources/images/contacts1.png" />" />
<script src="resources/jquery.min.js">
	
</script>
<link href="<c:url value="resources/bootstrap.min.css" />"
	rel="stylesheet" />
<script type="text/javascript">
	$(function() {
		$("#text")
				.keyup(
						function() {
							$
									.ajax({
										type : 'GET',
										url : 'search?term=' + $('#text').val(),
										dataType : "json", //to parse string into JSON object,
										success : function(data) {
											if (data) {
												var len = data.length;

												if (len > 0) {
													var txt = "<tr><th>ID</th><th>Name</th><th>Mobile No</th><th>Speed Dial No</th><th>Email</th><th>Dept</th></tr>";
													for (var i = 0; i < len; i++) {
														if (data[i].name
																&& data[i].speedDialNo) {
															txt += "<tr id=\""  + data[i].empId +    "\"><td class='id'>"
																	+ data[i].empId
																	+ "</td><td class='name'>"
																	+ data[i].name
																	+ "</td><td class='mobileNo'>"
																	+ data[i].mobileNo
																	+ "</td><td class='speedDialNo'>"
																	+ data[i].speedDialNo
																	+ "</td><td class='email'>"
																	+ data[i].email
																	+ "</td><td class='dept'>"
																	+ data[i].dept
																	+ "</td></tr>";
														}
													}
													if (txt != "") {
														$("#table").html(txt);
													}
												} else {
													$("#table")
															.html(
																	"<div style='color: red;'>No Such Record Found</div>");
												}
											}
										},
										error : function(jqXHR, textStatus,
												errorThrown) {
											alert('error: ' + textStatus + ': '
													+ errorThrown);
										}
									});
							return false;
						})
	});
</script>
</head>
<body>
	<ul class="nav nav-pills" style="margin-top:0.5%">
		<li><a href="Search.jsp"><img src="resources/images/logo.png"
				height="12%" width="24%" alt="logo"><span
				style="color:#0C96C8;font-weight:bold;font-size:2em;text-decoration:none;">Contact
					Book</span></a></li>
	</ul>


	<div style="margin:2% 20% 2% 20%;">
		<div class="row">
			<div class="col-lg-10">
				<div class="input-group">
					<input type="text" id="text" class="form-control"
						placeholder="Search for..."> <span class="input-group-btn">
						<button class="btn btn-default" type="button">Search!</button>
					</span>
				</div>
			</div>
			<br> <br>
			<div class="container fluid">
				<div class="row">
					<div class="col col-lg-8">
						<div class="panel panel-default">
							<table id="table" class="table">
							</table>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

</body>
</html>