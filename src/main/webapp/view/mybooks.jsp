<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/mybooks.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<sec:authentication var="principal" property="principal" />

	<c:set var="isItMyBooks" value="false"></c:set>

	<sec:authorize access="isFullyAuthenticated()">
		<sec:authentication property="principal.username" var="username" />

		<c:if test="${username == readerLogin}">

			<c:set var="isItMyBooks" value="true"></c:set>

		</c:if>
	</sec:authorize>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>
		<c:choose>
			<c:when test="${isItMyBooks == true }">
				<c:choose>
					<c:when test="${empty shelvesStates}">
						<h2 id="first">My Books:All</h2>
					</c:when>
					<c:otherwise>
						<h2 id="first">My Books:${shelvesStates.shelveState()}</h2>
					</c:otherwise>
				</c:choose>

			</c:when>
			<c:when test="${isItMyBooks == false }">
				<h2 id="first">${readerLogin}'sBooks</h2>
			</c:when>
		</c:choose>
		<hr>
		<c:choose>
			<c:when test="${all == 0}">
			There are no books yet!
			</c:when>
			<c:otherwise>

				<c:url var="showAll" value="/myBooks/showMyBooks">
					<c:param name="readerId" value="${readerId}" />
				</c:url>

				<c:url var="showRead" value="/myBooks/showMyBooks">
					<c:param name="shelves" value="Read" />
					<c:param name="readerId" value="${readerId}" />
				</c:url>

				<c:url var="showCurrentlyReading" value="/myBooks/showMyBooks">
					<c:param name="shelves" value="Currently Reading" />
					<c:param name="readerId" value="${readerId}" />
				</c:url>

				<c:url var="showWantToRead" value="/myBooks/showMyBooks">
					<c:param name="shelves" value="Want To Read" />
					<c:param name="readerId" value="${readerId}" />
				</c:url>

				<div id="filters">
					<b>Bookshelves</b><br> <a class="blackRefNoDec"
						href="${showAll}">All(${all})</a><br> <a
						class="blackRefNoDec" href="${showRead}">Read(${read})</a><br>
					<a class="blackRefNoDec" href="${showCurrentlyReading}">Currently
						Reading(${currentlyReading})</a><br> <a class="blackRefNoDec"
						href="${showWantToRead}">Want To Read(${wantToRead})</a><br>
				</div>

				<table id="mainTable">

					<tr>
						<th>cover</th>
						<th>title</th>
						<th>author</th>
						<th>avg rating</th>
						<th>rating</th>
						<th>shelves</th>
						<th>date read</th>
						<th>date added</th>
						<c:if test="${isItMyBooks == true}">
							<th>Delete</th>
						</c:if>
					</tr>

					<c:forEach var="tempReaderBook" items="${readerBooks}">
						<c:url var="titleRef" value="/reader/showBook">
							<c:param name="bookId" value="${tempReaderBook.book.id}" />
						</c:url>
						<tr>
							<td><img
								src="data:image/jpeg;base64,${tempReaderBook.book.encodedBookPhoto}" /></td>
							<td><a class="titleRef" href="${titleRef}">${tempReaderBook.book.fullTitle}</a></td>
							<td>${tempReaderBook.book.author}</td>
							<td>${tempReaderBook.overallRating}</td>
							<td>${tempReaderBook.readerRating.rate}</td>
							<td>${tempReaderBook.shelvesStates.shelveState()}<br> <c:choose>
									<c:when test="${isItMyBooks == true}">
										<form method="POST" action="/myBooks/updateState">
											<select class="rounded" name="newShelves"
												onchange="this.form.submit()">
												<option value="" selected disabled hidden>Select
													Shelves</option>
												<option value="Read">Read</option>
												<option value="Currently Reading">Currently Reading</option>
												<option value="Want To Read">Want To Read</option>
											</select> <input type="hidden" name="bookId"
												value="${tempReaderBook.book.id}"> <input
												type="hidden" name="isItUpdateReaderBook" value="true">
											<input type="hidden" name="readerBookId"
												value="${tempReaderBook.id}">
										</form>
									</c:when>
								</c:choose>
							</td>
							<td><fmt:formatDate pattern = "yyyy-MM-dd" 
         					value = "${tempReaderBook.dateRead}" /><c:choose>
									<c:when test="${isItMyBooks == true}">
										<form method="POST" action="/myBooks/updateDateRead"
											id="dateReadForm">
											<input class="dateReadChooser" name="dateRead" type="date"
												value="${tempReaderBook.dateRead}"
												onchange="this.form.submit()"> <input type="hidden"
												name="bookName" value="${tempReaderBook.book.miniTitle}">
										</form>
									</c:when>
								</c:choose>
							</td>
							<td><fmt:formatDate pattern = "yyyy-MM-dd" 
         					value = "${tempReaderBook.dateAdded}" /></td>
							<c:url var="delete" value="/myBooks/deleteReaderBook">
								<c:param name="bookId" value="${tempReaderBook.book.id}"></c:param>
							</c:url>
							<c:if test="${isItMyBooks == true}">
								<td style="vertical-align: top; padding-top: 20px;"><a
									class="titleRef" href="${delete}"
									onclick="return confirm('Are you sure you want to delete this book?');">Delete</a></td>
							</c:if>
						</tr>
					</c:forEach>

				</table>
			</c:otherwise>
		</c:choose>
		
		<div style="clear:both"></div>
		
		<div id="pageNumbers">
		<c:forEach begin="1" end="${readerBooksPage.totalPages}" var="i">
			<c:url var="page" value="/myBooks/showMyBooks">
				<c:param name="page" value="${i}"></c:param>
				<c:param name="shelves" value="${shelvesState.shelveState()}"></c:param>
				<c:param name="readerId" value="${readerId}"></c:param>
			</c:url>
			<h3 class="pageNum">
				<a class="blackRef" href="${page}">${i}</a>
			</h3>
		</c:forEach>
		</div>
	</div>

</body>

<script src="/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		var isItMyBooks = $
		{
			isItMyBooks
		}

		if (isItMyBooks == false) {

			$('#mainTable td').css({
				'padding-left' : '30px'
			})

			$('#mainTable th').css({
				'padding-left' : '30px'
			})

		}

		else if (isItMyBooks == true) {

			$('#mainTable td').css({
				'padding-left' : '10px'
			})

			$('#mainTable th').css({
				'padding-left' : '10px'
			})

			$('#mainTable td:nth-child(8)').css({
				'width' : '120px'
			})

			$('#mainTable td:nth-child(8)').css({
				'padding-left' : '40px'
			})

		}
	});
</script>

</html>