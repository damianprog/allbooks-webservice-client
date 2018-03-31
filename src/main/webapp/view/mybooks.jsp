<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/mybooks.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

<sec:authentication var="principal" property="principal" />

<c:set var="myBooks" value="false"></c:set>

<sec:authorize access="isFullyAuthenticated()">
	<sec:authentication property="principal.username" var="username" />
	
	<c:if test="${username == readerLogin}">
	
		<c:set var="myBooks" value="true"></c:set>
	
	</c:if>
</sec:authorize>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>
		<c:choose>
			<c:when test="${myBooks == true }">
				<h2 id="first">My Books</h2>
			</c:when>
			<c:when test="${myBooks == false }">
				<h2 id="first">${readerLogin}'s Books</h2>
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
						<c:if test="${myBooks == true}">
							<th>Delete</th>
						</c:if>
					</tr>

					<c:forEach var="tempBook" items="${readerBooks}">
						<c:url var="titleRef" value="showBook">
							<c:param name="bookName" value="${tempBook.book.miniTitle}" />
						</c:url>
						<tr>
							<td><img
								src="data:image/jpeg;base64,${tempBook.encodedBookPic}" /></td>
							<td class="cells"><a class="titleRef" href="${titleRef}">${tempBook.book.fullTitle}</a></td>
							<td class="cells">${tempBook.book.author}</td>
							<td class="cells">${tempBook.overallRating}</td>
							<td class="cells">${tempBook.readerRating.rate}</td>
							<td class="cells">${tempBook.shelves}<br> <c:choose>
									<c:when test="${myBooks == true}">
										<form method="GET" action="/profile/updateState">
											<select name="newState">
												<option value="Read">Read</option>
												<option value="Currently Reading">Currently Reading</option>
												<option value="Want To Read">Want To Read</option>
											</select> <input type="hidden" name="bookName"
												value="${tempBook.book.miniTitle}"> <input
												type="submit" value="Submit" />
										</form>
									</c:when>
								</c:choose>
							</td>
							<td class="cells">${tempBook.dateRead}<c:choose>
									<c:when test="${myBooks == true}">
										<form method="GET" action="/profile/updateDateRead">
											<input id="dateReadChooser" name="dateRead" type="date"
												value="${tempBook.dateRead}"> <input type="hidden"
												name="bookName" value="${tempBook.book.miniTitle}">
											<input type="submit" value="Submit" />
										</form>
									</c:when>
								</c:choose>
							</td>
							<td id="lastTd">${tempBook.dateAdded}</td>
							<c:url var="delete" value="deleteReaderBook">
								<c:param name="bookId" value="${tempBook.book.id}"></c:param>
							</c:url>
							<c:if test="${myBooks == true}">
								<td style="vertical-align: top; padding-top: 20px;"><a
									class="titleRef" href="${delete}">Delete</a></td>
							</c:if>
						</tr>
					</c:forEach>

				</table>
			</c:otherwise>
		</c:choose>
	</div>

</body>

</html>