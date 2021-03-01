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
.texts {
    text-align: left;
    padding: 5px;
    font-size: 13px;
    color: black;
    font-family: sans-serif;
}
.text-center {
    text-align: center!important;
    width: 100%;
    background-image: url('${context}/resources/messagecloud/background.png');
    background-repeat: no-repeat;
    background-size: contain;
    background-color: #4c5600;
    padding: 235px 20px 0px 20px;
}
.box_text {
    font-size: 14px;
    text-align: center;
    color: black;
    margin-top: 500px;
}
@media only screen and (max-width: 600px) {
    .box_text {
    font-size: 14px;
    text-align: center;
    color: white;
    margin-top: 10px;
}
}
@media screen and (max-width: 768px) and (min-width: 426px) {
    .box_text {
    font-size: 14px;
    text-align: center;
    color: white;
    margin-top: 210px;
}}
@media screen and (max-width: 1024px) and (min-width: 769px) {
    .box_text {
    font-size: 14px;
    text-align: center;
    color: white;
    margin-top: 350px;
}}
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
    <div class="box_text">
        <div class="texts" style="margin-top: 20px;">
            <!-- <img id="tits" src="../../static/img/playtext2.png" alt=""> -->
            <p class="text4">
                Diese Allgemeinen Geschäftsbedingungen gelten für die Nutzung von GAMESHUB. Durch die Nutzung dieses Dienstes erkennt der Kunde diese Allgemeinen Geschäftsbedingungen an und stimmt diesen zu.
                <br>
                <br>
                <b>Zum Spaß spielen</b>
                GAMESHUB ist ein Abonnementdienst. Die Gebühren werden von Ihrer Handyrechnung oder von Ihrem Prepaid-Konto abgebucht. Transport- oder Datengebühren können anfallen. Kontaktieren Sie uns: info@vacastudios.com. Durch Klicken auf Abonnieren erklären Sie sich damit einverstanden, 15 CHF pro Woche zu abonnieren und dass Sie der Eigentümer des Geräts sind und mindestens 18 Jahre alt sind oder die Erlaubnis des Rechnungszahlers haben und die Allgemeinen Geschäftsbedingungen akzeptieren. Sie erhalten uneingeschränkten Zugriff auf die neuesten Inhalte. Um den Dienst zu deaktivieren, wird STOP an 311 gesendet. 
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">1. Art des Eintrags</span>
                <br>
                Abonnenten können auf den Dienst zugreifen, sofern verfügbar
                Diese Internetseite<br>
                SMS<br>
                Das mobile Internet<br>
                SSobald Sie das GAMESHUB-Abonnement abgeschlossen haben, erhalten Sie eine SMS mit einem Link, über den Sie auf den Inhalt zugreifen können. Befolgen Sie die Anweisungen, um den Inhalt Ihrer Wahl herunterzuladen. 
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">2. Abonnieren / stornieren Sie den Service:</span>
                <br>
                Um den Dienst abzubestellen / abzubrechen, senden Sie STOP an 311. Bei dieser Nachricht wird zwischen Groß- und Kleinschreibung unterschieden. Sie erhalten eine Antwort, die die Beendigung des Dienstes bestätigt. 
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">3. Haftung</span>
                <br>
              	3.1. Die Haftung des Veranstalters und seiner Verwaltungsgesellschaft ist auf die Höhe der tatsächlich gezahlten Abonnementgebühren begrenzt<br>
				3.2 Wir haften nicht für Verluste oder Schäden, die wir zu Vertragsbeginn nicht vernünftigerweise hätten vorhersehen können, oder für den Verlust oder die Beschädigung von Daten, Systemen, Geräten,<br>
				3.3. In Bezug auf einen versicherten Preis ist die Haftung des Veranstalters und seiner Verwaltungsgesellschaft auf die Beträge begrenzt, die im Rahmen der geltenden Versicherungspolice tatsächlich erstattungsfähig sind.<br>
				3.4. Nichts in diesen Geschäftsbedingungen soll unsere Haftung für Tod oder Körperverletzung, die durch unsere Fahrlässigkeit verursacht wurden, ausschließen oder einschränken. <br>
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">4. Allgemeine Geschäftsbedingungen für E-Mail und Mobile Marketing:</span>
                <br>
                Indem Sie uns Ihre Mobiltelefonnummer und / oder E-Mail-Adresse mitteilen, stimmen Sie zu, dass Service Name und seine Verwaltungsgesellschaft Ihnen entweder eine gelegentliche E-Mail oder einen Text senden, um Sie über die neuesten Angebote und Aktionen unserer Gruppe zu informieren. Wenn Sie jemals aus einer unserer E-Mail- / Text-Einsendungen entfernt werden möchten, wird in jeder E-Mail ein Link mit der Aufschrift "UNSUBSCRIBE" angezeigt. Alternativ können Sie unsere Hotline anrufen, sobald Sie dies tun. Sie werden innerhalb von 14 Tagen nach Ihrer Anfrage aus zukünftigen Sendungen entfernt. 
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">5. Zuordnung:</span>
                <br>
                Service Name behält sich das Recht vor, die Vereinbarung und Annahme dieser Allgemeinen Geschäftsbedingungen jederzeit ohne vorherige Ankündigung ganz oder teilweise abzutreten. Der Benutzer darf keine seiner Rechte oder Pflichten aus der Zustimmung und Annahme dieser Allgemeinen Geschäftsbedingungen abtreten. 
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">6. Kontakt</span>
                <br>
                Dienstname: GAMESHUB<br>
                Kontakt E-Mail: info@vacastudios.com.<br>
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">7. Änderungen dieser Allgemeinen Geschäftsbedingungen</span>
                <br>
                Wir bemühen uns, hohe Standards zu erfüllen. Unsere Richtlinien und Verfahren werden daher ständig überprüft. Von Zeit zu Zeit können wir unsere Allgemeinen Geschäftsbedingungen und Datenschutzrichtlinien aktualisieren, um die Benutzerfreundlichkeit zu optimieren und gesetzliche und behördliche Anforderungen zu erfüllen. Dementsprechend empfehlen wir Ihnen, diese Seiten regelmäßig zu überprüfen, um unsere aktuellen Richtlinien zu überprüfen. 
                <br>
                <br>
                <span style="color: #a8653a;font-size: 13px;
                font-weight: 600;">8. Marken / Logos / Bilder</span>
                <br>
                Marken, Dienstleistungsmarken, Logos (einschließlich, aber nicht beschränkt auf die einzelnen Namen von Produkten und Einzelhändlern) sind Eigentum ihrer jeweiligen Inhaber. Die verwendeten Marken / Logos / Bilder wurden weder mit der Teilnahme an dieser Werbung erstellt, noch wurden sie von den Eigentümern in irgendeiner Weise überprüft oder autorisiert.
            </p>
        </div>
    </div>
</div>     
</body>
</html>