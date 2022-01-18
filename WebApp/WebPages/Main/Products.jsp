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
				<a href="/DVDMania-Web/Customers">Customers</a>
				<a href="/DVDMania-Web/Stores">Stores</a>
			</c:if>
			
			<c:if test="${not empty account and account.priv ne 1 and account.priv ne 2}">
				<a href="/DVDMania-Web/Employees">Employees</a>
			</c:if>
			
			<a class="returnal" href="/DVDMania-Web/Login"><c:out value="${not empty account ? 'Logout' : 'Login'}"/></a>
	    </div>
	    
		<div class="subnav">
			<a class="active" href="/DVDMania-Web/Products">Edit products</a>
	    	<a href="/DVDMania-Web/Products/ToNewProduct">New product</a>
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
						<th>ID</th>
						<th>Titlu</th>
						<th>Actor principal</th>
						<th>Director</th>
						<th>Durata</th>
						<th>Gen</th>
						<th>An</th>
						<th>Audienta</th>
					</tr>
					
					<c:forEach var="movie" items="${products}">
						<c:set var="idMovie" value="${movie.getIdMovie()}"/>
						<tr>
							<td>${idMovie}</td>
							<td>${movie.getTitle()}</td>
							<td>${movie.getMainActor()}</td>
							<td>${movie.getDirector()}</td>
							<td>${movie.getDuration()}</td>
							<td>${movie.getGenre()}</td>
							<td>${movie.getYear()}</td>
							<td>${movie.getAudience()}</td>
							<form action="/DVDMania-Web/Products/ToEditProduct" method="POST">
								<td><input type="submit" name="EditButton" value="Edit"></td>
								<input type="hidden" name="idMovie" value="${idMovie}">
							</form>
						</tr>
					</c:forEach>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Jocuri'}">
					<tr>
						<th>ID</th>
						<th>Titlu</th>
						<th>Platforma</th>
						<th>Developer</th>
						<th>Publisher</th>
						<th>Gen</th>
						<th>An</th>
						<th>Audienta</th>
					</tr>
					
					<c:forEach var="game" items="${products}">
						<c:set var="idGame" value="${game.getIdGame()}"/>
						<tr>
							<td>${idGame}</td>
							<td>${game.getTitle()}</td>
							<td>${game.getPlatform()}</td>
							<td>${game.getDeveloper()}</td>
							<td>${game.getPublisher()}</td>
							<td>${game.getGenre()}</td>
							<td>${game.getYear()}</td>
							<td>${game.getAudience()}</td>
							<form action="/DVDMania-Web/Products/ToEditProduct" method="POST">
								<td><input type="submit" name="EditButton" value="Edit"></td>
								<input type="hidden" name="idGame" value="${idGame}">
							</form>
						</tr>
					</c:forEach>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Albume'}">
					<tr>
						<th>ID</th>
						<th>Trupa</th>
						<th>Titlu</th>
						<th>Casa discuri</th>
						<th>Nr. Melodii</th>
						<th>Gen</th>
						<th>An</th>
					</tr>
					
					<c:forEach var="album" items="${products}">
						<c:set var="idAlbum" value="${album.getIdAlbum()}"/>
						<tr>
							<td>${idAlbum}</td>
							<td>${album.getArtist()}</td>
							<td>${album.getTitle()}</td>
							<td>${album.getProducer()}</td>
							<td>${album.getNrMel()}</td>
							<td>${album.getGenre()}</td>
							<td>${album.getYear()}</td>
							<form action="/DVDMania-Web/Products/ToEditProduct" method="POST">
								<td><input type="submit" name="EditButton" value="Edit"></td>
								<input type="hidden" name="idAlbum" value="${idAlbum}">
							</form>
						</tr>
					</c:forEach>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Melodii'}">
					<tr>
						<th>Artist</th>
						<th>Titlu album</th>
						<th>Nume melodie</th>
						<th>Durata (secunde)</th>
					</tr>
					
					<c:forEach var="album" items="${products}">
						<c:forEach var="song" items="${album.getSongs()}">
							<tr>
								<td>${album.getArtist()}</td>
								<td>${album.getTitle()}</td>
								<td>${song.getNume()}</td>
								<td>${song.getDuration()}</td>
								<form action="/DVDMania-Web/Products/ToEditProduct" method="POST">
									<td><input type="submit" name="EditButton" value="Edit"></td>
									<input type="hidden" name="idSong" value="${song.getIdSong()}">
									<input type="hidden" name="idAlbum" value="${album.getIdAlbum()}">
								</form>
							</tr>
						</c:forEach>
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
	</body>
</html>