
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<head>
<script type="text/javascript"
	src="resources/bootstrap-filestyle.min.js">
	
</script>
</head>

<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
<a href="resources/excel.xls">Download</a>
Excel format for Upload

<br>
<br>
<!-- help taking from
	http://markusslima.github.io/bootstrap-filestyle/
 -->

ONLY RECORDS WILL BE INSERTED SO KINDLY UPLOAD EXCEL WITH ONLY NEW
RECORDS
<form action="uploadservletNew" method="post"
	enctype="multipart/form-data" role="form">
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<input type="file" name="file" class="filestyle" data-icon="false">
			</div>
		</div>
		<div class="col-md-4">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</div>
</form>


<br>
<br>
ALL RECORDS WILL BE DELETED AND NEW RECORS WILL BE FROM NEW EXCEL FILE
<form action="uploadservletRefresh" method="post"
	enctype="multipart/form-data" role="form">
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<input type="file" name="file" class="filestyle" data-icon="false">
			</div>
		</div>
		<div class="col-md-4">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</div>
</form>