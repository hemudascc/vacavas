<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE >
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
 <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes">
<meta http-equiv="cache-control" content="max-age=0">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1,IE=9,IE=8,IE=7">
<title>WEB First Consent</title>



	<script type="text/javascript">
	function validateForm(validatemsisdn,chargeableTypeUrl){
		
		 isValid=false;
		if(validatemsisdn=='true'){
			//alert("validatemsisdn:::::::::::::::::: ");
			 $.ajax({
			        url: chargeableTypeUrl+"?msisdn="+$("#msisdn").val(),
			        type: 'GET',       
			        data: {
			            format: 'jsonp'            
			        },
			        success: function(response) {        	
			        	var json = $.parseJSON(response);  
			        	//alert("chargeable "+json.chargeable); 
			        	isvalid=json.chargeable;
			        	if(json.chargeable==true){
			        		isValid=true;
			        		//alert("chargeable");   
			        	}
			        },
			        error: function(xhr, status, error) {
				    	var err = eval("(" + xhr.responseText + ")");
				    	  alert(err.Message);	
				    	//alert("Some Technical problem to validate msisdn. Please try after some time");   
				    }
			  });
		}
		
		if(isValid==false){
			alert("Not a valid msisdn to subscribe KidoKingdom servcie");
			return false;
		}
		$('#frm1').submit();
		return false;
	};
	
	 function  onCancel(url){
		 alert(url);
		 window.location.href =url;
		 return false;
	 };
		function submitToCG()
		{   
			document.frm1.submit();
		}
</script>
</head>	
<body onload="submitToCG()">
<div id="div1" style="position:fixed; float:left; height:100%; overflow:auto; width:100%; right:0; bottom:0; left:0;">
        		
<form id="frm1" name="frm1" action="${cgUrl}" method="get" >

<table class="wwFormTable">
                </table><table style="width: 100%; align: center;" height="100%" border="0">
                       <tbody>
                       <tr>
                       		
                                <td width="100%" align="center">
                                        
                                </td>
                        </tr>
                     <tr>
                                 <td width="100%" align="center">
                                <p style="font-size: 12px; color:#898989;">
                                </p>
                                </td>
                        </tr>
                   
 						<tr>
                              <td width="100%" valign="middle" align="center">
                              <input id="msisdn" type="hidden" name="MSISDN" value="${msisdn}"/> 
                              <input  type="hidden" name="productID" value="${oredooKuwaitServiceConfig.productId}"/> 
                              <input  type="hidden" name="pName" value="${oredooKuwaitServiceConfig.productName}"/> 
                              <input  type="hidden" name="pPrice" value="${oredooKuwaitServiceConfig.pricePoint}"/> 
                              <input  type="hidden" name="pVal" value="${oredooKuwaitServiceConfig.validity}"/> 
                              <input  type="hidden" name="CpId" value="${oredooKuwaitServiceConfig.cpId}"/> 
                              <input  type="hidden" name="CpPwd" value="${oredooKuwaitServiceConfig.cpPwd}"/> 
                              <input  type="hidden" name="CpName" value="${oredooKuwaitServiceConfig.cpName}"/> 
                              <input  type="hidden" name="ismID" value="${oredooKuwaitServiceConfig.ismId}"/> 
                              <input  type="hidden" name="transID" value="${transID}"/>                              
                              <input  type="hidden" name="reqMode" value="WAP"/>
                              <input  type="hidden" name="reqType" value="Subscription"/>     
                              <input  type="hidden" name="cpBgColor" value=""/>                              
                              <input  type="hidden" name="sRenewalPrice" value="${oredooKuwaitServiceConfig.renewalPrice}"/>
                              <input  type="hidden" name="sRenewalValidity" value="${oredooKuwaitServiceConfig.renewalValidity}"/>
                              <input  type="hidden" name="Wap_mdata" value="${Wap_mdata}"/> 
                              <input  type="hidden" name="token" value="${token}"/>
                             <input  type="hidden" name="serviceid" value="${oredooKuwaitServiceConfig.serviceId}"/> 
								
                              </td>
                        </tr>
                    
                         <tr>
                                 <td width="100%" align="center">

                                </td>
                        </tr>
								
                        <tr>
                            <td width="100%" valign="middle" align="center">

                                </td>
                        </tr>
                         <tr>
						       <td width="100%" valign="middle" align="center">

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