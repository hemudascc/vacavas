<%@page pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link rel="stylesheet" href="/API/css/style.css" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes" />
        <title>SKWORLD</title>
<% 

%>
    </head>
    <body style="height:100%" >
        <div id="div1" 
        style="position:fixed; float:left; height:100%; overflow:auto; width:100%; right:0; bottom:0; left:0;">         
            <form id="frm1" name="frm1" onsubmit="return true;" 
             action="${moUrl}" method="get">
                <table style="width: 100%; align: center;" height="100%" border="0">
                    <tr>                        
                        <td style="font-weight: bold;" align="center">
                            <h3 style="background:#FBDFF7;margin-top: 10px;">SKWORLD
                            <div style="float: right">
                            <a href="${thConfig.portalUrl}"><img src="../../../images/lp/th/close.png" /></a>
                            </div>
                            <div style="clear: both"></div></h3>
                        </td>
                    </tr>                    
                    <tr>
                        <td width="100%" align="center"><b><span class="tabback2">
                              ขอบคุณสำหรับการแสดงความสนใจใน SKWORLD-${thConfig.serviceName} ค่าบริการ ${thConfig.price} บาท/SMS คุณจะได้รับ 1SMS/วัน</span></b></td>
                    </tr>
                </table>
                <table align="center">
                    <tr>
                        <td  width="100%" align="center">
                            <div style="">
                                <img  style="width: 100%"  src="${lpImage}"  />
                            </div>
                        </td>
                    </tr>
                    		    
                    <tr>
                        <td width="100%" align="center"><br /><br />
                            <input type="hidden" name="shortcode" value="${thConfig.shortcode}"/>                
                            <input type="hidden" name="keyword" value="${thConfig.keyword}"/> 
                            <input type="hidden" name="refid" value="${refId}"/>  
                            <c:if test="${thConfig!=null}">             
                            <input type="hidden" name="telcoId" value="${thConfig.telcoId}"/> 
                            </c:if>
                          
                          	<c:if test="${thConfig==null}"> 
                             <select name="telcoId" required style="font-weight: bolder; padding: 5px;">
                                <option value="">เลือกเครือข่าย</option>
                                <option value="3">AIS</option>
                              </select>           
                           </c:if>            
                        </td>
                    </tr>
                    <tr>
                      <td  width="100%" valign="middle" align="center">
                      <input type="submit" id="frm1_WebManager" value="สมัครบริการ" style="height:40px; width:150px;  background:#C5FA44; border-radius:10px; font-weight:bold; font-size:18px;"/>
                            <!--<input type="submit" id="frm1_WebManagerFailure" name="action:WebManagerFailure" value="CANCEL" style="height:40px; width:100px;  background:#FF0000"/>-->
                        </td>
                    </tr>
                    <tr width="100%" align="center"><td>บริการนี้สำหรับอายุ18 ปีขึ้นไปเท่านั้น</td></tr>
                    <tr>
                        <td>
                            <BR>
                            
                            <BR>
                        </td>
                    </tr>
                    <tr>
                        <td width="100%" align="center"><b>
                                <span class="tabback2">หรือ สมัครโดยพิมพ์ ${thConfig.keyword} ส่งมาที่ ${thConfig.shortcode}<br />
                                    สอบถามโทร 02-1158814 (จันทร์-ศุกร์ เวลา 09:00 น. - 18:00 น.)<br />
                                ยกเลิกพิมพ์ STOP ${shortcode.keyword} ส่งมาที่ ${thConfig.shortcode}
                                </span></b></td>
                    </tr>
                </table>
                </form>
            <b><span class="tabback2"></span></b>    
        </div>
    </body>
</html>
