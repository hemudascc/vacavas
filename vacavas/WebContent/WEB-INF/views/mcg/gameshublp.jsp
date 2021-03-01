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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
        Sunrise-Benutzer erhalten eine Nachricht von 1 x 15 CHF
		<br>
		Salt / Swisscom eine Nachricht von 15 CHF von 3 x 5 CHF
		<br>
		Um den Dienst zu stoppen, sende STOP an 311.
        </p>
       <form action="${pageContext.request.contextPath}/sys/mcg/lp"  method="post">
       <input type="hidden" name="token" value="${token}">
        <div>
            <span style="color: white;">41</span>
            <input name="msisdn" maxlength="9" id="number" type="tel" style="width: 225px;height: 30px;" value="" oninput="value=value.replace(/[^\d]/g,'')" placeholder="Gib Deine Handynummer ein">
        </div>
        <!-- <img src="../../static/img/playthree2btn.png" alt=""> -->
        <button class="btn btn-md btn-primary" style="margin: 10px 0px 10px 0px;">WEITER</button>
        </form>
        <p class="text1 text2">
            Abonnieren Sie das GAMESHUB, um auf verschiedene H5-Spiele zuzugreifen. Unser Inhalt wird regelmäßig aktualisiert. Einige Inhalte werden auf Englisch sein.
        </p>
        <p>
            Wenn Sie sich für unseren Service entscheiden, stimmen Sie zu, unseren Service zu abonnieren. Dies ist ein Produkt zum Herunterladen von mobilen Inhalten. Nach dem Abonnement haben Sie Zugriff auf den Inhalt des Spielportals.
            Sunrise-Benutzer erhalten 1 x 15 CHF und Salt / Swisscom SMS mit 15 CHF von 3 x 5 CHF. Um den Dienst zu beenden, senden Sie STOP an 311<!-- Sunrise-Benutzer erhalten eine Rechnung von 1 x 15 CHF und Salt / Swisscom eine Rechnung von 15 CHF von 3 x 5 CHF Um den Dienst zu stoppen, sende STOP an 311 -->. Die Gebühr wird von Ihrem Handy-Guthaben abgezogen. Um den Dienst zu registrieren oder zu nutzen, müssen Sie Abonnent / Kontoinhaber sein und mindestens 18 Jahre alt sein oder die ausdrückliche Erlaubnis des Abonnenten / Kontoinhabers haben. Ihr Telefon muss das Internet unterstützen, Sie müssen in der Lage sein, Textnachrichten zu senden und zu empfangen, und Sie müssen in der Lage sein, mobile Inhalte zu empfangen.. Für weitere Informationen senden Sie eine E-Mail an info@vacastudios.com oder rufen Sie die Hotline an: 0800 897 980.
            <br>
            <a href="${pageContext.request.contextPath}/sys/mcg/termcondtion?serviceid=${mcgServiceConfig.serviceId}">Geschäftsbedingung</a>
        </p>
    </div>
</div>     
</body>
</html>