<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
	<%
		if(!session.isNew() || session.getAttribute("ADMIN") != null)
		{
			session.setAttribute("ADMIN", null);
			session.invalidate();
	%>
		<jsp:include page="index.html"></jsp:include>
	<%		
		}
		else {
	%>
		<p style=color:red> You need to login first !!! </p>
		<jsp:include page="adminlogin.html"></jsp:include>
	<%		
		}
	%>
</div>

</body>
</html>