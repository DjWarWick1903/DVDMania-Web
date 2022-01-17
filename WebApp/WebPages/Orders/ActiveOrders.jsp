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
				<a href="Customers.php">Customers</a>
				<a href="Stores.php">Stores</a>
			</c:if>
			
			<c:if test="${not empty account and account.priv ne 1 and account.priv ne 2}">
				<a href="Employees.php">Employees</a>
			</c:if>
			
			<a class="returnal" href="/DVDMania-Web/Login">Logout</a>
	    </div>
	    
	    <c:if test="${not empty account and account.priv ne 1}">
		    <div class="subnav">
		    	<a href="/DVDMania-Web/Orders/GetClients">New order</a>
		    	<a class="active" href="/DVDMania-Web/Orders/GetOrders">Return order</a>
		    </div>
	    </c:if>
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<table class="customTable">
			<tr>
				<th>Nume</th>
				<th>Prenume</th>
				<th>CNP</th>
				<th>ID stoc</th>
				<th>Data imprumutului</th>
			</tr>
			
			<c:forEach var="order" items="${orders}">
				<tr <c:if test="${empty order.returnDate}"> class="imprumutat" </c:if>>
					<td>${order.client.nume}</td>
					<td>${order.client.prenume}</td>
					<td>${order.client.cnp}</td>
					<td>${order.stock.idProduct}</td>
					<td>${order.borrowDate}</td>
					<form action="/DVDMania-Web/Orders/ReturnOrder" method="POST">
						<td><input type="submit" value="Returneaza"></td>
						<input type="hidden" name="idStock" value="${order.stock.idProduct}">
						<input type="hidden" name="idClient" value="${order.client.id}">
					</form>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>