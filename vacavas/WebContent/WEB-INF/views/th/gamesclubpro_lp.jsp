<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width initial-scale=1">
<title>Games club</title>
</head>
<style>
body{
	width:100%;
	height:auto;
	margin:auto;
	background:#ccc;
	margin-top: 20px;
}
#main{
	width:90%;
	height:auto;
	margin:auto;
	background:#f3f3f3;
}
#bannerbox{
	width:100%;
	margin:auto;
	background:#5f5e5e;
	height:auto;
	box-sizing:border-box;
}
#banner{
	width: 100%;
	height: auto;
	margin: auto;
	text-align: center;
	background: #fff;
}
#bannerbox img{
	width:100%;
	vertical-align: middle;
}
#close{
	position: absolute;
	z-index: 1;
	top:0;
	right:3%;
}
p{
	font-family: Arial, Helvetica, sans-serif;
	font-size:14px;
	color:#000;
	margin: 5px;
	text-align:center;
	line-height:1.5em;
}
h1{
	font-size:20px;
	font-weight:bold;
	text-align:center;
	color:#333;
	font-family:Arial, Helvetica, sans-serif;
	margin: 10px 0 10px 0;
}
h2{
	font-size:16px;
	font-weight:bold;
	text-align:center;
	color:#333;
	font-family:Arial, Helvetica, sans-serif;
	margin: 9px 0 10px 0;
	text-decoration:underline;
}
h3{
	font-size:12px;
	font-weight:bold;
	text-align:left;
	color:#333;
	font-family:Arial, Helvetica, sans-serif;
	margin: 15px 0 10px 0;
	text-decoration:underline;
}
form{
	width:90%;
	height:auto;
	margin:auto;
	text-align:center;
}
.formStyle{
	width:200px;
	height:30px;
	border:1px solid #CCC;
	border-radius:2px;
	padding-left:5px;
	font-size:14px;
}
.formButton {
   background-color:#1146c5;
	display: inline-block;
	color: #fff;
	font-size: 18px;
	font-weight: 500;
	margin-top: 10px;
	width: 125px;
	height: 30px;
	padding-top: 2px;
	border-radius: 2px;
	text-decoration: none;
}
.formButton:hover {
	color:#ffffff;
	text-decoration:underline;
}
.formButton:active {
	position:relative;
	top:3px;
}
.warning{
	font-size:12px;
	text-align:center;
	color:red;
	font-family:Arial, Helvetica, sans-serif;
	margin: 15px 0 10px 0;
}
.loginbar{
	width:100%;
	margin:auto;
	text-align:center;
}
.loginbttn {
    background-color:#d41d95;
    display: inline-block;
    color: #fff;
    font-size: 18px;
    font-weight: 500;
    margin-top: 10px;
    width: 75px;
    height: 25px;
    padding-top: 5px;
    border-radius: 2px;
    text-decoration: none;
}
.loginbttn:hover {
	color:#ffffff;
	text-decoration:underline;
}
.loginbttn:active {
	position:relative;
	top:3px;
}
.warning{
	font-size:12px;
	text-align:center;
	color:red;
	font-family:Arial, Helvetica, sans-serif;
	margin: 15px 0 10px 0;
}
.content{
	width:80%;
	height:auto;
	margin:auto;
	margin-bottom:20px;
	padding-bottom:10px;
}
.smalltxt{
	font-size:12px;
	font-weight:bold;
	text-align:center;
	color:#333;
	font-family:Arial, Helvetica, sans-serif;
	margin: 10px 0 10px 0;
}
.select{
	width: 60%;
    height: 30px;
    border-radius: 5px;
    border: 1px solid #a2a2a2;
}
@media only screen and (max-width: 800px) {
#close{
	position: absolute;
	z-index: 1;
	top:0;
	right:0;
}
}
</style>
<script>
function xyz(){
	window.location="https://www.google.co.th/";
}
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


<body>
  <form id="frm1" name="frm1" onsubmit="return true;" action="${pageContext.request.contextPath}/cnt/th/tocg" method="get">
<div id="main">
	<div id="bannerbox">
      <a href="#" onclick="xyz();"><span id="close"><img src="${pageContext.request.contextPath}/resources/images/th/close.png"></span></a>
      <img src="${pageContext.request.contextPath}/resources/images/th/gamesclub.png"/>
     </div>
     
    <div class="content">
        	
        	
            	<input type="hidden" name="refid" value="${refid}"/>
            	<input type="hidden" name="configid" value="${thConfig.id}"/> 
               
               
         <div class="loginbar">
           <p>บริการ Games club</p>
           
         	<input type="submit" id="frm1_WebManager" value="สมัครบริการ" 
         	style="height:40px; width:150px;  background:#C5FA44; border-radius:10px; font-weight:bold; font-size:18px;"/>
             <p>บริการ Game HTML</p>
             <p>โดยท่านจะได้รับ SMS วันละ 2 ข้อความ</p>            
             <p>ยกเลิกพิมพ์ STOP GC ส่งมาที่ 4541932</p>
             <p>ค่าบริการ 5 บาท (ไม่รวมภาษีและค่าเชื่อมต่ออินเตอร์เน็ต)</p>
             <p> สอบถาม : 02-1158814, 9.00 – 18.00 (จันทร์ – ศุกร์)</p>
            
	  </div>
     </div>
</form>
</body>
</html>
