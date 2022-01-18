<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<title>DVDMania/Orders</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/topnav.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/subnav.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/customTable.css">
	</head>
	<body>
		<div class="banner">
			<img class="logo" src="${pageContext.request.contextPath}/logo.png">
			<h1>DVDMania</h1>
		</div>
		<hr>
		
		<div class="topnav">
			<a href="/DVDMania-Web/MainPage">Home</a>
			<a href="/DVDMania-Web/Orders">Orders</a>
			<c:if test="${not empty account and account.priv ne 1}">
				<a href="/DVDMania-Web/Products">Products</a>
				<a class="active" href="/DVDMania-Web/Customers">Customers</a>
				<a href="/DVDMania-Web/Stores">Stores</a>
			</c:if>
			
			<c:if test="${not empty account and account.priv ne 1 and account.priv ne 2}">
				<a href="/DVDMania-Web/Employees">Employees</a>
			</c:if>
			
			<a class="returnal" href="/DVDMania-Web/Login">Logout</a>
	    </div>
	    
	    <c:if test="${not empty account and account.priv ne 1}">
		    <div class="subnav">
		    	<a href="/DVDMania-Web/Customers">Edit customers</a>
		    	<a class="active" href="/DVDMania-Web/Customers/ToNewCustomer">New customer</a>
		    </div>
	    </c:if>
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<table class="customTable">
			<tr>
				<th>Nume</th>
				<th>Prenume</th>
				<th>Adresa</th>
				<th>Oras</th>
				<th>Data nasterii (dd/MM/yyyy)</th>
				<th>CNP</th>
				<th>Telefon</th>
				<th>Email</th>
				<th>Username</th>
				<th>Password</th>
			</tr>
			
			<form action="/DVDMania-Web/Customers/NewCustomer" method="POST">
				<td><input type="text" name="nume"></td>
				<td><input type="text" name="prenume"></td>
				<td><input type="text" name="adresa"></td>
				<td><input type="text" name="oras"></td>
				<td><input type="text" name="datan"></td>
				<td><input type="text" name="cnp"></td>
				<td><input type="text" name="tel"></td>
				<td><input type="text" name="email"></td>
				<td><input type="text" name="username"></td>
				<td><input type="text" name="password"></td>
				<td><input type="submit" value="Insert"></td>
			</form>
		</table>
	</body>
</html>