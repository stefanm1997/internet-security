<%@page import="org.unibl.etf.dao.DAOUser"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.unibl.etf.dto.DTOItem"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="script.js"></script>
<title>Aplikacija za upravljanje artiklima</title>

</head>
<%
	String username = (String) session.getAttribute("edu.yale.its.tp.cas.client.filter.user");
	String role = DAOUser.getRoleFromUser(username);
	if ("admin".equals(role) || "admin-artikl".equals(role)) {

	} else {
		
		response.sendRedirect("errorPage.html");

	}
%>
<body bgcolor="yellow" onload="javascript:onLoad()">

	<h1 align="center">Dobrodosli na aplikaciju za upravljanje
		artiklima</h1>

	<form method="POST" action="ServletAdd">
		<p>
			<b>Dodavanje artikla: </b>
		</p>
		<p>
			Unesi sifru artikla:<br> <input type="text" name="code"></input>
		</p>
		<p>
			Unesi naziv artikla: <br>
			<input type="text" name="name"></input>
		</p>
		<p>
			Unesi proizvodjaca artikla: <br>
			<input type="text" name="producer"></input>
		</p>
		<p>
			Unesi cijenu artikla: <br>
			<input type="text" name="price"></input>
		</p>

		<input type="submit" value="Dodaj">
	</form>
	<br>
	<p>
		<b>Pregled i brisanje artikla: </b>
	</p>
	<div id="itemTable"></div>
</body>
</html>