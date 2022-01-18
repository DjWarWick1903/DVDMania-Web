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
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<c:if test="${not empty msg}">
			<p class="msg" style="color:green">${msg}</p>
		</c:if>
		
		<form action="/DVDMania-Web/Employees/EditEmployee" method="POST">
			<table class="customTable">
				<input type="hidden" name="idEmp" value="${employee.getId()}">
				<tr>
					<td>Nume</td>
					<td><input type="text" name="nume" value="${employee.getNume()}"></td>
				</tr>
				<tr>
					<td>Prenume</td>
					<td><input type="text" name="prenume" value="${employee.getPrenume()}"></td>
				</tr>
				<tr>
					<td>Adresa</td>
					<td><input type="text" name="adresa" value="${employee.getAdresa()}"></td>
				</tr>
				<tr>
					<td>Oras</td>
					<td><input type="text" name="oras" value="${employee.getOras()}"></td>
				</tr>
				<tr>
					<td>Data nasterii(yyyy-MM-dd)</td>
					<td><input type="text" name="datan" value="${employee.getDatan()}"></td>
				</tr>
				<tr>
					<td>CNP</td>
					<td><input type="number" name="cnp" value="${employee.getCnp()}"></td>
				</tr>
				<tr>
					<td>Telefon</td>
					<td><input type="number" name="telefon" value="${employee.getTel()}"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" name="email" value="${employee.getEmail()}"></td>
				</tr>
				<tr>
					<td>Functie</td>
					<td><input type="text" name="functie" value="${employee.getFunctie()}"></td>
				</tr>
				<tr>
					<td>Salariu</td>
					<td><input type="number" name="salariu" value="${employee.getSalariu()}"></td>
				</tr>
				<tr>
					<td>Adresa magazin</td>
					<td>
						<select name="orasMag">
							<c:forEach var="store" items="${storeList}">
								<option value="${store}" <c:if test="${store eq employee.getStore().getOras()}"> selected </c:if>>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" value="${empAcc.getUsername()}"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" name="password" value="${empAcc.getPassword()}"></td>
				</tr>
			</table>
			<input type="submit" value="Edit">
		</form>
	</body>
</html>