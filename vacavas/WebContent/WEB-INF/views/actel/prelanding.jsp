<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>${config.serviceName}</title>
<style>
.button {
    background-color: #9eaa00;
    border: 1px solid;
    color: #ffffff;
    padding: 6px 32px;
    text-align: center;
    display: inline-block;
    font-weight: bold;
    width: 65%;
    border-radius: 5px;
}
</style>
<script type="text/javascript">
function exit(){
	alert("exit")
	location.href='${actelServiceConfig.portalUrl}';
	return false;
}

function changeLang(lang){
	location.href='${pageContext.request.contextPath}/sys/actel/lp/'+lang+'/${campId}/${token}';
	return false;
}

</script>
</head>
<body style="height: 100%;width: 100%;background: radial-gradient(#757429, #3ca8a2);">

	<header>
		<div>
			<div style="text-align: right; padding-top: 10px; font-weight: bold">
				<div
					style="text-align: left; padding-top: 5px; font-weight: bold; font-size: 12px; padding-left: 20px;"></div>
				<a style="color: #823a07;" href="#" onclick="changeLang(0)">ENG</a>
				<a style="color: #823a07;" href="#" onclick="changeLang(1)" dir="rtl">عربى</a>
			</div>

			<c:if test="${l==0}">
				<div style="text-align: center;padding-bottom: 5px;font-weight: bold;font-size: 13px;color: #fed64c;">
					DU : ${config.priceDesc} ${config.price} / ${config.validityDesc}, VAT included
				</div>
			</c:if>
			<c:if test="${l==1}">
				<div style="text-align: center;padding-bottom: 5px;font-weight: bold;font-size: 13px;color: #fed64c;"
					dir="rtl">
					DU: ${config.priceDesc} ${config.price} / ${config.validityDesc} ، شامل ضريبة القيمة المضافة
				</div>
			</c:if>

		</div>
	</header>
	<section>
		<div class="col-md-12 col-sm-12 col-lg-12" style="text-align: center;">
			<p style="text-align: center;">
				<img style="max-height: 255px;border: 2px solid #d2a550;"
					src="${pageContext.request.contextPath}/resources/actel/bannergd.jpg"
					class="img-responsive" height="200" width="300"></img>
			</p>
			<form id="operatorSelectForm" method="post"
				action="${pageContext.request.contextPath}/cnt/actel/tocg">
				<c:if test="${l==0}">
					<p style="text-align: center;font-size: 14px;color: #fed64c;">
						<strong>${config.serviceName}</strong><br /> Please select your operator.
					</p>
				</c:if>
				<c:if test="${l==1}">
					<p style="text-align: center;font-size: 14px;color: #fed64c;" dir="rtl">
						<strong>${config.serviceName}</strong>
						<br/>
						<div dir="rtl">
						 يرجى تحديد المشغل الخاص بك.
		</div>
					</p>
				</c:if>

				<div class="ok" style="max-width: 100%; padding-bottom: 15px;">
					<input type="hidden" name="token" value="${token}" /> <input
						type="hidden" name="l" value="${l}" /> <input type="hidden"
						id="serviceId" name="serviceId" value="" /> 
					<a href="${pageContext.request.contextPath}/sys/sub?adid=2&evid=5&ref=${token}" type="button" class="button" style="font-size: 15px;">
						${l==0?'DU':'دو'}</a>
				</div>
			</form>
		</div>
	</section>
</body>
</html>
