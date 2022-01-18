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
			<c:if test="${not empty account and account.priv ne 1}">
				<a href="/DVDMania-Web/Products">Products</a>
				<a href="/DVDMania-Web/Customers">Customers</a>
				<a href="/DVDMania-Web/Stores">Stores</a>
			</c:if>
			
			<c:if test="${not empty account and account.priv ne 1 and account.priv ne 2}">
				<a href="/DVDMania-Web/Employees">Employees</a>
			</c:if>
			
			<a class="returnal" href="/DVDMania-Web/Login">Logout</a>
	    </div>
	    
	    <c:if test="${not empty account and account.priv ne 1}">
		    <div class="subnav">
		    	<a class="active" href="/DVDMania-Web/Orders/GetClients">New order</a>
		    	<a href="/DVDMania-Web/Orders/GetOrders">Return order</a>
		    </div>
	    </c:if>
	    
	    <hr><br>
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<table class="customTable">
			<tr>
				<th>ID</th>
				<th>Nume</th>
				<th>Prenume</th>
				<th>Adresa</th>
				<th>Oras</th>
				<th>Data nasterii</th>
				<th>CNP</th>
				<th>Telefon</th>
				<th>Email</th>
			</tr>
			
			<c:forEach var="client" items="${clients}">
				<tr>
					<td>${client.id}</td>
					<td>${client.nume}</td>
					<td>${client.prenume}</td>
					<td>${client.adresa}</td>
					<td>${client.oras}</td>
					<td>${client.datan}</td>
					<td>${client.cnp}</td>
					<td>${client.tel}</td>
					<td>${client.email}</td>
					<form action="/DVDMania-Web/Orders/NewOrder" method="POST">
						<td><input type="submit" name="newOrderButton" value="Comanda noua"></td>
						<input type="hidden" name="idClient" value="${client.id}">
					</form>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>