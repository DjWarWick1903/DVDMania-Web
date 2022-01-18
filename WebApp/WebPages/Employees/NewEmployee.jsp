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
		    	<a href="/DVDMania-Web/Employees">Edit employee</a>
		    	<a class="active" href="/DVDMania-Web/Employees/ToNewEmployee">New employee</a>
		    </div>
	    </c:if>
	    
	    <hr><br>
	    
	    <c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<c:if test="${not empty msg}">
			<p class="msg" style="color:green">${msg}</p>
		</c:if>
		
		<form action="/DVDMania-Web/Employees/NewEmployee" method="POST">
			<table class="customTable">
				<tr>
					<td>Nume</td>
					<td><input type="text" name="nume"></td>
				</tr>
				<tr>
					<td>Prenume</td>
					<td><input type="text" name="prenume"></td>
				</tr>
				<tr>
					<td>Adresa</td>
					<td><input type="text" name="adresa"></td>
				</tr>
				<tr>
					<td>Oras</td>
					<td><input type="text" name="oras"></td>
				</tr>
				<tr>
					<td>Data nasterii(yyyy-MM-dd)</td>
					<td><input type="text" name="datan"></td>
				</tr>
				<tr>
					<td>CNP</td>
					<td><input type="number" name="cnp"></td>
				</tr>
				<tr>
					<td>Telefon</td>
					<td><input type="number" name="telefon"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" name="email"></td>
				</tr>
				<tr>
					<td>Functie</td>
					<td><input type="text" name="functie"></td>
				</tr>
				<tr>
					<td>Salariu</td>
					<td><input type="number" name="salariu"></td>
				</tr>
				<tr>
					<td>Adresa magazin</td>
					<td>
						<select name="orasMag">
							<c:forEach var="store" items="${storeList}">
								<option value="${store}">
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" name="password"></td>
				</tr>
			</table>
			<input type="submit" value="Save">
		</form>
	</body>
</html>