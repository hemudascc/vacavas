<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>PlayMall</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<style>
body {
	text-align: center;
	color: #e8f5e9;
	background-color: darkslategrey;
}
</style>
<body>
	<!-- Body Start -->
	<div class="img-div">
		<img style="height: 200px; width: 80%;"
			src="${pageContext.request.contextPath}/resources/timwe/image/banner.png"
			class="center-block img-rounded img-banner"
			alt="PlayMall">
	</div>
	<h3>PlayMall</h3>
	<p>
		<strong>${message}</strong>
	</p>
	<!-- Body End -->
</body>
</html>