<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

	<head>
		<title>DVDMania/New account</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/customTable.css">
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
		
		<form action="/DVDMania-Web/VerificareNewAccount" method="POST">
			<table class="customTable">
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="email" name="email"></td>
				</tr>
				<tr>
					<td>First Name:</td>
					<td><input type="text" name="fname"></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><input type="text" name="lname"></td>
				</tr>
				<tr>
					<td>Address:</td>
					<td><input type="text" name="address"></td>
				</tr>
				<tr>
					<td>City:</td>
					<td><input type="text" name="city"></td>
				</tr>
				<tr>
					<td>CNP:</td>
					<td><input type="text" name="cnp"></td>
				</tr>
				<tr>
					<td>Phone:</td>
					<td><input type="text" name="phone"></td>
				</tr>
				<tr>
					<td>Year(yyyy):</td>
					<td><input type="number" min="1900" max="2022" name="year"></td>
				</tr>
				<tr>
					<td>Month(mm):</td>
					<td><input type="number" min="1" max="12" name="month"> </td>
				</tr>
				<tr>
					<td>Day(dd):</td>
					<td><input type="number" min="1" max="31" name="day"></td>
				</tr>
			</table>
					
			<input type="submit" value="Sign Up" />
		</form>
		
		<form action="/DVDMania-Web/Login" method="GET">
			<input type="submit" value="Sign In" />
		</form>
	</body>
</html>