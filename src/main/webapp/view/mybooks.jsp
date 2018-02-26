<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet"
	href="/css/mybooks.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>



	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>
			<c:choose>
			<c:when test="${myBooks == true }">
				<h2 id="first">My Books</h2>
			</c:when>
			<c:when test="${myBooks == false }">
				<h2 id="first">${reader}'s Books</h2>
			</c:when>
			</c:choose>
			<hr>
			<c:choose>
			<c:when test="${empty readerBooks}">
			There are no books yet!
			</c:when>
			<c:otherwise>
			<table>
				
				<tr>
					<th>cover</th>
					<th>title</th>
					<th>author</th>
					<th>avg rating</th>
					<th>rating</th>
					<th>shelves</th>
					<th>date read</th>
					<th>date added</th>
				</tr>
				
				<c:forEach var="tempBook" items="${readerBooks}">
				<c:url var="titleRef" value="showBook">
					<c:param name="bookName" value="${tempBook.minBookName}"/>
				</c:url>
					<tr>
						<td ><img src="data:image/jpeg;base64,${tempBook.encodedBookPic}"/></td>
						<td  class="cells"><a id="titleRef" href="${titleRef}">${tempBook.fullBookName}</a></td>
						<td  class="cells">${tempBook.author}</td>
						<td  class="cells">${tempBook.rating}</td>
						<td  class="cells">${tempBook.readerRating}</td>						
						<td  class="cells">${tempBook.shelves}<br>
						<c:choose>
							<c:when test="${myBooks == true}">
							<form method="GET" action="/profile/updateState">
								<select name="newState">
									<option value = "Read">Read</option>
									<option value = "Currently Reading">Currently Reading</option>
									<option value = "Want To Read">Want To Read</option>
								</select>
								<input type="hidden" name="bookName" value="${tempBook.minBookName}">
								<input type="submit" value="Submit" />
							</form>
							</c:when>
						</c:choose>
						</td>						
						<td class="cells">${tempBook.dateRead}
						<c:choose>
						<c:when test="${myBooks == true}">
							<form method="GET" action="/profile/updateDateRead">
								<input id="dateReadChooser" name="dateRead" type="date" value="${tempBook.dateRead}">
							<input type="hidden" name="bookName" value="${tempBook.minBookName}">
							<input type="submit" value="Submit" />
							</form>
						</c:when>
						</c:choose>
						</td>						
						<td  id="lastTd">${tempBook.dateAdded}</td>						
					</tr>
				</c:forEach>
				
			</table>
			</c:otherwise>
			</c:choose>
	</div>

</body>

</html>