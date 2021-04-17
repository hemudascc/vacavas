<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>unsubscribe</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/tpay/js/tpay.js"></script>
</head>
<style>
body {
	text-align: center;
	font-size: 24px;
	padding: 10px 10px 10px 10px;
}
</style>
<body>
	<input id="msisdn" value="${msisdn}" type="hidden">
	<input id="langvalue" value="${lang}" type="hidden">
	<input id="campId" value="${campId}" type="hidden">
	<input id="tokenvalue" value="${token}" type="hidden">
	<input id="lpimageUrl" value="${lpImageUrl}" type="hidden">
	<input id="portalurl" value="${portalUrl}" type="hidden">
	<input id="productId" value="${productId}" type="hidden">
	<c:choose>
		<c:when test="${lang==2}">
			<input id="already-unsub-message" value="أنت بالفعل غير مشترك في الخدمة." type="hidden">
			<input id="success-unsub-message" value="لقد قمت بإلغاء الاشتراك بنجاح من الخدمة يمكنك دائمًا الاشتراك مرة أخرى في الخدمة في أي وقت." type="hidden">
		</c:when>
		<c:otherwise>
			<input id="already-unsub-message" value="You are already unsubscribed the service." type="hidden">
			<input id="success-unsub-message" value="You have unsubscribe successfully from the service you can always subscribe again to the service at any time." type="hidden">
		</c:otherwise>
	</c:choose>

	<div class="img-div">
		<img
			src="${pageContext.request.contextPath}/${lpImageUrl}"
			class="img-responsive center-block img-rounded img-banner"
			alt="gameshub">
	</div>
	<c:choose>
		<c:when test="${lang==2}">
			<c:choose>
				<c:when test="${campId==null || empty campId}">
					<p id="you-are-already-unsub">أنت بالفعل غير مشترك في الخدمة.</p>
				</c:when>
				<c:otherwise>
					<p id="consent-p">هل أنت متأكد أنك تريد إلغاء الاشتراك في
						الخدمة؟</p>
					<p id="unsubscribe-msg" style="display: none"></p>
					<div>
						<button class="btn btn-sm btn-warning" id="unsubscribe-button" onclick="unsubscribe();">إلغاء
							الاشتراك</button>
						<button class="btn btn-sm btn-danger" id="exit-button" onclick="exit();">خروج</button>
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${campId==null || empty campId}">
					<p id="you-are-already-unsub">You are already unsubscribed the
						service.</p>
				</c:when>
				<c:otherwise>
					<p id="consent-p">Are you sure you want to unsubscribe from the
						service ?</p>
					<p id="unsubscribe-msg" style="display: none"></p>
					<div>
						<button class="btn btn-sm btn-warning" id="unsubscribe-button1" onclick="unsubscribe();">Unsubscribe</button>
						<button class="btn btn-sm btn-danger" id="exit-button" onclick="exit();">Exit</button>
					</div>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</body>
</html>