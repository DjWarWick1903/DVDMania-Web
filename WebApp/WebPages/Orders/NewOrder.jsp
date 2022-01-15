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
			<a class="active" href="/DVDMania-Web/Orders">Orders</a>
			<a href="Products.php">Products</a>
			<a href="Customers.php">Customers</a>
			<a href="Employees.php">Employees</a>
			<a href="Stores.php">Stores</a>
			
			<a class="returnal" href="/DVDMania-Web/Login">Logout</a>
	    </div>
	    
	    <c:if test="${not empty account and account.priv ne 1}">
		    <div class="subnav">
		    	<a class="active" href="/DVDMania-Web/GetClients">New order</a>
		    	<a href="">Return order</a>
		    </div>
	    </c:if>
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<table class="customTable">
			<tr>
				<th>ID client</th>
				<th>CNP</th>
				<th>Numar telefon</th>
				<th>Categorie</th>
				<th>Id produs</th>
			</tr>
			
			<tr>
				<td>${client.id}</td>
				<td>${client.cnp}</td>
				<td>${client.tel}</td>
				<form action="/DVDMania-Web/Orders/SaveOrder" method="POST">
					<td>
						<select name="categorii">
							<option value="Filme" selected>Filme</option>
							<option value="Jocuri">Jocuri</option>
							<option value="Albume">Albume</option>
						</select>
					</td>
					<td><input type="number" name="idProd"></td>
					<input type="hidden" value="${client.id}" name="idClient">
					<td><input type="submit" name="newOrderButton" value="Inchiriaza"></td>
				</form>
			</tr>
		</table>
	</body>
</html>