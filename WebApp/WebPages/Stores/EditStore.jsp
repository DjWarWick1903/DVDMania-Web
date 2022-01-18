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
				<a href="/DVDMania-Web/Customers">Customers</a>
				<a class="active" href="/DVDMania-Web/Stores">Stores</a>
			</c:if>
			
			<c:if test="${not empty account and account.priv ne 1 and account.priv ne 2}">
				<a href="Employees.php">Employees</a>
			</c:if>
			
			<a class="returnal" href="/DVDMania-Web/Login">Logout</a>
	    </div>
	    
	    <c:if test="${not empty account and account.priv ne 1}">
		    <div class="subnav">
		    	<a class="active" href="/DVDMania-Web/Stores">Edit stores</a>
		    	<a href="/DVDMania-Web/Stores/ToNewStore">New store</a>
		    </div>
	    </c:if>
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<c:if test="${not empty msg}">
			<p class="msg" style="color:green">${msg}</p>
		</c:if>
		
		<table class="customTable">
			<tr>
				<th>ID</th>
				<th>Adresa</th>
				<th>Oras</th>
				<th>Telefon</th>
			</tr>
			
			<tr>
				<form action="/DVDMania-Web/Stores/EditStore" method="POST">
					<td><input type="hidden" value="${store.getId()}" name="idStore"></td>
					<td><input type="text" value="${store.getAdresa()}" name="adresa"></td>
					<td><input type="text" value="${store.getOras()}" name="oras"></td>
					<td><input type="number" value="${store.getTelefon()}" name="telefon"></td>
					<td><input type="submit" value="Save"></td>
				</form>
			</tr>
		</table>
	</body>
</html>