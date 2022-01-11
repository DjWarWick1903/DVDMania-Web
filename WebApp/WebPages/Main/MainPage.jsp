<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

	<head>
		<title>DVDMania/MainPage</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/topnav.css">
	</head>
	
	<body>
		<div class="banner">
			<img class="logo" src="${pageContext.request.contextPath}/logo.png">
			<h1>DVDMania</h1>
		</div>
		<hr>
		<br>
		
		<div class="topnav">
			<a class="active" href="Main.php">Home</a>
			<a href="Orders.php">Orders</a>
			<a href="Products.php">Products</a>
			<a href="Customers.php">Customers</a>
			<a href="Employees.php">Employees</a>
			<a href="Stores.php">Stores</a>
			<a class="returnal" href="Backend/Logout.php">Logout</a>
	    </div>
		
		<c:if test="${errorMsg not empty}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<form action="/DVDMania-Web/VerificareLogin" method="POST">
			<label for="username">Username:</label> 
			<input type="text" name="username"> <br> <br> 
			
			<label for="password">Password:</label>
			<input type="password" name="password"> <br> <br> 
			
			<input type="submit" value="Sign In" />
		</form> <br>
		
		<form action="/DVDMania-Web/NewAccount" method="POST">
			<input type="submit" value="Sign Up" />
		</form> <br>
		
		<form action="/DVDMania-Web/VerificareLogin" method="POST">
			<input type="submit" value="Guest" />
		</form>
		
		<br>
	</body>
</html>