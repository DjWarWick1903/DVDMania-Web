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
		    	<a class="active" href="/DVDMania-Web/Customers">Edit customers</a>
		    	<a href="/DVDMania-Web/Customers/ToNewCustomer">New customer</a>
		    </div>
	    </c:if>
	    <hr><br>
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<form action="/DVDMania-Web/Customers/EditCustomer" method="POST">
			<table class="customTable">
				<input type="hidden" value="${client.getId()}" name="idClient">
				<tr>
					<td>Nume</td>
					<td><input type="text" value="${client.getNume()}" name="nume"></td>
				</tr>
				<tr>
					<td>Prenume</td>
					<td><input type="text" value="${client.getPrenume()}" name="prenume"></td>
				</tr>
				<tr>
					<td>Adresa</td>
					<td><input type="text" value="${client.getAdresa()}" name="adresa"></td>
				</tr>
				<tr>
					<td>Oras</td>
					<td><input type="text" value="${client.getOras()}" name="oras"></td>
				</tr>
				<tr>
					<td>Data nasterii(yyyy-mm-dd)</td>
					<td><input type="text" value="${client.getDatan()}" name="datan"></td>
				</tr>
				<tr>
					<td>CNP</td>
					<td><input type="number" value="${client.getCnp()}" name="cnp"></td>
				</tr>
				<tr>
					<td>Telefon</td>
					<td><input type="number" value="${client.getTel()}" name="tel"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" value="${client.getEmail()}" name="email"></td>
				</tr>
				<tr>
					<td>Username</td>
					<td><input type="text" value="${customerAcc.getUsername()}" name="username"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" value="${customerAcc.getPassword()}" name="password"></td>
				</tr>
			</table>
			<input type="submit" value="Edit">
		</form>
	</body>
</html>