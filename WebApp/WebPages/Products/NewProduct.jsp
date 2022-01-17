<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

	<head>
		<title>DVDMania/Products</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/topnav.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/customTable.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/subnav.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/categories.css">
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
				<a class="active" href="/DVDMania-Web/Products">Products</a>
				<a href="Customers.php">Customers</a>
				<a href="Stores.php">Stores</a>
			</c:if>
			
			<c:if test="${not empty account and account.priv ne 1 and account.priv ne 2}">
				<a href="Employees.php">Employees</a>
			</c:if>
			
			<a class="returnal" href="/DVDMania-Web/Login"><c:out value="${not empty account ? 'Logout' : 'Login'}"/></a>
	    </div>
	    
		<div class="subnav">
	    	<a href="/DVDMania-Web/Products">Edit products</a>
	    	<a class="active" href="/DVDMania-Web/Products/ToNewProduct">New product</a>
		</div>
		<hr>
		
		<div class="categories">
			<form action="/DVDMania-Web/Products" method="GET">
				<input <c:if test="${sessionScope.productSelection eq 'Filme'}"> <c:out value="class=selected"/> </c:if> type="submit" value="Filme" name="productSelection">
				<input <c:if test="${sessionScope.productSelection eq 'Jocuri'}"> <c:out value="class=selected"/> </c:if> type="submit" value="Jocuri" name="productSelection">
				<input <c:if test="${sessionScope.productSelection eq 'Albume'}"> <c:out value="class=selected"/> </c:if> type="submit" value="Albume" name="productSelection">
				<input <c:if test="${sessionScope.productSelection eq 'Melodii'}"> <c:out value="class=selected"/> </c:if> type="submit" value="Melodii" name="productSelection">
			</form>
		</div>
		<br>
		
		<c:if test="${not empty errorMsg}">
			<p class="error" style="color:red">${errorMsg}</p>
		</c:if>
		
		<c:if test="${not empty msg}">
			<p class="msg" style="color:green">${msg}</p>
		</c:if>
		
		<table class="customTable">
			<c:choose>
				<c:when test="${sessionScope.productSelection eq 'Filme'}">
					<tr>
						<th>Titlu</th>
						<th>Actor principal</th>
						<th>Director</th>
						<th>Durata</th>
						<th>Gen</th>
						<th>An</th>
						<th>Audienta</th>
						<th>Cantitate</th>
						<th>Pret</th>
					</tr>

					<form action="/DVDMania-Web/Products/NewProduct" method="POST">
						<tr>
							<td><input type="text" name="titlu"></td>
							<td><input type="text" name="actPr"></td>
							<td><input type="text" name="director"></td>
							<td><input type="number" name="durata"></td>
							<td><input type="text" name="gen"></td>
							<td><input type="number" name="an"></td>
							<td><input type="number" name="audienta"></td>
							<td><input type="number" name="cantitate"></td>
							<td><input type="number" name="pret"></td>
							<td><input type="submit" value="Insert"></td>
						</tr>
					</form>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Jocuri'}">
					<tr>
						<th>Titlu</th>
						<th>Platforma</th>
						<th>Developer</th>
						<th>Publisher</th>
						<th>Gen</th>
						<th>An</th>
						<th>Audienta</th>
						<th>Cantitate</th>
						<th>Pret</th>
					</tr>
					
					<form action="/DVDMania-Web/Products/NewProduct" method="POST">
						<tr>
							<td><input type="text" name="titlu"></td>
							<td><input type="text" name="platforma"></td>
							<td><input type="text" name="developer"></td>
							<td><input type="text" name="publisher"></td>
							<td><input type="text" name="gen"></td>
							<td><input type="number" name="an"></td>
							<td><input type="number" name="audienta"></td>
							<td><input type="number" name="cantitate"></td>
							<td><input type="number" name="pret"></td>
							<td><input type="submit" value="Insert"></td>
						</tr>
					</form>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Albume'}">
					<tr>
						<th>Trupa</th>
						<th>Titlu</th>
						<th>Casa discuri</th>
						<th>Nr. Melodii</th>
						<th>Gen</th>
						<th>An</th>
						<th>Cantitate</th>
						<th>Pret</th>
					</tr>
					
					<form action="/DVDMania-Web/Products/NewProduct" method="POST">
						<tr>
							<td><input type="text" name="trupa"></td>
							<td><input type="text" name="titlu"></td>
							<td><input type="text" name="casaDisc"></td>
							<td><input type="number" name="nrMel"></td>
							<td><input type="text" name="gen"></td>
							<td><input type="number" name="an"></td>
							<td><input type="number" name="cantitate"></td>
							<td><input type="number" name="pret"></td>
							<td><input type="submit" value="Insert"></td>
						</tr>
					</form>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Melodii'}">
					<tr>
						<th>Titlu album</th>
						<th>Nume melodie</th>
						<th>Durata (secunde)</th>
					</tr>
					
					<c:forEach var="album" items="${albumList}">
						<form action="/DVDMania-Web/Products/NewProduct" method="POST">
							<tr>
								<td>
									<select name="titluri">
										<option value="${album}">${album}</option>
									</select>
								</td>
								<td><input type="text" name="nume"></td>
								<td><input type="number" name="durata"></td>
								<td><input type="submit" value="Insert"></td>
							</tr>
						</form>
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
		
		<br>
	</body>
</html>