<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${timweServiceConfig.serviceName}</title>
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
			alt="${timweServiceConfig.serviceName}">
	</div>
	<h3>${timweServiceConfig.serviceName}</h3>
		<p>
			<strong>You have been successfully subscribed to
				${timweServiceConfig.serviceName}</strong>
		</p>
	<br>
		<div class="terms-condition">
		<p>
			<b>-</b>Play Mall is the Service provided by Vaca Mobiles For doubts or further more information about services contact to to vas.support@vacastudios.com
		</p>
		<p>
			<b>-</b>The service will be charged at JOD ${timweServiceConfig.price} per ${timweServiceConfig.validityDesc} (Including VAT).
		</p>
		<p>
			<b>-</b>Your subscription will be automatically renewed monthly untill you unsubscribe.
		</p>
		<p>
			<b>-</b>To unsubscribe send ${timweServiceConfig.unsubKey} to ${timweServiceConfig.shortCode}
		</p>
		<p>
			<b>-</b>Standard data browsing charges apply.
		</p>
	</div>
	<!-- Body End -->
</body>
</html>