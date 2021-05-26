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
			src="${pageContext.request.contextPath}/resources/altruist/image/banner.png"
			class="center-block img-rounded img-banner"
			alt="${altruistServiceConfig.serviceName}">
	</div>
	<h3>${altruistServiceConfig.serviceName}</h3>
	<c:if test="${lang==0}">
		<p>
			<strong>You have been successfully subscribed to
				${altruistServiceConfig.serviceName}</strong>
		</p>
	</c:if>
	<c:if test="${lang==1}">
		<p dir="rtl">لقد تم اشتراكك بنجاح في $
			{altruistServiceConfig.serviceName}</p>
	</c:if>
	<br>
	<div class="terms-condition">
		<p>
			<c:if test="${lang==0}">
				<b>We have sent you a confirmation SMS that includes your
					subscription details for your records.</b>
			</c:if>
			<c:if test="${lang==1}">
				<b dir="rtl">لقد أرسلنا إليك رسالة تأكيد SMS تتضمن تفاصيل
					اشتراكك في سجلاتك.</b>
			</c:if>
		</p>
		<p>
			<c:if test="${lang==0}">
				<b>Please keep that message for future reference.</b>
			</c:if>
			<c:if test="${lang==1}">
				<b dir="rtl">يرجى الاحتفاظ بهذه الرسالة للرجوع إليها في المستقبل.</b>
			</c:if>
		</p>
		<p>
		<c:if test="${lang==0}">
			<b><a href="${portalURL}">Click here</a> to access the
				${altruistServiceConfig.serviceName}
			</b>
		</c:if>
		<c:if test="${lang==1}">
			<b dir="rtl"><a href="${portalURL}">انقر هنا</a>للوصول إلى
				${altruistServiceConfig.serviceName}
			</b>
			</c:if>
		</p>
	</div>
	<!-- Body End -->
</body>
</html>