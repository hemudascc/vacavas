
<!DOCTYPE html>

<html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<head>
<link href="<c:url value='/resources/css/main.css'/>" />

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Report</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<style type="text/css">
div {
    border: 0px solid;
    resize: both;
    overflow: auto;
}

select  {
    outline: 0;
    overflow: hidden;
    height: 30px;
    background: #DCDCDC;
    color: black;
    border: #2c343c;
    padding: 5px 3px 5px 10px;
    -moz-border-radius: 6px;
    -webkit-border-radius: 6px;
    border-radius: 10px;
}

select option {border: 1px solid #000; background: #DCDCDC;}


</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
$('.formselect').change(function(){
	//alert("change");
	//$("#otpform").attr("action", "${pageContext.request.contextPath}/sys/bz/change/msisdnprefix");
	$("#reportform").submit();
})});
</script>

</head>
<body>


	<div >
	<p>
	<h3 style="color: red;">
	<a href="http://192.241.167.189:8080/vacavas/sys/rcerpaotrt/hourlyreport">International |
	</a>
	</h3>
	
	</p>
	
	 <p>
    <h3 style="color: red;white-space:nowrap;"><a 
	href="http://<%=request.getLocalAddr()%>:8080/vacavas/sys/rcerpaotrt/currentstats">Adnetwork Report</a> |
	
	<a 
	href="http://<%=request.getLocalAddr()%>:8080/vacavas/sys/rcerpaotrt/aggstats">Monthly Report</a></h3>
	
	</p>
	</div>
	
	
  <table>
    <tr>
    <td><p><h3 style="color: red;">
     <c:forEach var="operator" items="${listOperator}"	varStatus="loop">	
	<a 
	href="http://<%=request.getLocalAddr()%>:8080/vacavas/sys/rcerpaotrt/hourlyreport?opid=${operator.operatorId}">${operator.operatorName} ||</a>	
	</c:forEach>
	</h3>
	</p>
	
	</td>	
	</tr>
	</table>
	
	<table>
    <tr>
     <c:forEach var="product" items="${listProduct}"	varStatus="loop">	
	<td><h3 style="color: red;white-space:nowrap;"><a 
	href="http://<%=request.getLocalAddr()%>:8080/vacavas/sys/rcerpaotrt/hourlyreport?opid=${param.opid}&productid=${product.id}">${product.productName} |</a></h3></td>	
	</c:forEach>
	</tr>
	</table>
	
   <form:form modelAttribute="AggReport" id="reportform" name="reportform"
    action="${pageContext.request.contextPath}/sys/rcerpaotrt/hourlyreport">
    <div style=" border-radius: 5px 5px 5px 5px;">
   
    <table  border="0"  align="left" style="width:50%;height:100%;background-color:#ababab" cellpadding="2" cellspacing="1">
     <tr>
     
     <td>
         <p> Aggregator
         <form:select class="formselect" name="aggregatorId" id="aggregatorId" path="aggregatorId" > 
						<form:option value="0" label="Select " />
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
						<form:option value="0" label="Select Operator" />
						<c:forEach var="operator" items="${operatorList}"
								varStatus="oploop">
							<form:option value="${operator.operatorId}" 
							label="${operator.operatorName}"></form:option>
						</c:forEach>
				</form:select>
				</p>
           </td>  
           
           <td></td>        
           <td>
           <p>
           	Product
             <form:select  name="productid" id="opid" path="productId" > 
						<form:option value="0" label="Select Product" />
						<c:forEach var="product" items="${productList}"
								varStatus="productloop">
							<form:option value="${product.id}" 
							label="${product.productName}"></form:option>
						</c:forEach>
				</form:select>
				</p>
           </td> 
                    
          </tr>
          <tr>
      <td colspan="3" align="center">
    
   	 <form:button type="submit" class="btn  btn-lg btn-block">
   	  Find Report
    </form:button>

      </td>
       
      </tr>  
      
          </table>
          </div>
   </form:form>
   	<br/>	<br/>	<br/>	<br/>	<br/>
	<table border="1" bgcolor="#ababab" cellpadding="2" cellspacing="1"
		style="width: 100%" align="left">
			<tr><td colspan="3" align="left"></td></tr>
			<tr>
		<tr bgcolor="#f5efa1">
			<th style="text-align: center; font-weight:bold">Day</th>
			<th style="text-align: center">Activation</th>
			<th style="text-align: center">Activation Revenue</th>
			<th style="text-align: center">Renewal</th>
			<th style="text-align: center">Renewal Revenue</th>
			<th style="text-align: center">No Of Grace</th>
			<th style="text-align: center">Total Revenue</th>
			<th style="text-align: center">Deactivation</th>
			
		</tr>
		<tr>
			<td><span style="font-weight:bold">Today</span></td>
			<td><span style="font-weight:bold"> ${currentDateActivationCount}</span></td>
			<td>${currentDateActivationAmount}</td>
			<td>${currentDateRenewalCount}</td>
			<td>${currentDateRenewalAmount}</td>
			<td>${currentDateParkingCount}</td>
			<td><span style="font-weight:bold"> ${currentDateTotalRevenue}</span></td>
			<td>${currentDateDctCount}</td>
			
		</tr>
		<tr>
		<td><span style="font-weight:bold">Yesterday</span></td>
		<td><span style="font-weight:bold"> ${yesterdayDateActivationCount}</span></td>
		<td>${yesterdayDateActivationAmount}</td>
		<td>${yesterdayDateRenewalCount}</td>
		<td>${yesterdayDateRenewalAmount}</td>
		<td>${yesterdayDateParkingCount}</td>
		<td><span style="font-weight:bold"> ${yesterdayDateTotalRevenue}</span></td>
		<td>${yesterdayDateDctCount}</td>		
		</tr>
	</table>

	<br />

	<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
	<c:forEach var="adnetworkCompaignReportWrapper" items="${listadnetworkCompaignReportWrapper}"	varStatus="loop">
	<h1>
	<c:choose>
		<c:when	test="${adnetworkCompaignReportWrapper.getNoOfPrevDay()==0}">
		Today
		</c:when>
		<c:otherwise>Yesterday</c:otherwise>
		</c:choose>
	</h1>
	<br />


	<table border="1" bgcolor="#ababab" cellpadding="2" cellspacing="1"
		style="width: 100%; color: #000; font-weight: bold;">
		<tr bgcolor="#f5efa1">
			<th rowspan="2">Campaign Id/Hour</th>
			<th colspan="4">Total</th>
			<c:forEach var="hour" items="${adnetworkCompaignReportWrapper.getReportHour()}"
				>
				<th bgcolor="#dddddd" colspan="4">${hour}<sup>th</sup> Hour
				</th>
			</c:forEach>
		</tr>

		<tr bgcolor="#d1d1d1">
			<th>Act</th>
			<th>Grace</th>
			<th>Tot</th>
			<th>Rev</th>
			<c:forEach var="hour" items="${adnetworkCompaignReportWrapper.getReportHour()}"
			>
				<th>Act</th>
				<th>Grace</th>
				<th>Tot</th>
				<th>Rev</th>
			</c:forEach>
		</tr>


		<c:forEach var="adnetworkCampaignReport"
			items="${adnetworkCompaignReportWrapper.getMapAdnetworkCampaignReport()}" >

			<tr bgcolor="#f5efa1">
				<th>${adnetworkCampaignReport.value.getCampaignName()}</th>
				<th>${adnetworkCampaignReport.value.getTotalActivationCount()}</th>
				<th>${adnetworkCampaignReport.value.getTotalParkingCount()}</th>
				<th colspan="2">${adnetworkCampaignReport.value.getTotalActivationAmount()}</th>

				<c:forEach var="hour" items="${adnetworkCompaignReportWrapper.getReportHour()}"
					>
					<c:choose>
						<c:when	test="${adnetworkCampaignReport.value.getMapReport().get(hour)!=null}">
							<th>${adnetworkCampaignReport.value.getMapReport().get(hour).get(0).getConversionCount()}</th>
							<th>${adnetworkCampaignReport.value.getMapReport().get(hour).get(0).getParkingToActivationCount()}</th>
							<th colspan="2">${adnetworkCampaignReport.value.getMapReport().get(hour).get(0).getAmount()}</th>
						</c:when>
						<c:otherwise>
						<th>0</th>
							<th>0</th>
							<th colspan="2">0</th>
						</c:otherwise>
					</c:choose>

				</c:forEach>
			</tr>
		</c:forEach>

		<tr bgcolor="#f5efa1">
			<th>Total Renewal</th>

			<th colspan="2">${adnetworkCompaignReportWrapper.getTotalRenewalCount()}</th>
			<th colspan="2">${adnetworkCompaignReportWrapper.getTotalRenewalAmount()}</th>
			
			<c:forEach var="hour" items="${adnetworkCompaignReportWrapper.getReportHour()}"
					>					
			
			<c:choose>
					<c:when	test="${adnetworkCompaignReportWrapper.getMapRenewal().get(hour)!=null}">
			            <th colspan="2">${adnetworkCompaignReportWrapper.getMapRenewal().get(hour).getRenewalCount()}</th>
			            <th colspan="2">${adnetworkCompaignReportWrapper.getMapRenewal().get(hour).getRenewalAmount()}</th>    
			            </c:when>
			            <c:otherwise>
			            <th colspan="2">0</th>
			            <th colspan="2">0</th>  
			            </c:otherwise>			       
			</c:choose>
			      </c:forEach>

		</tr>

		<tr bgcolor="#f5efa1">
			<th>Total Deactivation</th>

			<th colspan="4">${adnetworkCompaignReportWrapper.getTotalDeactivationCount()}</th>

				<c:forEach var="hour" items="${adnetworkCompaignReportWrapper.getReportHour()}"
					>					
			
			<c:choose>
					<c:when	test="${adnetworkCompaignReportWrapper.getMapDeactivation().get(hour)!=null}">
			            <th colspan="4">${adnetworkCompaignReportWrapper.getMapDeactivation().get(hour).getDctCount()}</th>			             
			            </c:when> 
			            <c:otherwise>
			            <th colspan="4">0</th>
			           
			            </c:otherwise>
			        
			</c:choose>
			        
			      </c:forEach>
		</tr>
	</table>
</c:forEach>


</body>
</html>
