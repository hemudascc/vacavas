
<!DOCTYPE html>

<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Report</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  
  <style type="text/css">
div {
    border: 0px solid;
    resize: both;
    overflow: auto;
}
p { word-break: break-all }
</style>

  
  <style>
    label, input { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
  </style>
  
<script src="../../resources/js/jquery-1.12.4.js"></script>
  <script src="../../resources/js/jquery-ui.js"></script>
  <script  src="../../resources/js/jquery.datetimepicker.js"></script>
<script>
$( function() {
    var dialog, form,
    operatorName = $( "#operatorName" ),
      networkName = $( "#networkName" ),
      noOfConversion = $( "#noOfConversion" ),
      allFields = $( [] ).add( operatorName ).add( networkName ).add( noOfConversion ),
      tips = $( ".validateTips" ); 
    
    $( "#fromTime" ).datepicker({ dateFormat: 'yy-mm-dd'});
    $( "#toTime" ).datepicker({ dateFormat: 'yy-mm-dd'});
    
    function createCRRequest() {    	
      var valid = true;    
      //103.231.8.72
    $.post("http://192.241.167.189:8080/vacavas/sys/rcerpaotrt/crrequest", $("#crform").serialize(), function(res){        
        alert(res);        
        dialog.dialog( "close" );
    });
    allFields.removeClass( "ui-state-error" );     
    return valid;
    }
 
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 600,
      width: 350,
      modal: true,
      buttons: {
        "Create CR Request": createCRRequest,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
        close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
 
    form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      alert("dialog.find "+$(this).attr('action'));
      //createCRRequest(); 
      
    });
    
   
    $("button[name^='create_crrequest']").button().on( "click", function() {
    	
    	var id = $(this).attr('id'); 
    	
    	$('#operatorName').val($('#operatorName'+id).val());    	
    	$('#operatorId').val($('#operatorId'+id).val());    	
    	$('#networkName').val($('#networkName'+id).val());    	
    	$('#adnetworkCampaignId').val($('#adnetworkCampaignId'+id).val());   
    	
        dialog.dialog( "open" );
    });
  } );
  
 
  </script>


  </head>
<body>

<div id="dialog-form" title="Create CR Request">
  <p class="validateTips">All form fields are required.</p>
 
  <form id="crform" name="crform" >
    <fieldset>
      <label for="operatorName">OP Name</label>
      <input type="text" readonly="readonly" name="operatorName" id="operatorName" value="" class="text ui-widget-content ui-corner-all">
       <input type="hidden" name="operatorId" id="operatorId" value="" class="text ui-widget-content ui-corner-all">
      <label for="networkName">Adnetwork Name</label>
      <input type="text" readonly="readonly" name="networkName" id="networkName" value="" class="text ui-widget-content ui-corner-all">
      <input type="hidden" name="adnetworkCampaignId" id="adnetworkCampaignId" value="" 
      class="text ui-widget-content ui-corner-all">
      <label for="noOfConversion">No Of Conversion</label>
      <input type="text" name="noOfConversion" id="noOfConversion" value="" class="text ui-widget-content ui-corner-all"> 
     
      <label for="fromTime">From Time</label>
      <input type="text" name="fromTime" id="fromTime"  class="text ui-widget-content ui-corner-all"> 
      
      <label for="toTime">To Time</label>
      <input type="text" name="toTime" id="toTime"  class="text ui-widget-content ui-corner-all"> 
      
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>


<div >
   <p>
   <%
   String url=request.getHeader("referer");
   if(url==null||!url.contains("hourlyreport")){
	url="http://"+request.getLocalAddr()+":8080/vacavas/sys/rcerpaotrt/hourlyreport";   
   }
   %>
    <h3 style="color: red;white-space:nowrap;"><a 
	href="<%=url%>" >Hourly Report</a>
	<a href="<%="http://"+request.getLocalAddr()+":8080/vacavas/sys/rcerpaotrt/adnoconfig"%>">Adnetwork Callback Config</a>
	</h3>
	</p>
	</div>
	
 <table width="80%" border="1" align="center">


	<tr>
		<th>Operator Name</th>
		<th>Report Date</th>
		<th>Adnetwork Name</th>
		<th>CR</th>
		<th>Total hits</th>
		<th>Duplicate count</th>
		<th>Reverse Click Count</th>
		<th>Block Click Count</th>
		<th>Already Subscribed Count</th>
		<th>Total redirected to cg</th>
		<th>Subscription</th>
	
		
		<th>Sent count</th>
		<th>Manual Sent count</th>
		<th>Not sent</th>
		<th>Grace count</th>
         <th>Grace sent count</th>
         <th>Total Sent count</th>
		
		<th>Revenue</th>
		<th>Spent</th>
		<th>Net Amount</th>
		<th>Churn count</th>
		<th>Churn%</th>
		<th>Last Activation Time</th>
		<th>Last Click Time</th>
		
	</tr>

	<c:forEach var="entry" items="${mapLiveReport}" varStatus="outerloop">
		<c:if test="${not empty entry.value}">


			<c:forEach var="liveReport" items="${entry.value}" varStatus="loop">
				<tr bgcolor="">
					<c:if test="${loop.index==0}">
						<td  rowspan="${entry.value.size()}">${liveReport.operatorName}-Max Churn:-${mapOperator.get(liveReport.operatorId).maxchurnP20}
						-Block:-${mapOperator.get(liveReport.operatorId).blockActivation}</td>
					</c:if>
					<td><fmt:formatDate pattern="dd-MM-yyyy"
							value="${liveReport.reportDate}" /></td>
					<td>${liveReport.networkName}</td>
					<td>
					<input id="operatorName${outerloop.index}${loop.index}"  type="hidden" value="${liveReport.operatorName}">
					<input id="operatorId${outerloop.index}${loop.index}"  type="hidden" value="${liveReport.operatorId}">
					<input id="networkName${outerloop.index}${loop.index}"  type="hidden" value="${liveReport.networkName}">
					<input id="adnetworkCampaignId${outerloop.index}${loop.index}"  type="hidden" value="${liveReport.adnetworkCampaignId}">
					<button id="${outerloop.index}${loop.index}" name="create_crrequest${loop.index}">CR</button>
					</td>
					<td>${liveReport.clickCount}</td>
					<td>${liveReport.duplicateClickCount}</td>
					<td>${liveReport.reverseClickCount}</td>
					<td>${liveReport.blockClickCount}</td>
					<td>${liveReport.alreadySubscribedCount}</td>
					<td>${liveReport.validClickCount}</td>
					<td>${liveReport.conversionCount}</td>
					
				
					
					<td>${liveReport.sendConversionCount}</td>
						<td>${liveReport.sendManualConversionCount}</td>
					

					<td>${liveReport.conversionCount-liveReport.sendConversionCount}</td>
                  <td>${liveReport.graceConversionCount}</td>
                  <td>${liveReport.graceSendConversionCount}</td>
                <td>${liveReport.graceSendConversionCount+liveReport.sendConversionCount}</td>
               
<%--                 <td><fmt:formatNumber pattern="##.##" value="${(liveReport.graceSendConversionCount+liveReport.sendConversionCount*100)/liveReport.clickCount}"/></td> --%>
					
					<td>${liveReport.amount}</td>
					<td><fmt:formatNumber pattern="##.##" value="${liveReport.spend}"/></td>
					<td bgcolor="${(liveReport.amount-liveReport.spend)<0?'red':'white'}"><fmt:formatNumber pattern="##.##" value="${liveReport.amount-liveReport.spend}"/></td>
					<td>${liveReport.churnDctCount}</td>
					
					<c:choose>
						<c:when
							test="${liveReport.churnDctCount!=0&&liveReport.conversionCount!=0}">
							<td><fmt:formatNumber pattern="##.##"
									value="${(liveReport.churnDctCount*100)/liveReport.conversionCount}" /></td>
						</c:when>
						<c:otherwise>
							<td>0</td>
						</c:otherwise>
					</c:choose>
					<td class="${liveReport.lastActivationColoumnColor}">${liveReport.lastActivationTime}</td>
					<td class="${liveReport.lastClickColoumnColor}">${liveReport.lastClickTime}</td>
									
				</tr>
			</c:forEach>
			
			<tr>
			<td colspan="7" align="right">MTD CHURN</td>
			<c:if test="${not empty mapMTDChurn[entry.key]}">
			<c:forEach items="${mapMTDChurn[entry.key]}" var="mtdLiveReportCurn" >		
			<td><fmt:formatNumber pattern="##.##"
									value="${mtdLiveReportCurn.churnDctCount*100/mtdLiveReportCurn.conversionCount}"/></td>
			</c:forEach>
			</c:if>
			
			
			<td colspan="7" align="right">Previous day CHURN:</td>
			<c:if test="${not empty mapLastDayChurn[entry.key]}">
			<c:forEach items="${mapLastDayChurn[entry.key]}" var="ldLiveReportCurn" >		
			<td><fmt:formatNumber pattern="##.##"
									value="${ldLiveReportCurn.churnDctCount*100/ldLiveReportCurn.conversionCount}"/></td>
			</c:forEach>
			</c:if>
			
			<td colspan="7" align="right"> Last 3 Day churn :</td>
			<c:if test="${not empty mapLast3DayChurn[entry.key]}">
			<c:forEach items="${mapLast3DayChurn[entry.key]}" var="l3dLiveReportCurn" >		
			<td><fmt:formatNumber pattern="##.##"
									value="${l3dLiveReportCurn.churnDctCount*100/l3dLiveReportCurn.conversionCount}"/></td>
			</c:forEach>
			</c:if>
			
			
			</tr>
		</c:if>
	</c:forEach>

</table>
</body>
</html>
