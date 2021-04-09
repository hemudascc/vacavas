let msisdn,token,subscriptionContractId,sessionToken;
var numberRegex = /^\s*[+-]?(\d+|\.\d+|\d+\.\d+|\d+\.)(e[+-]?\d+)?\s*$/;
var changeLangLink = "./change-lang";
var portalurlsentcount=0;
$(document).ready(function(){
	token = $("#token").val();
	lang = $("#langvalue").val();
	var portal = $("#protalUrl").val();
	portalUrl = portal.split("?")[0];
	if($("#sessionToken").val()===''){
		sessionToken = TPay.HeaderEnrichment.sessionToken();	
	}
	else{
		sessionToken = $("#sessionToken").val();
	}
	
	if($("#flow").val()==='wifi'){
		$("#pin-label").hide();
		$("#msisdn-label").show();
		$("#msisdn").show();
		$("#pin").hide();
		$("#send-pin").show();
		$("#subscribe-pin").hide();
		$("#resend-pin").hide();
		$("#message").hide();
		$("#missing-msisdn-modal").modal("show");
	}
	
	/*click on main subscribe button*/
	$("#sub-button").click(function(){
		/*$("#pin-label").hide();
		$("#msisdn-label").show();
		$("#msisdn").show();
		$("#pin").hide();
		$("#send-pin").show();
		$("#subscribe-pin").hide();
		$("#resend-pin").hide();
		$("#message").hide();
		$("#missing-msisdn-modal").modal("show");*/
		
		if(TPay.HeaderEnrichment.operatorCode() === $("#operatorCode").val()) {
			TPay.HeaderEnrichment.hasSubscription("", function(hasSub, subId){
				if(!hasSub) {
					TPay.HeaderEnrichment.confirm($("#subscriptionPlanId").val(), $("#catalogName").val(), $("#paymentProductId").val(), function (result, refNo) {
						if(result == true){
							$.get("./"+$('#baseURL').val()+"/send-pin?token="+token+"&msisdn="+TPay.HeaderEnrichment.msisdn()+"&sessionToken="+sessionToken+"&lang="+$("#langvalue").val()+"&headerEnrichmentReferenceCode="+refNo, function(data, status){
								var obj = JSON.parse(data);
								if(obj.operationStatusCode == 0){
									subscriptionContractId = obj.subscriptionContractId;
									sendWelcomeMT(TPay.HeaderEnrichment.msisdn(),token,subscriptionContractId);
									setTimeout(function(){window.location.href=obj.portalUrl;},1000);
								}else{
									redirectToWifi();
								}
							});
						}
						else{
							redirectToWifi();
						}
					},operatorCode,msisdn);
				}else{
					window.location.href="./"+$('#baseURL').val()+"/redirect-portal/"+subId+"/"+lang+"/"+token 
				}
			});
		}else{
			$("#pin-label").hide();
			$("#msisdn-label").show();
			$("#msisdn").show();
			$("#pin").hide();
			$("#send-pin").show();
			$("#subscribe-pin").hide();
			$("#resend-pin").hide();
			$("#message").hide();
			$("#missing-msisdn-modal").modal("show");
		}
	});
	
	$("#send-pin").click(function(){
		$("#send-pin").prop("disabled",true);
		if(numberRegex.test($("#msisdn").val())){
		$.get("./"+$('#baseURL').val()+"/send-pin?token="+token+"&msisdn="+$("#msisdn").val()+"&sessionToken="+sessionToken+"&lang="+$("#langvalue").val(), function(data, status){
			var obj = JSON.parse(data);
			//var obj = JSON.parse('{"subscriptionContractId":"12345", "operationStatusCode":51, "errorMessage":"This user already subscribed"}');
					if(obj.operationStatusCode == 0){
						$("#pin-label").show();
						$("#msisdn-label").hide();
						$("#msisdn").hide();
						$("#pin").val("");
						$("#pin").show();
						$("#send-pin").hide();
						$("#subscribe-pin").show();
						$("#resend-pin").show();
						$("#message").hide();
						subscriptionContractId = obj.subscriptionContractId;
				     }else {
				    	 if(obj.errorMessage.includes("This user already subscribed")){
				    		 if(lang==0){
				    			 $("#message").html('<span>'+$("#already-sub-msg").val()+' </span> <a href="#" onclick="sendPortalUrlMt()" style="color: #59fb5f;">click here</a> to get the URL');	 
				    		 }else{				    			 
				    			 $("#message").html('للحصول على عنوان موقع البوابة <a href="#" onclick="sendPortalUrlMt()" style="color: #59fb5f;">انقر هنا</a> أو '+'<span>'+$("#already-sub-msg").val()+' </span>');
				    		 } 
				    	 }else if(obj.errorMessage.includes("Expired Session Token")){
				    		 $("#message").text($("#session-expired-msg").val());
				    	 }
				    	 else if(obj.errorMessage.includes("wait for 2 minutes")){
				    		 $("#message").text($("#wait-msg").val());
				    	 }
				    	 else{
				    		 $("#message").text($("#enter-valid-number-msg").val()); 
				    	 }
				    	 $("#message").show();
				    	 $("#send-pin").prop("disabled",false);
					}
		});}else{
			$("#subscribe-pin").prop("disabled",false);
			 $("#message").text($("#enter-valid-number-msg").val());
	    	 $("#message").show();
		}
	});	
		$("#resend-pin").click(function(){
			$("#resend-pin").prop("disabled",true);
			$.get("./"+$('#baseURL').val()+"/resend-pin?token="+token+"&msisdn="+$("#msisdn").val()+"&subscriptionContractId="+subscriptionContractId+"&lang="+$("#langvalue").val(), function(data, status){
				var obj = JSON.parse(data);
				if(obj.operationStatusCode == 0){
					$("#message").text($("#pin-resend-msg").val());
			    	$("#message").show();
			    	
				}else{
					$("#resend-pin").prop("disabled",false);
				}
			});
		});
		
		$("#subscribe-pin").click(function(){
			$("#subscribe-pin").prop("disabled",true);
			if(numberRegex.test($("#pin").val())){
			$.get("./"+$('#baseURL').val()+"/validate-pin?token="+token+"&msisdn="+$("#msisdn").val()+"&subscriptionContractId="+subscriptionContractId+"&pin="+$("#pin").val()+"&lang="+$("#langvalue").val(), function(data, status){
				var obj = JSON.parse(data);
				if(obj.operationStatusCode == 0){
					//$("#message").text("Pin has been resent to you.");
			    	//$("#message").show();
					$("subscribe-pin").hide();
					$("resend-pin").hide();
					$("pin").hide();
			    	//window.open(obj.portalUrl);
					sendWelcomeMT(msisdn,token,subscriptionContractId);
					setTimeout(function(){
						window.location.href=obj.portalUrl;
					},1000)
				}
				else{
					$("#subscribe-pin").prop("disabled",false);
					$("#message").text($("#enter-valid-pin-msg").val());
			    	$("#message").show();
				}
			});}
			else{
				 $("#message").text($("#enter-valid-pin-msg").val());
		    	 $("#message").show();
		    	 $("#subscribe-pin").prop("disabled",false);
			}
		});
		function validate(){
			if ((event.keyCode < 48 || event.keyCode > 57)) 
			{
			   event.returnValue = false;
			}
		}
		

});

function exit(){
	window.location.href=$("#portalurl").val();
} 
 
function unsubscribe(){
	
		$("#unsubscribe-button").prop("disabled",true);
		$("#unsubscribe-button1").prop("disabled",true);
			$.get("./unsubscribe?msisdn="+$("#msisdn").val()+"&lang="+$("#langvalue").val()+"&token="+$("#tokenvalue").val()+"&productId="+$("#productId").val(), function(data, status){
				if(parseInt(data)==51){
					$("#consent-p").css('display',"none");
					$("#unsubscribe-msg").text($("#already-unsub-message").val());
					$("#unsubscribe-msg").css('display',"block");
				}else{
				var obj = JSON.parse(data);
				
				if(obj.operationStatusCode == 0){
					$("#consent-p").css('display',"none");
					$("#unsubscribe-msg").text($("#success-unsub-message").val());
					$("#unsubscribe-msg").css('display',"block");
					setTimeout(function(){ window.location.href="http://192.241.167.189:8080/vacavas/sys/sub?adid=1&evid="+$("#campId").val()+"&ref=test"}, 3000);
				}else{
					$("#consent-p").css('display',"none");
					$("#unsubscribe-msg").text($("#already-unsub-message").val());
					$("#unsubscribe-msg").css('display',"block");
				}
				}
			});

}
 
function changeLang(){
	var x = document.getElementById("redirectStatus").value;
	var lang = document.getElementById("lang").value;
	if(x=="true"){
		window.location.href=changeLangLink+"?lang="+lang+"&token="+token+"&sessionToken="+sessionToken;
	}else{
		window.location.href="./"+$('#baseURL').val()+"/change-lang?lang="+lang+"&token="+token+"&sessionToken="+sessionToken;
	}
}

function sendWelcomeMT(msisdn,token,subscriptionContractId){
	$.get("./"+$('#baseURL').val()+"/send-welcome-mt?token="+token+"&msisdn="+$('#msisdn').val()+"&subscriptionContractId="+subscriptionContractId+"&lang="+$("#langvalue").val(), function(data, status){
		var obj = JSON.parse(data);
		if(obj.messageDeliveryStatus==true){
			$("#message").text($("#success-sub-msg").val());
		}
	});
}

function sendPortalUrlMt(){
	$.get("./"+$('#baseURL').val()+"/send-content-mt?msisdn="+$('#msisdn').val()+"&lang="+$("#langvalue").val()+"&token="+token, function(data, status){
		var obj = JSON.parse(data);
		if(obj.messageDeliveryStatus==true){
			$("#message").text($("#success-content-msg").val());
		}
	});
}
function redirectToWifi(){
	window.location.href="./"+$('#baseURL').val()+"/wifi-flow?lang="+lang+"&token="+token
}
