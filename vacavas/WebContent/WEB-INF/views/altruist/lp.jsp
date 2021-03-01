<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>PlayMall</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/altruist/css/altruist.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/altruist/js/altruist.js"></script>
</head>
<style>
body{
	text-align: center;
    color: #e8f5e9;
    background-color: darkslategrey;
}
</style>
<body>
	<form>
		<!-- Hidden Parameters Start -->
		<input id="token" value="${token}" type="hidden">
		<!-- Hidden Parameters End -->
		<!-- Body Start -->
		<p>Free for 1 day then AED ${altruistServiceConfig.price} per
			${altruistServiceConfig.durationDescription} VAT Included</p>
		<div class="img-div">
			<img style="height: 200px;width: 80%;"
				src="${pageContext.request.contextPath}/resources/altruist/image/banner.png"
				class="center-block img-rounded img-banner"
				alt="${altruistServiceConfig.serviceName}">
		</div>
		<h3>${altruistServiceConfig.serviceName}</h3>
		<p>Enter your Etisalat Mobile number to receive OTP</p>
		<input type="text" name="msisdn" id="msisdn" placeholder="05XXXXXXXX" style="width: 200px;height: 30px;margin: 0px 0px 5px 0px;">
		<br>
		<button id="subscribe" type="submit" class="btn btn-success">Subscribe</button>
		<button id="exit" class="btn btn-danger">Exit</button>
	</form>
	<div class="terms-condition">
		<p>
			<b>By clicking on Subscribe, you agree to the below terms and
				conditions:</b>
		</p>
		<p>
			<b>-</b>You will start the paid subscription automatically after the
			free trail.
		</p>
		<p>
			<b>-</b>Renewal will be automatic every
			${altruistServiceConfig.durationDescription}
		</p>
		<p>
			<b>-</b>No commitment you can cancel anytime by sending
				${altruistServiceConfig.unsubKey} to
				${altruistServiceConfig.shortCode}
		</p>
		<p>
			<b>-</b>For support please contact vas.support@vacastudios.com
		</p>
	</div>
	<!-- Body End -->
</body>
</html>