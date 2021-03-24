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

.langchange {
	padding: 5px;
	text-align: right;
	color: blue;
}
</style>
<body>
	<form action="${pageContext.request.contextPath}/sys/altr/send-pin"
		method="post">
		<div class="langchange">
			<select id="lang-select" onchange="changeLang()">
				<option value="0">EN</option>
				<option value="1">AR</option>
			</select>
		</div>
		<!-- Hidden Parameters Start -->
		<input id="token" name="token" value="${token}" type="hidden">
		<input id="lang" name="lang" value="${lang}" type="hidden">
		<!-- Hidden Parameters End -->
		<!-- Body Start -->
	<%-- 	<c:if test="${lang==0}">
			<p>Free for 1 day then AED ${altruistServiceConfig.price} per
				${altruistServiceConfig.durationDescription} VAT Included</p>
		</c:if>
		<c:if test="${lang==1}">
			<p dir="rtl">
				مجانًا ليوم واحد ثم ${altruistServiceConfig.price} درهم لكل
				<c:if test="${altruistServiceConfig.durationDescription=='day'}">يوم</c:if>
				<c:if test="${altruistServiceConfig.durationDescription=='week'}">أسبوع</c:if>
				شامل ضريبة القيمة المضافة
			</p>
		</c:if> --%>
		<div class="img-div" style="margin-top: 15px;">
			<img style="height: 200px; width: 80%;"
				src="${pageContext.request.contextPath}/resources/altruist/image/banner.png"
				class="center-block img-rounded img-banner"
				alt="${altruistServiceConfig.serviceName}">
		</div>
		<h3>${altruistServiceConfig.serviceName}</h3>
		<c:if test="${lang==0}">
			<c:if test="${status==0}">
				<p>Enter your Etisalat Mobile number to receive OTP</p>
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
		</c:if>
		<c:if test="${lang==1}">
			<c:if test="${status==0}">
				<p dir="rtl">أدخل رقم هاتف اتصالات الخاص بك لتلقي OTP</p>
			</c:if>
			<c:if test="${status==1}">
				<p dir="rtl">الرجاء إدخال رقم جوال صحيح</p>
			</c:if>
			<c:if test="${status==2}">
				<p dir="rtl">لقد تجاوزت الحد الخاص بك</p>
			</c:if>
			<c:if test="${status==3}">
				<p dir="rtl">تعذر إرسال رقم التعريف الشخصي ، يرجى المحاولة مرة
					أخرى</p>
			</c:if>
		</c:if>
		<input type="text" name="msisdn" id="msisdn" placeholder="971XXXXXXXX"
			style="width: 200px; height: 30px; margin: 0px 0px 5px 0px; color: black;">
		<br>
		<c:if test="${lang==0}">
			<button id="subscribe" type="submit" class="btn btn-success">Subscribe</button>
			<button id="exit" type="button" onclick="exit();"
				class="btn btn-danger">Exit</button>
		</c:if>
		<c:if test="${lang==1}">
			<button id="subscribe" type="submit" class="btn btn-success"
				dir="rtl">الإشتراك</button>
			<button id="exit" type="button" onclick="exit();"
				class="btn btn-danger" dir="rtl">مخرج</button>
		</c:if>
	</form>
	<%-- <div>
		<c:if test="${lang==0}">
			<p>Free for 1 day then AED ${altruistServiceConfig.price} per
				${altruistServiceConfig.durationDescription} VAT Included</p>
		</c:if>
		<c:if test="${lang==1}">
			<p dir="rtl">
				مجانًا ليوم واحد ثم ${altruistServiceConfig.price} درهم لكل
				<c:if test="${altruistServiceConfig.durationDescription=='day'}">يوم</c:if>
				<c:if test="${altruistServiceConfig.durationDescription=='week'}">أسبوع</c:if>
				شامل ضريبة القيمة المضافة
			</p>
		</c:if>
	</div> --%>
	<div class="terms-condition">
		<p>
			<c:if test="${lang==0}">
		<!-- 		<b>By clicking on Subscribe, you agree to the below terms and
					conditions:</b> -->
					<b>Terms and Conditions:</b>
			</c:if>
			<c:if test="${lang==1}">
				<!-- <b dir="rtl">بالنقر فوق "اشتراك" ، فإنك توافق على الشروط وبالنقر
					فوق "اشتراك" ، فإنك توافق على الشروط والأحكام التالية:</b> -->
					<b  dir="rtl">الأحكام والشروط</b>
			</c:if>
		</p>
		<p>
			<b>-</b>
			<c:if test="${lang==0}">
			You will start the paid subscription automatically after the
			free trail.
			</c:if>
			<c:if test="${lang==1}">
				<span dir="rtl">ستبدأ الاشتراك المدفوع تلقائيًا بعد انتهاء
					الفترة التجريبية المجانية.</span>
			</c:if>
		</p>
		<p>
			<b>-</b>
			<c:if test="${lang==0}">
				<span dir="rtl">Renewal will be automatic every
					${altruistServiceConfig.durationDescription}</span>
			</c:if>
			<c:if test="${lang==1}">
			سيتم التجديد تلقائيًا كل <c:if
					test="${altruistServiceConfig.durationDescription=='day'}">يوم</c:if>
				<c:if test="${altruistServiceConfig.durationDescription=='week'}">أسبوع</c:if>
			</c:if>
		</p>
		<p>
			<b>-</b>
			<c:if test="${lang==0}">
				<span>No commitment you can cancel anytime by sending
					${altruistServiceConfig.unsubKey} to
					${altruistServiceConfig.shortCode}</span>
			</c:if>
			<c:if test="${lang==1}">
				<span dir="rtl"> لا يوجد التزام يمكنك إلغاؤه في أي وقت بإرسال
					${altruistServiceConfig.unsubKey} إلى
					${altruistServiceConfig.shortCode}</span>
			</c:if>
		</p>
		<p>
			<c:if test="${lang==0}">
				<b>-</b>
				<span>For support please contact vas.support@vacastudios.com</span>
			</c:if>
			<c:if test="${lang==1}">
				<span dir="rtl"> للحصول على الدعم ، يرجى الاتصال بـ
					vas.support@vacastudios.com </span>
			</c:if>
		</p>
		<p>
			<b>-</b>
			<c:if test="${lang==0}">
				<span>For complete T&amp;C <a
					href="${pageContext.request.contextPath}/sys/altr/tc">click
						here</a></span>
			</c:if>
			<c:if test="${lang==1}">
				<span dir="rtl"> للحصول على T&amp;C كاملة <a
					href="${pageContext.request.contextPath}/sys/altr/tc">انقر هنا</a>
				</span>
			</c:if>
		</p>
	</div>
	<!-- Body End -->
</body>
<script>
	function changeLang() {
		window.location.href = '${pageContext.request.contextPath}/sys/altr/change-lang?lang='
				+ $("#lang-select").val() + '&token=' + $("#token").val()
	}
	$(document).ready(function() {
		var e = document.getElementById("lang-select");
		e.value = $("#lang").val();
	});
	$("#exit").click(function() {
		window.location.href = "http://theplaymall.com/?dcb=altr"
	});
</script>
</html>