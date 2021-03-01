<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/mt2uae/stylesheet.css">
<title>${actelServiceConfig.serviceName}</title>
</head>

<body class="termspage">
	<div id="mainbox">
		<div class="content">
         <h2>Terms and Conditions</h2>
         	 	<p>
         	 	
         	 	<c:if test="${l==0}">
				<div style="text-align:center;font-weight:bold;">
				By clicking on Subscribe, you agree to the below terms and conditions:
				</div>

				<div style="text-align:center;">
				• You will start the paid subscription automatically after the free trial period.	
				</div>
				<div style="text-align:center;">
				• No commitment. You can cancel anytime by sending ${actelServiceConfig.unsubKey} to ${actelServiceConfig.shortCode}
				
				</div>
				<div style="text-align:center;">
				• The Free Trial is valid only for new subscribers	
				</div>
				<div style="text-align:center;">
				• For support, please contact etisalatnoc@altruistindia.com or 00971044204520
				</div>
				
				</c:if>
				
					<c:if test="${l==1}">
								
				<div style="text-align:center;font-weight:bold;" dir="rtl">
			من خلال الضغط على زر الإشتراك، تجري الموافقة على هذه الأحكام والشروط:
				</div>

				<div style="text-align:center;" dir="rtl">
				• سيبدأ الاشتراك المدفوع بعد انتهاء الفترة المجانية تلقائيا	
				</div>
				<div style="text-align:center;" dir="rtl">
				•	لا إلتزام، يمكنك إلغاء اشتراكك في أي وقت بإرسال  ${actelServiceConfig.unsubKey} إلى 1111	
				</div>
				<div style="text-align:center;" dir="rtl">
				•	التجربة المجانية صالحة فقط للمشتركين الجدد.	
				</div>
				<div style="text-align:center;" dir="rtl">
				•	للحصول على المساعدة, الرجاء الإتصال بنا على etisalatnoc@altruistindia.com أو 00971044204520
				</div>
				
				
				</c:if>

			    </p>         
  </div>
	</div>


</body></html>