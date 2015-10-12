
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<style type="text/css">
.hidden {
	display: none;
}
</style>


</script>
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
<%if (request.getServletPath().equals("/index.jsp")) {%>
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
<%} else {%>
	var txt = "<tr><th>Select</th><th>ID</th><th>Name</th><th>Mobile No</th><th>Speed Dial No</th><th>Email</th><th>Dept</th></tr>";
													for (var i = 0; i < len; i++) {
														if (data[i].name
																&& data[i].speedDialNo) {
															txt += "<tr id=\""  + data[i].empId + "\"><td><input type='checkbox' name='empId' value=\""  + data[i].empId +    "\"> </td><td class='id'>"
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

													$("#btnDel").removeClass(
															"hidden");
<%}%>
	if (txt != "") {
														$("#table").html(txt);
													}
												} else if (len == 0) {
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
							return false;//suppress natural form submission
						})
	});
<%if (request.getServletPath().equals("/index.jsp")) {%>
	$(function() {
		$("#table").delegate(
				"tr",
				"click",
				function() {
					document.getElementById("infoEmpID").readOnly = true;
					document.getElementById("infoName").readOnly = true;
					document.getElementById("infoEmpID").value = $(this)
							.children('.id').text()
					document.getElementById("infoName").value = $(this)
							.children('.name').text();
					document.getElementById("infoMobileNo").value = $(this)
							.children('.mobileNo').text();
					document.getElementById("infoSpeedDialNo").value = $(this)
							.children('.speedDialNo').text();
					document.getElementById("infoEmail").value = $(this)
							.children('.email').text();
					document.getElementById("infoDept").value = $(this)
							.children('.dept').text();
				});
	});
<%}%>
	
</script>
</head>
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.username == null }">Please Login First
		<jsp:include page="signup.jsp"></jsp:include>
		</c:when>
		<c:otherwise>
			<div style="margin:2% 20% 2% 20%;">
				<div class="row">
					<!-- /.col-lg-6 -->
					<div class="col-lg-6">
						<div class="input-group">
							<input type="text" id="text" class="form-control"
								placeholder="Search for..."> <span
								class="input-group-btn">
								<button class="btn btn-default" type="button">Search!</button>
							</span>
						</div>
						<!-- /input-group -->
					</div>
					<!-- /.col-lg-6 -->
				</div>
			</div>
			<!-- /.row -->
		</c:otherwise>
	</c:choose>
</body>

