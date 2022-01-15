<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

	<head>
		<title>DVDMania/New account</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/newAccount.css">
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
		
		<div class="form">
			<form action="/DVDMania-Web/VerificareNewAccount" method="POST">
				<label for="username">Username:</label> 
				<input type="text" name="username">
				
				<label for="password">Password:</label>
				<input type="password" name="password">
				
				<label for="email">Email:</label>
				<input type="email" name="email">
				
				<label for="fname">First Name:</label>
				<input type="text" name="fname">
				
				<label for="lname">Last Name:</label>
				<input type="text" name="lname">
				
				<label for="address">Address:</label>
				<input type="text" name="address">
				
				<label for="city">City:</label>
				<input type="text" name="city">
				
				<label for="cnp">CNP:</label>
				<input type="text" name="cnp">
				
				<label for="phone">Phone:</label>
				<input type="text" name="phone">
				
				<label for="year">Year(yyyy):</label>
				<label for="month">Month(mm):</label>
				<label for="day">Day(dd):</label>
				<input type="number" min="1900" max="2022" name="year">
				<input type="number" min="1" max="12" name="month"> 
				<input type="number" min="1" max="31" name="day"> <br> <br>
				
				<input type="submit" value="Sign Up" />
			</form>
		</div>
		
		<form action="/DVDMania-Web/Login" method="GET">
			<input type="submit" value="Sign In" />
		</form>
		<br>
	</body>
</html>