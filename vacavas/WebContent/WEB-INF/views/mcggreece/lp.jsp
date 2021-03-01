<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>GamesHub</title>
    <meta charset="utf-8">
    
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
    color: white;
    margin-top: 500px;
}
img{
height: 250px;
    border: 2px solid #f6d236;
    border-radius: 5%;
}
body {
    width: 100%;
    background-image: url('${pageContext.request.contextPath}/resources/mcggreece/images/background.png');
    background-repeat: no-repeat;
    background-size: contain;
    background-color: #4c5600;
    padding: 235px 20px 0px 20px;
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
}
}
</style>
<body class="text-center">
  <div class="box">
    <!--<h4 style="color:#f1301e"><b>GamesHub</b></h4>-->
    <!--<img id="banner" src="./banner.png" alt="">-->
    <div class="box_text">
        <p class="text1">
		



            2.08 x 3 / εβδομάδα
            <br>
            Για να σταματήσετε την υπηρεσία, στείλτε ΣΤΑΣΗ GMS ΣΤΟ 54422
        </p>
        <div>
		    Εισάγετε τον αριθμό του κινητού σας για να παίξετε!
			 <br>
            
            <input maxlength="10" id="number" type="tel" value="" oninput="value=value.replace(/[^\d]/g,'')" placeholder="69xxxxxxx">
             <br>
			<select name="" style="width: 166px;" id="network">
			<option value="no">Διάλεξε πάροχο:</option> 
			<option value="vodafone">Vodafone</option> 
			<option value="cosmote">Cosmote</option>
			<option value="wind">Wind</option></select>
		</div>
        <!-- <img src="../../static/img/playthree2btn.png" alt=""> -->
        <button class="btn btn-md btn-primary" style="margin: 10px 0px 10px 0px;">Συνέχεια</button>
		<p><input type="checkbox" id="checkterms" value="true">
		 Έχω λάβει γνώση των όρων χρήσης και αποδέχομαι να ενεργοποιήσω την συνδρομητική υπηρεσία Games For You.
                2.08 x 3 / εβδομάδα</p>
            
		<p class="text1 text2">
            Αποποίηση ευθυνών: Κάποιο από το περιεχόμενο θα είναι στα Αγγλικά.Εάν συνεχίζετε να εγγραφείτε, έχετε διαβάσει και αποδέχεστε αυτήν την αποποίηση ευθυνών </p>
        <p style="text-align: justify;">
		
		  <b> Υπηρεσία Gameshub:</b><br>
		    
			Όταν γίνεται η εγγραφή σε αυτή την υπηρεσία και λαμβάνετε δωρεάν μήνυμα το οποίο αποστέλλεται στο κινητό σας τηλέφωνο και περιέχει όλες τις πληροφορίες των υπηρεσιών,
αναγνωρίζετε και συμφωνείτε ότι θα είστε συνδρομητές της υπηρεσίας Gameshub. Χρέωση με κάθε λήψη sms από τα 54422 & 54600. Το κόστος της υπηρεσίας είν
2.08 x 3 / εβδομάδα και η χρέωση γίνεται με 12 γραπτά μηνύματα από το 54422 (€2.08 ανά μήνυμα). Ενδέχεται κάποια από τα μηνύματα από το 54422 να αντικατασταθούν με έως 4 μηνύματα το καθένα από το 54600 (€0.52 / SMS). Σε κάθε περίπτωση το συνολικό κόστος της υπηρεσίας δε θα ξεπερνάει τα 2.08 x 3 / εβδομάδα. Όλες οι τιμές συμπεριλαμβάνουν 24% Φ.Π.Α. Εφαρμόζεται επιπλέον τέλος 
συνδρομητών κινητής τηλεφωνίας υπέρ του Δημοσίου ανάλογα με το ύψος του μηνιαίου λογαριασμού προ ΦΠΑ: 12% (για λογαριασμό μέχρι και €50), 15% (για λογαριασμό από €50.01 μέχρι και €100), 18% (για λογαριασμό από €100.01 μέχρι και €150), ή 20% (για λογαριασμό €150.01 και άνω).Θα λαμβάνετε χρεώσιμα μηνύματα με διαδικτυακό σύνδεσμο προς περιεχόμενο για κινητά. Η σύνδεση με το internet μπορεί να επιφέρει πρόσθετες χρεώσεις, για τις οποίες μπορείτε να συμβουλευτείτε τον πάροχο κινητής τηλεφωνίας σας. Αν θέλετε να σταματήσετε 
αυτή την υπηρεσία, μπορείτε να το κάνετε στέλνοντας ένα μήνυμα με τη φράση STOP GMS, στο 54422 . Για συμμετοχή στην υπηρεσία μας, πρέπει να είστε ο ιδιοκτήτης της συσκευής που χρησιμοποιείται και να είστε πάνω από την ηλικία των 18 ετών ή να έχετε την άδεια αυτού που πληρώνει τον λογαριασμό. Όλα τα προβαλλόμενα εμπορικά σήματα και λογότυπα ανήκουν στους αντίστοιχους ιδιοκτήτες τους. 
 . The organizer company Vaca Mobiles is solely responsible for everything related to the service that offers it. Customer service: 080 022 (3018). or info@vacastudios.com
            
        </p><p><a href="./tc">Όροι Χρήσης</a></p>
    </div>
</div>     
</body>
</html>