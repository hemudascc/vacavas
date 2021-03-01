
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
  
  
  
 

</head>
<body>


 <a href="adnoconfig">Adnetwork Callback Config</a>
 <br><br><br><br>
<table width="80%" border="1" align="center">


	<tr>
		<th>Operator Name</th>
		<th>Report Date</th>
		<th>Adnetwork Name</th>
		<th>Total hits</th>
		<th>Duplicate count</th>
		<th>Reverse Click Count</th>
		<th>Block Click Count</th>
		<th>Already Subscribed Count</th>
		<th>Total redirected to cg</th>
		<th>Subscription</th>
		<th>Zero Price ACT-REN</th>
		<th>Zero Price ACT-REN Amount</th>
		
		<th>Sent count</th>
		<th>Manual Sent count</th>
		<th>Not sent</th>
		<th>Grace count</th>
         <th>Grace sent count</th>
         <th>Total Sent count</th>
		<th>CR</th>
		<th>Revenue</th>
		<th>Spent(USD)</th>
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
					<td>${liveReport.clickCount}</td>
					<td>${liveReport.duplicateClickCount}</td>
					<td>${liveReport.reverseClickCount}</td>
					<td>${liveReport.blockClickCount}</td>
					<td>${liveReport.alreadySubscribedCount}</td>
					<td>${liveReport.validClickCount}</td>
					<td>${liveReport.conversionCount}</td>
					
					<td>${liveReport.renewalCountOfZeroPriceActivationAfter1Days}</td>
					<td>${liveReport.renewalAmountOfZeroPriceActivationAfter1Days}</td>
					
					<td>${liveReport.sendConversionCount}</td>
						<td>${liveReport.sendManualConversionCount}</td>
					

					<td>${liveReport.conversionCount-liveReport.sendConversionCount}</td>
                  <td>${liveReport.graceConversionCount}</td>
                  <td>${liveReport.graceSendConversionCount}</td>
                <td>${liveReport.graceSendConversionCount+liveReport.sendConversionCount}</td>
                <td><fmt:formatNumber pattern="##.##" value="${(liveReport.graceSendConversionCount+liveReport.sendConversionCount*100)/liveReport.clickCount}"/></td>
					
					<td>${liveReport.amount}</td>
					<td><fmt:formatNumber pattern="##.##" value="${liveReport.spend}"/></td>
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
