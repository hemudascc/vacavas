<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script
	src="${pageContext.request.contextPath}/resources/tpay/js/${jspath}"></script>
<title></title>
</head>
<body>

</body>
<script>
$(document).ready(function(){
  window.location.href="https://www.google.com/search?q="+sessionToken();
});
</script>
</html>