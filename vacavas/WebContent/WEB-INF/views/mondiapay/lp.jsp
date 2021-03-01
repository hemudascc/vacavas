<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>GamesHub</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/mondiapay/css/mondiapay.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/mondiapay/js/mondiapay.js"></script>
</head>
<body>

	<!-- Hidden Parameters Start -->
	<input id="token" value="${token}" type="hidden">
	<input id="accessToken" value="${accessToken}" type="hidden">
	<!-- <input id="token" value="1234" type="hidden">
	<input id="accessToken" value="C415681b9-894c-4ff8-9ce4-0ccdcdd22a18" type="hidden"> -->
	<!-- Hidden Parameters End -->

	<!-- Body Start -->
	<div class="img-div">
		<img
			src="${pageContext.request.contextPath}/resources/mondiapay/image/banner.jpg"
			class="center-block img-rounded img-banner" alt="gameshub">
	</div>
	
	<div class="center-div">
		<button type="button" class="btn btn-md center-block sub-btn"
			id="accept-btn">Subscribe</button>
	</div>
	<div class="text-content">
		<h2>GAMESHUB</h2>
		<h4>R10/day Subscription Service</h4>
		<a href="mailto:vas.support@vacastudios.com">vas.support@vacastudios.com</a>
	</div>
	<!-- Body End -->
	<footer>
	<p>by clicking continue you are accepting the&nbsp;<a href="./mondiapay/tc">Terms and Conditions</a>. Service provided by Vaca Mobiles. Daily subscription.</p>
	</footer>
</body>
</html>