
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


 
<table width="80%" border="1" align="center">
	
	<a href="currentstats" >Currentstats Report</a>
	<br><br>
	<a href="reload" >Reload Config(${lastreloadtime})</a>
	<br><br>
	<tr align="center">
		<th >Operator Name</th>
		<th>Adnetwork Name</th>
		<th>Block(%)</th>
	</tr>

	<c:forEach var="vwadnetworkOperatorConfig" items="${list}" varStatus="outerloop">
			<form action="updateadnopconfig">
			<tr bgcolor="" align="center">
					<td>
					<input type="hidden" name="adnopconfigid" value="${vwadnetworkOperatorConfig.adnetworkOperatorConfigId}"> 
					${vwadnetworkOperatorConfig.operatorName}</td>
					<td>${vwadnetworkOperatorConfig.adNetworkName}</td>					
				  <td>
				   <select name="skipno" >	
				   	   <c:forEach var = "i" begin = "0" end = "100" step="10">	
				   	   <c:if test="${vwadnetworkOperatorConfig.skipNumber==i}">	     
					  <option value="${i}" selected>${i}</option>
					  </c:if>
					  <c:if test="${vwadnetworkOperatorConfig.skipNumber!=i}">	     
					  <option value="${i}" >${i}</option>
					  </c:if>
					  </c:forEach>					 
					</select>
					</td>
					<td> 
					
				   <input type="submit" value="Update">
					</td>				
				</tr>
				</form>
			</c:forEach>
			
		
</table>
</body>
</html>
