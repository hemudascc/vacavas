function unsub(id,unsubUrl,redirectUrl){ 
	
    $.ajax({
        url: unsubUrl,
        type: 'GET',
        crossDomain:true,
        data: {
            format: 'jsonp'
            
        },
        timeout: 180000,
        success: function(response) {
        	
        	var json = $.parseJSON(response);
        	alert(json.messgae);
        	$("#"+id).text(); //Response
        	if(redirectUrl!=null){
        	location.href=redirectUrl;
        	}else{
        	setTimeout(function(){// wait for 5 secs(2)
                location.reload(); // then reload the page.(3)
           }, 5000); 
           }
        },
    error: function(textstatus,response) {
        alert("Some technical issue to unsubscribe.please try after some time");
        setTimeout(function(){// wait for 5 secs(2)
            location.reload(); // then reload the page.(3)
       }, 5000); 
    }
    });
    return false; // for good measure
};
