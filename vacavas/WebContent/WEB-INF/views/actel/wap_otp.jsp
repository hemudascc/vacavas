<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<!doctype html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>  
  <title>${actelServiceConfig.packageName} Pin Page</title>
  <style>
 
.button {
  background-color: #00b359; /* Green */
  border: none;
  color: white;
  padding: 6px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 23px;
  font-weight: bold;
  width: 90%;
  border-radius: 12px;
}

.button1 {
  background-color: #000000; /* Green */
  border: none;
  color: white;
  padding: 6px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 23px;
  font-weight: bold;
  width: 30%;
  border-radius: 12px;
}

</style>
<script type="text/javascript">

function exit(){
	//alert("exit")
	location.href='https://www.google.com/';
	//location.href='${actelServiceConfig.portalUrl}';
	return false;
}

function changeLang(lang){
	//alert("exit")
	location.href='${pageContext.request.contextPath}/cnt/actel/chnage/lang?l='+lang+'&token=${token}&msisdn=${msisdn}&page=wap_otp';
	return false;
}

</script>
 </head>
<body>

<header>


			<div>
		<div style="text-align:right;padding-top:10px;font-weight: bold">
			<a href="#" onclick="changeLang(0)">ENG</a> <a href="#" onclick="changeLang(1)">عربى</a>						
			</div>
		<c:if test="${l==0}">	
			<div style="text-align:center;padding-top:10px;font-weight: bold">			
			Free for 1 day then AED ${actelServiceConfig.price}/${actelServiceConfig.validityDesc}, VAT Included	            	
			</div>
			</c:if>
			 <c:if test="${l==1}">	
			<div style="text-align:center;padding-top:10px;font-weight: bold" dir="rtl">			
			مجانًا لمدة يوم واحد ثم 11.0 درهمًا إماراتيًا / أسبوعيًا ، شاملة ضريبة القيمة المضافة	            	
			</div>
			</c:if>
			
			<!--<span class="title" ><img src="./cgImg/logo.png"></span>-->
			</div>
			</header>
			<section>
			<div class="col-md-12 col-sm-12 col-lg-12" style="text-align:center;">
			<form id="otpform" method="post" action="${pageContext.request.contextPath}/cnt/actel/web/send/otp/validation">
			<p style="text-align:center;">
			<img src="${pageContext.request.contextPath}/resources/actel/play_it_banner.png" class="img-responsive" height="200" width="300"></img></p>
			<p style="text-align:center;font-size:14px;"><strong>PLAY IT <br/> Welcome: ${msisdn}</strong>
			<p style="text-align:center;font-size:14px;"><strong>${otpinfo}</strong></p>
			<br/> 
			<c:if test="${l==0}">
			<p style="text-align:center;font-size:14px;">			
			Please enter the PIN Code you received to   <br/>activate your subscription
			</p>
			</c:if>
			<c:if test="${l==1}">
			<p style="text-align:center;font-size:14px;" dir="rtl">						
الرجاء إدخال الرمز السري لتفعيل الإشتراك  

			</p>
			</c:if>
			
			<div class="ok" style="max-width: 100%;padding-bottom: 15px;">
			<input type="text" name="pin" style="width:90%">
			<input type="hidden"  name="token" value="${token}"/>
			<input type="hidden"  name="l" value="${l}"/>
			<input type="hidden"  name="msisdn" value="${msisdn}"/>
			<br/><br/>
			<input type="submit" class="button" style="font-size: 15px;" 
			value="${l==0?'Confirm your subscription' : 'تأكيد اشتراكك'}"/><br/><br/>
			<button class="button" style="font-size: 15px;" onclick="exit()">${l==0?'Exit':'خروج'}</button>
			</form>
			</div> 
              
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
				<div style="text-align:center;">
				• For full T&C, please click on this link<a href="${pageContext.request.contextPath}/cnt/actel/tc?token=${token}&l=${l}">T&C</a>
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
				<div style="text-align:center;" dir="rtl">
				• للمزيد من المعلومات، اضغط على هذا <a href="${pageContext.request.contextPath}/cnt/actel/tc?token=${token}&l=${l}"> الرابط </a>
				
				
				</div>
				
				
				</c:if>

			</div>
			</section>

			 


 </body>
</html>
