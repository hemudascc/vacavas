<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>GamesHub</title>
<meta charset="utf-8">
<c:set var="context" value="${pageContext.request.contextPath}" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<style>
.box_text {
    font-size: 14px;
    text-align: center;
    color: black;
    margin-top: 500px;
}
img{
height: 250px;
    border: 2px solid #f6d236;
    border-radius: 5%;
}
body {
    width: 100%;
    background-image: url('${context}/resources/messagecloud/background.png');
    background-repeat: no-repeat;
    background-size: contain;
    background-color: #4c5600;
    padding: 235px 20px 0px 20px;
}
@media only screen and (max-width: 600px) {
    .box_text {
    font-size: 14px;
    text-align: center;
    margin-top: 10px;
}
}
@media screen and (max-width: 768px) and (min-width: 426px) {
    .box_text {
    font-size: 14px;
    text-align: center;
    margin-top: 210px;
}}
@media screen and (max-width: 1024px) and (min-width: 769px) {
    .box_text {
    font-size: 14px;
    text-align: center;
    margin-top: 350px;
}
}
@media screen and (max-width: 1600px) and (min-width: 1025px) {
    .box_text {
    font-size: 14px;
    text-align: center;
    margin-top: 635px;
}
}
</style>
<body class="text-center">
	<div class="box">
		<!--<h4 style="color:#f1301e"><b>GamesHub</b></h4>-->
		<!--<img id="banner" src="./banner.png" alt="">-->
		<div class="box_text">
			<p class="text1">
				Sunrise-Benutzer erhalten eine Nachricht von 1 x 15 CHF<br>
				Salt / Swisscom eine Nachricht von 15 CHF von 3 x 5 CHF <br> Um
				den Dienst zu stoppen, sende STOP an 311.
			</p>
		</div>
		<p class="page_wrap__content__form__text">
			<strong>Zum Abonnieren dieser Nachricht Games Hub senden Sie
				die Option START ABO GHUB an die 311 -Meldung an</strong>
		</p>
		<p>
			<strong>Msisdn ${msisdn}</strong>
		</p>

		<p class="text1 text2">Sie knnen jederzeit von Ihrer Kontoseite abbrechen oder einen STOP an die 311 senden. Wenn Sie Hilfe bentigen, kontaktieren Sie uns bitte, indem Sie eine E-Mail an info@vacastudios.com senden. oder indem Sie  0800 897 980 aufrufen.</p>
		
		<p>
			Der Games Hub ist ein Abonnementdienst von Vaca Mobiles
			und Durch Klicken auf die Schaltfläche stimmen Sie den Allgemeinen Geschäftsbedingungen zu.
			<a href="./terms">Geschäftsbedingung</a>
		</p>
	</div>
	</div>
</body>
</html>