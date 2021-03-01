<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${TpayServiceConfig.serviceName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/tpay/css/tpay.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/tpay/js/tpay.js"></script>
<script src="${HEJSURL}"></script>
</head>
<body>

	<div style="text-align: left; padding: 5px;">
		<select id="lang" onchange="changeLang()">
			<option value="0">English</option>
			<option value="1" dir="rtl">عربى</option>
		</select>
	</div>

	<!-- Hidden Parameters Start -->
	<input id="token" value="${token}" type="hidden">
	<input id="langvalue" value="${lang}" type="hidden">
	<input id="sessionToken" value="${sessionToken}" type="hidden">
	<input id="redirectStatus" value="${redirectStatus}" type="hidden">

	<c:choose>
		<c:when test="${lang==0}">
			<input id="enter-valid-number-msg"
				value="201******" type="hidden">
			<input id="enter-valid-pin-msg" value="Please enter a valid pin."
				type="hidden">
			<input id="already-sub-msg"
				value="This msisdn is already subscribed please login with the short login URL sent."
				type="hidden">
			<input id="session-expired-msg"
				value="Your session is expired please try again later."
				type="hidden">
			<input id="pin-resend-msg" value="Pin has been resent to you."
				type="hidden">
			<input id="success-sub-msg"
				value="You are subscribed to the service successfully."
				type="hidden">
		</c:when>
		<c:otherwise>
			<input dir="rtl" id="enter-valid-number-msg"
				value="201******" type="hidden">
			<input dir="rtl" id="enter-valid-pin-msg"
				value="الرجاء إدخال رقم تعريف شخصي صالح." type="hidden">
			<input dir="rtl" id="already-sub-msg"
				value="تم اشتراك msisdn هذا بالفعل ، يرجى تسجيل الدخول باستخدام عنوان URL المختصر الذي تم إرساله"
				type="hidden">
			<input dir="rtl" id="session-expired-msg"
				value="انتهت جلستك يرجى المحاولة مرة أخرى في وقت لاحق."
				type="hidden">
			<input dir="rtl" id="pin-resend-msg" value="تم إرسال دبوس لك." type="hidden">
			<input dir="rtl" id="success-sub-msg" value="لقد اشتركت في الخدمة بنجاح."
				type="hidden">
		</c:otherwise>
	</c:choose>

	<!-- Hidden Parameters End -->

	<!-- Body Start -->
	<div class="img-div">
		<img
			src="${pageContext.request.contextPath}/resources/tpay/image/banner.jpg"
			class="center-block img-rounded img-banner" alt="gameshub">
	</div>
	<div class="center-div">
		<c:choose>
			<c:when test="${lang==0}">
				<button type="button" class="btn btn-md center-block sub-btn"
					id="sub-button">Subscribe</button>
			</c:when>
			<c:otherwise>
				<button dir="rtl" type="button" class="btn btn-md center-block sub-btn"
					id="sub-button">الإشتراك</button>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="text-content">
		<c:choose>
			<c:when test="${lang==0}">
			<div>
			You will subscribe in ${TpayServiceConfig.serviceName} for ${TpayServiceConfig.price} ${TpayServiceConfig.currency} per ${TpayServiceConfig.billingSequence}
. To cancel your subscription, for ${TpayServiceConfig.operatorName} ${TpayServiceConfig.country} subscribers please send ${TpayServiceConfig.unsubKeyword}
 to ${TpayServiceConfig.shortCode} for free. For any inquires please contact us on vas.support@vacastudios.com.
			</div>	<%-- <ol>
					<li>You will subscribe in GamesHub for
						${TpayServiceConfig.price}
						${TpayServiceConfig.currency}/${TpayServiceConfig.billingSequence}.</li>
					<li>To cancel your subscription, for
						${TpayServiceConfig.country} ${TpayServiceConfig.operatorName}
						subscribers please send ${TpayServiceConfig.unsubKeyword} to
						${TpayServiceConfig.shortCode}.</li>
					<li>For any inquires please contact us on
						vas.support@vacastudios.com</li>
					<li>Powered by vacastudios</li>
				</ul> --%>
			</c:when>
			<c:otherwise>
							<div dir="rtl">
							ستشترك في ${TpayServiceConfig.serviceName} مقابل ${TpayServiceConfig.price} ${TpayServiceConfig.currency} يوميًا. لإلغاء اشتراكك ، لمشتركي ${TpayServiceConfig.operatorName} ${TpayServiceConfig.country} ، يرجى إرسال ${TpayServiceConfig.unsubKeyword} إلى ${TpayServiceConfig.shortCode} مجانًا. لأية استفسارات ، يرجى الاتصال بنا على vas.support@vacastudios.com 
 </div>
		<%-- 		<ol>
					<li dir="rtl">سوف تشترك في GamesHub مقابل 2 جنيه / يوم.</li>
					<li dir="rtl">لإلغاء اشتراكك ، بالنسبة لمشتركي
						${TpayServiceConfig.operatorName} ${TpayServiceConfig.country} ،
						يرجى إرسال ${TpayServiceConfig.unsubKeyword} إلى 4041.</li>
					<li dir="rtl">لأية استفسارات يرجى الاتصال بنا على
						vas.support@vacastudios.com</li>
					<li dir="rtl">مدعوم من المجمع</li>
				</ul> --%>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- Body End -->

	<!-- missing msisdn modal start -->
	<div class="modal" id="missing-msisdn-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">${TpayServiceConfig.serviceName}</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<p id="message"></p>
					<c:choose>
						<c:when test="${lang==0}">
							<label for="usr" id="msisdn-label">Mobile:</label>
							<label for="usr" id="pin-label">Pin:</label>
							<input type="text" class="form-control" onkeypress="validate(event)" value="" name="msisdn"
								id="msisdn" placeholder="Please Enter Mobile Number">
							<input type="text" class="form-control" value="" name="pin"
								id="pin" placeholder="Please Enter Receved Pin">
							<br>
							<div class="popup-button-group">
								<button class="btn btn-md center-block popup-button"
									id="send-pin">Subscribe</button>
								<button class="btn btn-md center-block popup-button"
									id="subscribe-pin">Subscribe</button>
								<button class="btn btn-md center-block popup-button"
									id="resend-pin">Resend Pin</button>
							</div>
						</c:when>
						<c:otherwise>
							<label dir="rtl" for="usr" id="msisdn-label">التليفون المحمول:</label>
							<label dir="rtl" for="usr" id="pin-label">دبوس:</label>
							<input dir="rtl" type="text" class="form-control" value="" name="msisdn"
								id="msisdn" placeholder="يرجى إدخال رقم الهاتف المحمول">
							<input dir="rtl" type="text" class="form-control" value="" name="pin"
								id="pin" placeholder="يرجى إدخال رقم التعريف الشخصي المستلم">
							<br>
							<div class="popup-button-group">
								<button dir="rtl" class="btn btn-md center-block popup-button"
									id="send-pin">الإشتراك</button>
								<button dir="rtl" class="btn btn-md center-block popup-button"
									id="subscribe-pin">الإشتراك</button>
								<button dir="rtl" class="btn btn-md center-block popup-button"
									id="resend-pin">إعادة إرسال دبوس</button>
							</div>
						</c:otherwise>
					</c:choose>
					<div  class="text-content">
						<c:choose>
							<c:when test="${lang==0}">
							<div>You will subscribe in ${TpayServiceConfig.serviceName} for ${TpayServiceConfig.price} ${TpayServiceConfig.currency} per ${TpayServiceConfig.billingSequence}
. To cancel your subscription, for ${TpayServiceConfig.operatorName} ${TpayServiceConfig.country} subscribers please send ${TpayServiceConfig.unsubKeyword}
 to ${TpayServiceConfig.shortCode} for free. For any inquires please contact us on vas.support@vacastudios.com.
 </div>
								<%-- <ol>
									<li>You will subscribe in GamePad for
										${TpayServiceConfig.price}
										${TpayServiceConfig.currency}/${TpayServiceConfig.billingSequence}.</li>
									<li>To cancel your subscription, for
										${TpayServiceConfig.country} ${TpayServiceConfig.operatorName}
										subscribers please send ${TpayServiceConfig.unsubKeyword} to
										${TpayServiceConfig.shortCode}.</li>
									<li>For any inquires please contact us on
										vas.support@vacastudios.com</li>
									<li>Powered by vacastudios</li>
								</ul> --%>
							</c:when>
							<c:otherwise>
											<div dir="rtl">
							ستشترك في ${TpayServiceConfig.serviceName} مقابل ${TpayServiceConfig.price} ${TpayServiceConfig.currency} يوميًا. لإلغاء اشتراكك ، لمشتركي ${TpayServiceConfig.operatorName} ${TpayServiceConfig.country} ، يرجى إرسال ${TpayServiceConfig.unsubKeyword} إلى ${TpayServiceConfig.shortCode} مجانًا. لأية استفسارات ، يرجى الاتصال بنا على vas.support@vacastudios.com 
 </div>
								<%-- <ol>
									<li dir="rtl">سوف تشترك في GamePad مقابل 2 جنيه / يوم.</li>
									<li dir="rtl">لإلغاء اشتراكك ، بالنسبة لمشتركي
										${TpayServiceConfig.operatorName} ${TpayServiceConfig.country}
										، يرجى إرسال ${TpayServiceConfig.unsubKeyword} إلى 4041.</li>
									<li dir="rtl">لأية استفسارات يرجى الاتصال بنا على
										vas.support@vacastudios.com</li>
									<li dir="rtl">مدعوم من المجمع</li>
								</ul> --%>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>