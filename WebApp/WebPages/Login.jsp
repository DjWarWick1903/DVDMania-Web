<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

	<head>
		<title>DVDMania</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
	</head>
	
	<body>
		<div class="banner">
			<img class="logo" src="${pageContext.request.contextPath}/logo.png">
			<h1>DVDMania</h1>
		</div>
		<hr>
		<br>
		
	
		<%
		session.invalidate();
		%>
		<form action="MainPage.jsp" method="POST">
			<label for="username">Utilizator:</label> <input type="text"
				name="username"> <br> <br> <label for="parola">Parola:</label>
			<input type="password" name="parola"> <br> <br> <input
				type="submit" value="Login" />
		</form>
	
		<br>
	</body>
</html>