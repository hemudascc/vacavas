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
<title>Exceed Limit</title>
</head>	
<body>
<div id="div1" 
style="position:fixed; float:left; height:100%; overflow:auto; width:100%; right:0; bottom:0; left:0;">
 <table style="width: 100%; align: center;" height="100%" border="0">
                       <tbody>
                         <tr>
                                 <td width="100%" align="center">

                               		<p class="priceText">
										You have reached exceed limit of retry of subscription. Please try after 24 hour.
                                	</p>
                                </td>
                        </tr>
                        </tbody>
							</table>	
                       </div>
</body>
</html>