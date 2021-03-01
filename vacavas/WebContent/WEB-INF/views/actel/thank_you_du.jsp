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
  <title>${actelServiceConfig.serviceName}</title>
  
 </head>
<body>


			<section>
			<div class="col-md-12 col-sm-12 col-lg-12">
			<p style="text-align:center;"><img src="${pageContext.request.contextPath}/resources/actel/bannergd.jpg" class="img-responsive" height="200" width="300"></img></p>
			<p style="text-align:center;font-size:14px;"><strong>${actelServiceConfig.serviceName}</strong><br/></p>
			<p style="text-align:center;font-size:14px;font-weight:bold;">
			<strong>You have been successfully subscribed </strong> <br/><strong>to ${actelServiceConfig.serviceName} service.</strong></p>
			
				<div style="text-align:center;font-size:14px;">
				We have sent you a confirmation SMS that <br/>includes your subscription <br/> details for your records.	
				</div>
				
				<div style="text-align:center;font-size:14px;">
				Please keep that message for future refrence.
				</div>

				<%-- <div style="text-align:center;font-size:14px;">
				Click <a href="${portalurl}">here</a>	to access the ${actelServiceConfig.serviceName}
				</div> --%>
				<div style="text-align:center;font-size:14px;">
				For support,please contact vas.support@vacastudios.com
				</div>
			</div>
			</section>
 </body>
</html>
