<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        
<%
Enumeration e=request.getHeaderNames();
while(e.hasMoreElements()){
    String headerName=(String)e.nextElement();
    String value=request.getHeader(headerName);
    out.println(headerName+" : "+value+"<br/>");
}
out.println("IP : "+request.getRemoteAddr());
%>


    </body>
</html>