<%@page import="org.unibl.etf.dao.DAOUser"%>
<%@page import="java.util.List"%>
<%@page import="org.unibl.etf.dto.DTOUser"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="script.js"></script>
<title>Aplikacija za upravljanje nalozima</title>
</head>
<%
	String username = (String) session.getAttribute("edu.yale.its.tp.cas.client.filter.user");
	String role = DAOUser.getRoleFromUser(username);
	if ("admin".equals(role)) {

	} else {

		response.sendRedirect("errorPage.html");

	}
%>
<body bgcolor="yellow" onload="javascript:onLoad()">
	<h1 align="center">Dobrodosli na aplikaciju za upravljanje
		nalozima</h1>

	<form method="POST" action="ServletAdd">
		<p>
			<b>Dodavanje korisnika: </b>
		</p>
		<p>
			Unesi korisnicko ime:<br> <input type="text" name="username"
				id="username"></input>
		</p>
		<p>
			Unesi lozinku: <br>
			<input type="password" name="password"></input>
		</p>
		<p>Izaberi ulogu korisnika:</p>
		<input type="radio" name="role" value="admin"> Admin<br>
		<input type="radio" name="role" value="admin-artikl">
		Admin-artikl<br> <input type="radio" name="role" value="kupac">
		Kupac <br>
		<br> <input type="submit" value="Dodaj">
	</form>
	<br>
	<p>
		<b>Pregled i brisanje korisnika: </b>
	</p>
	<div id="usersList"></div>
</body>
</html>