<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kill session</title>
</head>
<body>
	<%
		session.removeAttribute("username");
		session.removeAttribute("password");
		session.removeAttribute("code");
		session.removeAttribute("name");
		session.invalidate();

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		System.out.println("Odradilo je sve planirano");
		response.sendRedirect("https://localhost:8443/ItemApp/killSession.jsp");
		//if(session.getAttribute("token")==null) {
		//response.sendRedirect("https://localhost:8443/cas/login");
		//}
	%>
</body>
</html>