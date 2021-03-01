<%
if(request.getServerName().contains("life.limemoments.com")){
	//response.sendRedirect("http://life.limemoments.com/vacavas/sys/cmp?adid=1&cmpid=3&token=ccc");
	request.getRequestDispatcher("sys/cmp?adid=1&cmpid=3&token=ccc").forward(request,response);
}
%>
