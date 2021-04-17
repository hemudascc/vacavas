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
	color: black;
	background-color: white;
}
</style>
<body>
	<form action="${pageContext.request.contextPath}/sys/timwe/sendpin" method="post">
		<!-- Hidden Parameters Start -->
		<input id="token" name="token" value="${token}" type="hidden">
		<!-- Hidden Parameters End -->
		<div class="img-div" style="margin-top: 15px;">
			<img style="height: 200px; width: 80%;"
				src="${pageContext.request.contextPath}/resources/timwe/image/banner.png"
				class="center-block img-rounded img-banner"
				alt="${timweServiceConfig.serviceName}">
		</div>
		<p>Play Mall is the Gaming Service provided by Vaca Mobiles</p>
		<h3>${timweServiceConfig.serviceName}</h3>
			<c:if test="${status==0}">
				<p>Please enter your mobile number</p>
			</c:if>
			<c:if test="${status==1}">
				<p>Please Enter a valid mobile number</p>
			</c:if>
			<c:if test="${status==2}">
				<p>You have exceded your limit.</p>
			</c:if>
			<c:if test="${status==3}">
				<p>Could not send pin please try again</p>
			</c:if>
		<input type="text" name="msisdn" value="${msisdn}" id="msisdn" placeholder="Mobile Number"
			style="width: 200px; height: 30px; margin: 0px 0px 5px 0px; color: black;">
		<br>
			<h5>JOD ${timweServiceConfig.price} per ${timweServiceConfig.validityDesc}(Including VAT)</h5>
			<button id="subscribe" type="submit" class="btn btn-success">Subscribe</button>
			<button id="exit" type="button" onclick="exit();" class="btn btn-danger">Exit</button>
	</form>
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
</body>
<script>
	/* $(document).ready(function(){
		if($("#msisdn").val().length !== 0){
			$("#msisdn").prop("disabled",true);
		}
	}); */

	$("#exit").click(function() {
		window.location.href = "${timweServiceConfig.portalURL}"
	});
</script>
</html>