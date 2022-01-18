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
				<a href="/DVDMania-Web/Stores">Stores</a>
			</c:if>
			
			<c:if test="${not empty account and account.priv ne 1 and account.priv ne 2}">
				<a class="active" href="/DVDMania-Web/Employees">Employees</a>
			</c:if>
			
			<a class="returnal" href="/DVDMania-Web/Login">Logout</a>
	    </div>
	    
	    <c:if test="${not empty account and account.priv ne 1}">
		    <div class="subnav">
		    	<a class="active" href="/DVDMania-Web/Employees">Edit employee</a>
		    	<a href="/DVDMania-Web/Employees/ToNewEmployee">New employee</a>
		    </div>
	    </c:if>
	    <hr><br>
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<c:if test="${not empty msg}">
			<p class="msg" style="color:green">${msg}</p>
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
				<th>Functie</th>
				<th>Salariu</th>
				<th>Adresa magazin</th>
				<th>Username</th>
				<th>Password</th>
			</tr>
			
			<c:forEach var="employee" items="${employees}">
				<tr>
					<c:set var="key" value="${employee.key}"/>
					<c:set var="value" value="${employee.value}"/>
					
					<td>${key.getId()}</td>
					<td>${key.getNume()}</td>
					<td>${key.getPrenume()}</td>
					<td>${key.getAdresa()}</td>
					<td>${key.getOras()}</td>
					<td>${key.getDatan()}</td>
					<td>${key.getCnp()}</td>
					<td>${key.getTel()}</td>
					<td>${key.getEmail()}</td>
					<td>${key.getFunctie()}</td>
					<td>${key.getSalariu()}</td>
					<td>${key.getStore().getOras()}</td>
					<td>${value.getUsername()}</td>
					<td>${value.getPassword()}</td>
					<form action="/DVDMania-Web/Employees/ToEditEmployee" method="POST">
						<input type="hidden" name="idEmp" value="${key.getId()}">
						<td><input type="submit" value="Edit"></td>
					</form>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>