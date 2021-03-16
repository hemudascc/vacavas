<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE >
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.js">
  </script>
<style type="text/css">
	.button {
	width:100%;
	max-width:260px;
	background:#009ecf;
	font-size:20px;
	color:#fff;
	padding:15px 20px;
	border:none;
	border-radius:7px;
	margin:10px auto;
	display:table;
}

.terms{
	padding:0 20px;
}
.terms h3{
	margin-bottom:0px;
	font-size:17px;
	font-weight:500;
	color:#898989;
}
.terms p{
	font-size:14px;
	color:#898989;
	margin-bottom:5px;
}
 .priceText{
 display:block;
	text-align:center;
	font-size:14px;
	font-weight:200;
	color:#636365;
}
.input{
	border:1px solid #ababab;
	padding:7px 8px;
	font-size:18px;
	margin:15px auto 5px;
	color:#898989;
}

input[type=image] {
 max-width: 100%; 
 width: auto;
 height: auto;
}
</style>
 <!-- Global site tag (gtag.js) - Google Ads: 606701345 -->
 <script async src="https://www.googletagmanager.com/gtag/js?id=AW-606701345"></script>
 <script>window.dataLayer = window.dataLayer || []; function gtag(){dataLayer.push(arguments);} gtag('js', new Date()); gtag('config', 'AW-606701345'); </script>
 
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes">
<meta http-equiv="cache-control" content="max-age=0">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1,IE=9,IE=8,IE=7">
<title>WEB First Consent</title>



	<script type="text/javascript">
	
	function validateForm(chargeableTypeUrl,cgUrl,portalUrl,serviceId,transId,Wap_mdata){
	
		var isValid=false;
	    var isSubscribed=false;
	    var oredooKuwaitServiceConfig;
	    var msisdn;
	  //  alert("chargeableTypeUrl:: "+chargeableTypeUrl);
	    
	     $.ajax({
			        url: chargeableTypeUrl+"?msisdn="+$("#msisdn").val()+"&serviceid="+serviceId,
			        type: 'GET',  			        
			        async: false,
			        crossDomain: true,
			        data: {
			            format: 'jsonp'
			            
			        },
			        timeout: 90000,
			       // dataType: 'jsonp',
			        success: function(response) {   
			        	var json = JSON.parse(response);
			        	
			        	if(json.subscribed==true){
			        	 isSubscribed=true;
			        	}
			        	
			        	if(json.chargeable==true){
			        		msisdn=json.msisdn;
			        		isValid=true;
			        	}
			        	
			        	oredooKuwaitServiceConfig=json.oredooKuwaitServiceConfig;
			        	 if(isSubscribed==true){
			    			 window.location.href =portalUrl+"?msisdn="+$("#msisdn").val();	
			    			 return false;
			    		 }
			    		 
			    		 if(isValid==false){
			    				alert("Not a valid msisdn to subscribe Arab Vibes service");
			    				return false;
			    			}
			    		 
			    		 //MSISDN=<msisdn>&productID=<productID>&pName=<pName>&pPrice=<pPrice>&pVal=<pVal>&CpId=<CpId>&CpPwd=<CpPwd>&CpName=<CpName>&reqMode=WAP&reqType=Subscription&ismID=<ismID>&transID=<transID>&cpBgColor=&sRenewalPrice=&sRenewalValidity=&Wap_mdata=<Wap_mdata>
			    		url=cgUrl+"?MSISDN="+msisdn+"&productID="+oredooKuwaitServiceConfig.productId+
			    		"&pName="+oredooKuwaitServiceConfig.productName+
			    		"&pPrice="+oredooKuwaitServiceConfig.pricePoint+
			    		"&pVal="+oredooKuwaitServiceConfig.validity+
			    		"&CpId="+oredooKuwaitServiceConfig.cpId+
			    		"&CpPwd="+oredooKuwaitServiceConfig.cpPwd+
			    		"&CpName="+oredooKuwaitServiceConfig.cpName+
			    		"&reqMode=WAP&reqType=Subscription&ismID="+oredooKuwaitServiceConfig.ismId+
			    		"&transID="+transId+"&cpBgColor=&sRenewalPrice="+oredooKuwaitServiceConfig.renewalPrice
			    		+"&sRenewalValidity="+oredooKuwaitServiceConfig.renewalValidity+"&Wap_mdata="+Wap_mdata
			    		+"&serviceid="+oredooKuwaitServiceConfig.serviceId
			    		+"&token=${token}";		
			    		window.location.href =url;
			        	
			        },
			    error: function(response) {
			    	alert("Some Technical problem to validate msisdn. Please try after some time");   
			    }
			  });
			 
		
		return false;
	}
	
	 function  onCancel(url){
		// alert(url);
		 window.location.href =url;
		 return false;
	 };
</script>
</head>	
<body style="text-align: center;padding: 5px;background-color: #f0ddd5;">      		
<form id="frm1" name="frm1"  
 method="get" >
 <p >
  <img style=" width:100%;height: 40%;" src="${img}">
  </p>
  
<table style="width: 100%; align: center;"  border="0">
                       <tbody>
                     
                   
 						<tr>
                              <td width="100%" valign="middle" align="center">
                              
                             <c:if test="${msisdn==null||msisdn=='NA'}">  
                                Msisdn start with 965: 
                               <input id="msisdn" type="text" name="MSISDN"> 
                                 </c:if>
                               <c:if test="${msisdn!=null&&msisdn!='NA'}">   
                                	<input id="msisdn" type="hidden" name="MSISDN" value="${msisdn}"> 
                                </c:if> 
                                </td>
                        </tr>
                    
                    	  <tr>
                                 <td width="100%" align="center">
                               		<p class="priceText">
										You can subscribe to GamezShop for 800.00 fils/7 day for prepaid users and 3000 fils /30 days for postpaid users                                	
                                 </p>                               
                                </td>
                        </tr>
								
                        <tr>
                            <td width="100%" valign="middle" align="center">
                          
                            <input type="submit" name="imageClick" value="Subscribe" 
                                class="button" 
                                onclick="return validateForm('${chargeableTypeUrl}',
                                '${cgUrl}','${portalUrl}','${serviceId}','${transID}','${Wap_mdata}');">
                             </td>
                        </tr>
                       
                         <tr>
						       <td width="100%" valign="middle" align="center">
                                  <input type="button" id="button"
                                   name="back" value="Back" class="button" onclick="onCancel('${portalUrl}')">
                                </td>
                       			 </tr>
                        <tr></tr></tbody></table><table class="terms">
                          		<tbody><tr>
                          			<td>
                          				<h3></h3>
                          			</td>
                          		</tr>
                         		<tr>
                                  <td>
                                            
                                </td>
                                </tr>
                                </tbody></table>
             
        </form>
</div>
</body></html>