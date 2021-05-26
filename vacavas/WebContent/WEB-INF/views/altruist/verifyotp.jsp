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
	<form action="${pageContext.request.contextPath}/sys/altr/verify-pin"
		method="post">
		<!-- Hidden Parameters Start -->
		<input id="token" name="token" value="${token}" type="hidden">
		<input id="msisdn" name="msisdn" value="${msisdn}" type="hidden">
		<input id="lang" name="lang" value="${lang}" type="hidden">
		<!-- Hidden Parameters End -->
		<!-- Body Start -->
		<div class="img-div" style="margin-top: 15px;">
			<img style="height: 200px; width: 80%;"
				src="${pageContext.request.contextPath}/resources/altruist/image/banner.png"
				class="center-block img-rounded img-banner"
				alt="${altruistServiceConfig.serviceName}">
		</div>
		<h3>${altruistServiceConfig.serviceName}</h3>
		<c:if test="${lang==0}">
			<c:if test="${status==0}">
				<p>Please Enter a valid pin to activate your subscription</p>
			</c:if>
			<c:if test="${status==1}">
				<p>Please Enter the pin you received to activate your
					subscription</p>
			</c:if>
			<c:if test="${status==2}">
				<p>Some error occurred while processing your request.</p>
			</c:if>
		</c:if>
		<c:if test="${lang==1}">
			<c:if test="${status==0}">
				<p dir="rtl">الرجاء إدخال رقم تعريف شخصي صالح لتفعيل اشتراكك</p>
			</c:if>
			<c:if test="${status==1}">
				<p dir="rtl">الرجاء إدخال رقم التعريف الشخصي الذي تلقيته لتفعيل
					اشتراكك</p>
			</c:if>
			<c:if test="${status==2}">
				<p dir="rtl">حدث خطأ ما أثناء معالجة طلبك.</p>
			</c:if>
		</c:if>
		<input type="text" name="pin" id="pin" placeholder="XXXX"
			style="width: 200px; height: 30px; margin: 0px 0px 5px 0px; color: black;">
		<br>
		<c:if test="${lang==0}">
			<button id="subscribe" type="submit" class="btn btn-success">Subscribe</button>
			<button id="exit" type="button" onclick="exit();" class="btn btn-danger">Exit</button>
		</c:if>
		<c:if test="${lang==1}">
			<button id="subscribe" type="submit" class="btn btn-success"
				dir="rtl">الإشتراك</button>
			<button id="exit" type="button" onclick="exit();" class="btn btn-danger" dir="rtl">مخرج</button>
		</c:if>
	</form>
	<div>
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
	</div>
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
			Free for 24 hours then, you will be charged AED 
			${altruistServiceConfig.price}/${altruistServiceConfig.durationDescription} automatically.
			</c:if>
			<c:if test="${lang==1}">
			
			<span dir="rtl">
			مجانًا لمدة 24 ساعة بعد ذلك ، سيتم محاسبتك بالدرهم الإماراتي
${altruistServiceConfig.price} / <c:if test="${altruistServiceConfig.durationDescription=='day'}">يوم</c:if>
				<c:if test="${altruistServiceConfig.durationDescription=='week'}">أسبوع</c:if> تلقائيًا.
			</span>
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
				<span > Free trial applicable only for first time subscriber.</span>
			</c:if>
			<c:if test="${lang==1}">
			<span dir="rtl">
			نسخة تجريبية مجانية قابلة للتطبيق فقط للمشترك لأول مرة.
			</span>
			</c:if>
		</p>
		<p>
			<b>-</b>
			<c:if test="${lang==0}">
				<span>Enjoy your Free trial until today 23:59 hours</span>
			</c:if>
			<c:if test="${lang==1}">
			<span dir="rtl">
			استمتع بتجربتك المجانية حتى اليوم الساعة 23:59
			</span>
			</c:if>
		</p>
		<p>
			<b>-</b>
			<c:if test="${lang==0}">
				<span>For complete T's &amp;C's <a
					href="${pageContext.request.contextPath}/sys/altr/tc">click
						here</a></span>
			</c:if>
			<c:if test="${lang==1}">
				<span dir="rtl"> للحصول على T's &amp;C's كاملة <a
					href="${pageContext.request.contextPath}/sys/altr/tc">انقر هنا</a>
				</span>
			</c:if>
		</p>
	</div>
	<!-- Body End -->
</body>
<script>
$("#exit").click(function(){
		window.location.href="http://theplaymall.com/?dcb=altr"
});
</script>
</html>