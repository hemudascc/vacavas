$(document).ready(function(){
	
	$("#accept-btn").click(function(){
		$.get("./mondiapay/purchase-subscription?token="+$("#token").val()+"&authorization="+$("#accessToken").val(),function(data, status){
			var obj = JSON.parse(data);
			window.location.href=obj._links.initiatePurchase.href;
		});
	});
	
	
	
});