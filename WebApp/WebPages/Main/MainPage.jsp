<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

	<head>
		<title>DVDMania/MainPage</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/DVDMania.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/topnav.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/customTable.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/PageStyles/categories.css">
	</head>
	
	<body>
		<div class="banner">
			<img class="logo" src="${pageContext.request.contextPath}/logo.png">
			<h1>DVDMania</h1>
		</div>
		<hr>
		
		<div class="topnav">
			<a class="active" href="/DVDMania-Web/MainPage">Home</a>
			<a href="/DVDMania-Web/Orders">Orders</a>
			<a href="Products.php">Products</a>
			<a href="Customers.php">Customers</a>
			<a href="Employees.php">Employees</a>
			<a href="Stores.php">Stores</a>
			
			<a class="returnal" href="/DVDMania-Web/Login"><c:out value="${not empty account ? 'Logout' : 'Login'}"/></a>
	    </div>
		
		<hr>
		
		<div class="categories">
			<form action="/DVDMania-Web/MainPage" method="GET">
				<input <c:if test="${sessionScope.productSelection eq 'Filme'}"> <c:out value="class=selected"/> </c:if> type="submit" value="Filme" name="productSelection">
				<input <c:if test="${sessionScope.productSelection eq 'Jocuri'}"> <c:out value="class=selected"/> </c:if> type="submit" value="Jocuri" name="productSelection">
				<input <c:if test="${sessionScope.productSelection eq 'Muzica'}"> <c:out value="class=selected"/> </c:if> type="submit" value="Muzica" name="productSelection">
			</form>
		</div>
		<br>
		<c:if test="${sessionScope.productSelection ne 'Melodii'}">
			<div class="search">
				<br>
				<form action="/DVDMania-Web/MainPage" method="GET">
					<label for="storeSelection" style="color:white">Oras:</label>
					<select name="storeSelection">
						<c:forEach var="store" items="${sessionScope.storeList}">
							<option value="${store.getOras()}"<c:if test="${sessionScope.storeSelection eq store.getOras()}"><c:out value="selected"/></c:if>>${store.getOras()}</option>
						</c:forEach>
					</select>
					
					<label for="categorySelection" style="color:white">Categorie:</label>
					<select name="categorySelection">
						<c:forEach var="category" items="${sessionScope.categoryList}">
							<option value="${category}" <c:if test="${sessionScope.categorySelection eq category}"><c:out value="selected"/></c:if>>${category}</option>
						</c:forEach>
					</select>
					
					<input type="text" name="searchField" value="${sessionScope.searchField}">
					<input type="submit" name="searchButton" value="Search">
				</form>
			</div>
		</c:if>
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
						<th>Pret</th>
						<th>Magazin</th>
						<th>Stoc in prezent</th>
					</tr>
					
					<c:forEach var="stock" items="${sessionScope.stocks}">
						<c:set var="movie" value="${stock.getMovie()}"/>
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
							<td>${stock.getPrice()}</td>
							<td>${stock.getStore().getOras()}</td>
							<td>${currentStock[idMovie]}</td>
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
						<th>Pret</th>
						<th>Magazin</th>
						<th>Stoc in prezent</th>
					</tr>
					
					<c:forEach var="stock" items="${sessionScope.stocks}">
						<c:set var="game" value="${stock.getGame()}"/>
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
							<td>${stock.getPrice()}</td>
							<td>${stock.getStore().getOras()}</td>
							<td>${currentStock[idGame]}</td>
						</tr>
					</c:forEach>
				</c:when>
				
				<c:when test="${sessionScope.productSelection eq 'Muzica'}">
					<tr>
						<th>ID</th>
						<th>Trupa</th>
						<th>Titlu</th>
						<th>Casa discuri</th>
						<th>Nr. Melodii</th>
						<th>Gen</th>
						<th>An</th>
						<th>Pret</th>
						<th>Magazin</th>
						<th>Stoc in prezent</th>
					</tr>
					
					<c:forEach var="stock" items="${sessionScope.stocks}">
						<c:set var="album" value="${stock.getAlbum()}"/>
						<c:set var="idAlbum" value="${album.getIdAlbum()}"/>
						<tr>
							<td>${idAlbum}</td>
							<td>${album.getArtist()}</td>
							<td>${album.getTitle()}</td>
							<td>${album.getProducer()}</td>
							<td>${album.getNrMel()}</td>
							<td>${album.getGenre()}</td>
							<td>${album.getYear()}</td>
							<td>${stock.getPrice()}</td>
							<td>${stock.getStore().getOras()}</td>
							<td>${currentStock[idAlbum]}</td>
							<form action="/DVDMania-Web/MainPage" method="GET">
								<td><input class="songs" type="submit" name="getSongs" value="Melodii"></td>
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
					
					<c:forEach var="songs" items="${songList}">
						<tr>
							<td>${sessionScope.album.getArtist()}</td>
							<td>${sessionScope.album.getTitle()}</td>
							<td>${songs.getNume()}</td>
							<td>${songs.getDuration()}</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</table>
		
		<br>
	</body>
</html>