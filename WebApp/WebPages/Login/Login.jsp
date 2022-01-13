<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

	<head>
		<title>DVDMania/Login</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
	</head>
	
	<body>
		<div class="banner">
			<img class="logo" src="${pageContext.request.contextPath}/logo.png">
			<h1>DVDMania</h1>
		</div>
		<hr>
		<br>
		
		<c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<c:if test="${not empty msg}">
			<p class="msg" style="color:red">${msg}</p>
		</c:if>
		
		<form action="/DVDMania-Web/VerificareLogin" method="POST">
			<label for="username">Username:</label> 
			<input type="text" name="username"> <br> <br> 
			
			<label for="password">Password:</label>
			<input type="password" name="password"> <br> <br> 
			
			<input type="submit" value="Sign In" />
		</form> <br>
		
		<form action="/DVDMania-Web/NewAccount" method="GET">
			<input type="submit" value="Sign Up" />
		</form> <br>
		
		<form action="/DVDMania-Web/VerificareLogin" method="POST">
			<input type="submit" value="Guest" name="Guest"/>
		</form>
		
		<br>
	</body>
</html>