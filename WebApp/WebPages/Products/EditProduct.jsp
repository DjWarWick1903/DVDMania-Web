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
						<th>Stoc</th>
						<th>Pret</th>
					</tr>
					
					<form action="/DVDMania-Web/Products/EditProduct" method="POST">
						<c:set var="movie" value="${movieStock.getMovie()}"/>
						<tr>
							<input type="hidden" name="idMovie" value="${movie.getIdMovie()}">
							<td><input type="text" name="title" value="${movie.getTitle()}"></td>
							<td><input type="text" name="actPr" value="${movie.getMainActor()}"></td>
							<td><input type="text" name="director" value="${movie.getDirector()}"></td>
							<td><input type="number" name="duration" value="${movie.getDuration()}"></td>
							<td><input type="text" name="genre" value="${movie.getGenre()}"></td>
							<td><input type="number" name="year" value="${movie.getYear()}"></td>
							<td><input type="number" name="audience" value="${movie.getAudience()}"></td>
							<td><input type="number" name="quant" value="${movieStock.getQuantity()}"></td>
							<td><input type="number" name="price" value="${movieStock.getPrice()}"></td>
							<td><input type="submit" name="EditButton" value="Save"></td>
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
						<th>Stoc</th>
						<th>Pret</th>
					</tr>
					
					<form action="/DVDMania-Web/Products/EditProduct" method="POST">
						<c:set var="game" value="${gameStock.getGame()}"/>
						<tr>
							<input type="hidden" name="idGame" value="${game.getIdGame()}">
							<td><input type="text" name="title" value="${game.getTitle()}"></td>
							<td><input type="text" name="platform" value="${game.getPlatform()}"></td>
							<td><input type="text" name="developer" value="${game.getDeveloper()}"></td>
							<td><input type="text" name="publisher" value="${game.getPublisher()}"></td>
							<td><input type="text" name="genre" value="${game.getGenre()}"></td>
							<td><input type="number" name="year" value="${game.getYear()}"></td>
							<td><input type="number" name="audience" value="${game.getAudience()}"></td>
							<td><input type="number" name="quant" value="${gameStock.getQuantity()}"></td>
							<td><input type="number" name="price" value="${gameStock.getPrice()}"></td>
							<td><input type="submit" name="EditButton" value="Save"></td>
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
						<th>Stoc</th>
						<th>Pret</th>
					</tr>
					
					<form action="/DVDMania-Web/Products/EditProduct" method="POST">
						<c:set var="album" value="${albumStock.getAlbum()}"/>
						<tr>
							<input type="hidden" name="idAlbum" value="${album.getIdAlbum()}">
							<td><input type="text" name="artist" value="${album.getArtist()}"></td>
							<td><input type="text" name="title" value="${album.getTitle()}"></td>
							<td><input type="text" name="producer" value="${album.getProducer()}"></td>
							<td><input type="number" name="nrMel" value="${album.getNrMel()}"></td>
							<td><input type="text" name="genre" value="${album.getGenre()}"></td>
							<td><input type="number" name="year" value="${album.getYear()}"></td>
							<td><input type="number" name="quant" value="${albumStock.getQuantity()}"></td>
							<td><input type="number" name="price" value="${albumStock.getPrice()}"></td>
							<td><input type="submit" name="EditButton" value="Save"></td>
						</tr>
					</form>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Melodii'}">
					<tr>
						<th>Nume melodie</th>
						<th>Durata (secunde)</th>
					</tr>
					
					<form action="/DVDMania-Web/Products/EditProduct" method="POST">
						<tr>
							<input type="hidden" name="idSong" value="${song.getIdSong()}">
							<td><input type="text" name="name" value="${song.getNume()}"></td>
							<td><input type="number" name="duration" value="${song.getDuration()}"></td>
							<td><input type="submit" name="EditButton" value="Save"></td>
						</tr>
					</form>
				</c:when>
			</c:choose>
		</table>
	</body>
</html>