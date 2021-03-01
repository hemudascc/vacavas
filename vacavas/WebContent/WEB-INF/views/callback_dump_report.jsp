
<!DOCTYPE html>

<html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/jquery.datetimepicker.css" />" rel="stylesheet">

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Callback Report</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="../../resources/js/jquery-1.12.4.js"></script>
  <script src="../../resources/js/jquery-ui.js"></script>
  <script  src="../../resources/js/jquery.datetimepicker.js"></script>
<!-- Export excel file -->  
<link href="/resources/tableexport.css" rel="stylesheet">

<script src="../../resources/js/FileSaver.js"></script>
<script src="../../resources/js/tableexport.js"></script>
<!-- End Export excel file --> 
  <style>
  	 .mtable  { 
     width:40%; 
    
     }
     .mtable  tr td { 
     width:25%; 
     text-align:center;
     align=center;
     padding: 3px;
    
     }
    table{
    border-spacing: 0px;
    border-collapse: separate;} 
    select {
    
    padding:3px;
    margin: 0;
    -webkit-border-radius:4px;
    -moz-border-radius:4px;
    border-radius:4px;
    -webkit-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    -moz-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    background: #f8f8f8;
    color:#888;
    border:none;
    outline:none;
    display: inline-block;
    -webkit-appearance:none;
    -moz-appearance:none;
    appearance:none;
    cursor:pointer;
}

</style>
    <script>
    
  $( function() {  
	  //$("table").tableExport();
	  $("#reporttable").tableExport({

  // Displays table headers (th or td elements) in the <thead>
  headers: true,                    

  // Displays table footers (th or td elements) in the <tfoot>    
  footers: true, 

  // Filetype(s) for the export
  formats: ["xls", "csv", "txt"],           

  // Filename for the downloaded file
  fileName: "id",                         

  // Style buttons using bootstrap framework  
  bootstrap: false,

  // Automatically generates the built-in export buttons for each of the specified formats   
  exportButtons: true,                          

  // Position of the caption element relative to table
  position: "bottom",                   

  // (Number, Number[]), Row indices to exclude from the exported file(s)
  ignoreRows: null,                             

  // (Number, Number[]), column indices to exclude from the exported file(s)              
  ignoreCols: null,                   

  // Removes all leading/trailing newlines, spaces, and tabs from cell text in the exported file(s)     
  trimWhitespace: false         

});

    $( "#fromTime" ).datepicker({ dateFormat: 'yy-mm-dd'});
    $( "#toTime" ).datepicker({ dateFormat: 'yy-mm-dd'});
  });
 </script>
	
	<script type="text/javascript">
	function callProduct(opid){
	  // alert("change:::::::::: "+opid+", serverip: <%=request.getLocalAddr()%>");
	   
   // var opid = $(this).val();
    $.ajax({
        type: 'GET',
        url: "http://<%=request.getLocalAddr()%>/ccsub/cnt/rcerpaotrt/productdetail?opid=" + opid,
        success: function(data){
            var product=$('#productId'), option="";
            product.empty();
            option = option + "<option value='0'>Select Product</option>";
            for(var i=0; i<data.length; i++){
                option = option + "<option value='"+data[i].id + "'>"+data[i].productName + "</option>";
            }
            product.append(option);
        },
        error:function(xhr, ajaxOptions, thrownError){
        	//alert(xhr.status);
            alert("Error");
        }

    });
};
</script>
	

<script type="text/javascript">
$(function(){
$('.formselect').change(function(){
	// alert("change");
	//$("#otpform").attr("action", "${pageContext.request.contextPath}/cnt/bz/change/msisdnprefix");
	$("#reportform").submit();
})});
</script>
</head>
<body>

	<div >
   <p>
   <%
   String url=request.getHeader("referer");
   if(url==null||!url.contains("hourlyreport")){
	url="http://"+request.getLocalAddr()+"/ccsub/cnt/rcerpaotrt/hourlyreport";   
   }
   %>
    <h3 style="color: red;white-space:nowrap;"><a 
	href="<%=url%>" >Hourly Report</a></h3>
	</p>
	</div>
	
    
<form:form  modelAttribute="AggReport" name="reportform" id="reportform"
 action="${pageContext.request.contextPath}/cnt/rcerpaotrt/callbackdump">
<table  border="1" class="mtable" align="center">

	 <tr>
     
     <td>
         <p> Aggregator
         <form:select class="formselect" name="aggregatorId" id="aggregatorId" path="aggregatorId" > 
						<form:option value="" label="Select " />
						<c:forEach var="aggregator" items="${listAggregator}"
								varStatus="aggregatorloop">
							<form:option value="${aggregator.id}"
							 label="${aggregator.name}" ></form:option>
						</c:forEach>
		   </form:select>
		   </p>
           </td>                     
           <td>
           <p>
           Operator
             <form:select class="formselect" name="opid" id="opid" path="opid" > 
						<form:option value="" label="Select Operator" />
						<c:forEach var="operator" items="${operatorList}"
								varStatus="oploop">
							<form:option value="${operator.operatorId}" 
							label="${operator.operatorName}"></form:option>
						</c:forEach>
				</form:select>
				</p>
           </td>  
           </tr>
               <tr>   
           <td>
           <p>
           	Product
             <form:select  name="productid" id="opid" path="productId" > 
						<form:option value="" label="Select Product" />
						<c:forEach var="product" items="${productList}"
								varStatus="productloop">
							<form:option value="${product.id}" 
							label="${product.productName}"></form:option>
						</c:forEach>
				</form:select>
				</p>
           </td>     
           <td>
            <label for="adnetworkId">Adnetwork :</label>&nbsp;
      <form:select id="adnetworkId" path="adnetworkId">
						<form:option value="0" label="Select Adnetwork" />
						<c:forEach var="adnetwork" items="${adnetworksList}"
							varStatus="adnetworkloop">
							<form:option value="${adnetwork.adNetworkId}" 
							label="${adnetwork.networkName}"></form:option>							
						</c:forEach>
				</form:select>
           </td>                
          </tr>
     
          
  <tr>
  <td > 
  	  <label for="fromTime">From Time :</label>&nbsp;		
     <form:input type="text" path="fromTime" name="fromTime" id="fromTime" 
      class="text ui-widget-content ui-corner-all"/>
   </td>
    <td > 
      <label for="toTime">To Time :</label>&nbsp;
      <form:input type="text" path="toTime" name="toTime" id="toTime" 
         class="text ui-widget-content ui-corner-all"/>
   </td>
     </tr>     
     
     <tr>
  <td>
         
         <label for="adnetworkId">Msisdn :</label>&nbsp;
          <form:input type="text" path="msisdn" name="msisdn" id="msisdn" 
      class="text ui-widget-content ui-corner-all"/>
           </td>
    <td > 
    
   </td>
     </tr> 
     
      <tr>
      <td colspan="2">
      <input type="submit" value="Find Report" />
   
      <form:button type="reset" class="btn btn-danger btn-lg btn-block">
    RESET
</form:button>
      </td></tr>            
	</table>
	
	<br><br>
	<table id="reporttable" width="80%" border="1" align="center">
	<tr>
		<th>id</th>
		<th>Msisdn</th>	
		<th>Aggregator Name</th>
		<th>Action</th>
		<th>Operator Name</th>
		<th>Service Name</th>
		<th>Product Name</th>		
		<th>Report Date</th>	
		<th>Amount</th>
		<th>Token</th>
		<th>Click Id</th>
		<th>Send To Adnetwork</th>
	</tr>
			<c:forEach var="vwCallbackDump" items="${reportList}" varStatus="loop">
				<tr bgcolor="">
				    <td>${vwCallbackDump.id}</td>	
					<td>${vwCallbackDump.msisdn}</td>
					<td>${mapAggregator[mapOperator[vwCallbackDump.operatorId].aggregatorId].name}</td>			
					<td>${vwCallbackDump.action}</td>	
					<td>${vwCallbackDump.operatorName}</td>					
					<td>${vwCallbackDump.serviceName}</td>
					<td>${vwCallbackDump.productName}</td>
					<td>${vwCallbackDump.createTime}</td>					
					<td>${vwCallbackDump.amount}</td>
					<td>${vwCallbackDump.token}</td>
					<td>${vwCallbackDump.clickId}</td>
					<td>${vwCallbackDump.sendToAdnetwork}</td>
					
				</tr>
			</c:forEach>
			</table>
	</form:form>
	<br><br>
	<br><br>
</body>
</html>
